package com.example.junglequest;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Rect;
import android.app.Dialog;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class easy extends AppCompatActivity {

    private CountDownTimer gameTimer;
    private TextView timerTextView;
    private boolean isPaused = false;
    private long timeLeftInMillis = 60000; // 1 minute for easy level
    private static final long THIRTY_SECONDS_MILLIS = 30000; // 30 seconds threshold
    private boolean warningShown = false;
    private Dialog pauseDialog; // Dialog for pause menu

    // Track correct animal placements
    private HashMap<Integer, View> targetZones = new HashMap<>();
    private HashMap<Integer, Integer> correctPlacements = new HashMap<>();
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_easy);

        // If startTime wasn't passed in intent, record it now
        if (getIntent().getLongExtra("startTime", 0) == 0) {
            getIntent().putExtra("startTime", System.currentTimeMillis());
        }

        // Find the correct main layout ID
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hard_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up timer
        timerTextView = findViewById(R.id.timer);

        // Initialize and start the timer
        startTimer();

        // Set up pause button
        Button pauseButton = findViewById(R.id.pausebtn_easy);
        pauseButton.setOnClickListener(v -> showPauseMenu());

        // Define target zone (assuming there is only one in easy mode)
        View landZone = findViewById(R.id.landzone_easy);

        // Add target zones to HashMap (if multiple zones exist)
        if (landZone != null) {
            targetZones.put(R.id.landzone_easy, landZone);
        }

        // Set up finish button
        finishButton = findViewById(R.id.donebtn_easy);
        // Initially disable the button - will be enabled when all animals are correctly placed
        finishButton.setEnabled(false);
        finishButton.setOnClickListener(v -> {
            // Stop the timer when finish button is clicked
            if (gameTimer != null) {
                gameTimer.cancel();
            }

            // Show different completion screens based on time remaining
            if (timeLeftInMillis > THIRTY_SECONDS_MILLIS) {
                // Completed with more than 30 seconds remaining (finished in less than 30 seconds)
                showGreatJobScreen();
            } else {
                // Completed with less than 30 seconds remaining
                showWinScreen();
            }
        });

        // Get the draggable animal ImageViews
        ImageView draggableLion = findViewById(R.id.drag_lion);
        ImageView draggableTiger = findViewById(R.id.drag_tiger);
        ImageView draggableElephant = findViewById(R.id.drag_elephant);
        ImageView draggableGiraffe = findViewById(R.id.drag_giraffe);
        ImageView draggableZebra = findViewById(R.id.drag_zebra);

        // Define which animals belong to which zone (assuming all belong to landzone in easy mode)
        int landZoneId = R.id.landzone_easy;

        // Set up draggable animals with their correct zone
        if (draggableLion != null) setupDraggable(draggableLion, landZoneId);
        if (draggableTiger != null) setupDraggable(draggableTiger, landZoneId);
        if (draggableElephant != null) setupDraggable(draggableElephant, landZoneId);
        if (draggableGiraffe != null) setupDraggable(draggableGiraffe, landZoneId);
        if (draggableZebra != null) setupDraggable(draggableZebra, landZoneId);

        // Get the container layout
        ConstraintLayout mainLayout = findViewById(R.id.hard_main);

        // Set drag listener on the container layout
        mainLayout.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;
                case DragEvent.ACTION_DROP:
                    // Get the dragged view
                    View draggedView = (View) event.getLocalState();

                    // Calculate position adjusted for the view's width/height
                    float dropX = event.getX() - (draggedView.getWidth() / 2);
                    float dropY = event.getY() - (draggedView.getHeight() / 2);

                    // Add boundary constraints
                    if (dropX < 0) dropX = 0;
                    if (dropY < 0) dropY = 0;
                    if (dropX > mainLayout.getWidth() - draggedView.getWidth())
                        dropX = mainLayout.getWidth() - draggedView.getWidth();
                    if (dropY > mainLayout.getHeight() - draggedView.getHeight())
                        dropY = mainLayout.getHeight() - draggedView.getHeight();

                    // Set the new position
                    draggedView.setX(dropX);
                    draggedView.setY(dropY);

                    // Make the view visible again
                    draggedView.setVisibility(View.VISIBLE);

                    // Check if the animal is in the correct zone
                    checkAnimalPlacement(draggedView);

                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Ensure the view is visible if the drag ended without a drop
                    View view = (View) event.getLocalState();
                    if (view != null && view.getVisibility() == View.INVISIBLE) {
                        view.setVisibility(View.VISIBLE);
                    }
                    return true;
                default:
                    return false;
            }
        });

        // Initialize pause dialog
        initializePauseDialog();
    }

    // Show win screen with clickable buttons (for completing in 30-60 seconds)
    private void showWinScreen() {
        // Calculate completion time
        long endTime = System.currentTimeMillis();
        long startTime = getIntent().getLongExtra("startTime", 0);
        long completionTimeMs = endTime - startTime;

        // Convert to a readable format (e.g., minutes:seconds)
        int seconds = (int) (completionTimeMs / 1000) % 60;
        int minutes = (int) ((completionTimeMs / (1000 * 60)) % 60);
        String timeString = String.format("%d:%02d", minutes, seconds);

        // Save the completion time
        SharedPreferences sharedPreferences = getSharedPreferences("JungleQuestPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("completionTime", timeString);
        editor.apply();

        // Now change layout
        setContentView(R.layout.activity_youwin);

        // Set up button click listeners on the win screen
        Button homeButton = findViewById(R.id.homebtn_youwin);
        Button instructionsButton = findViewById(R.id.instructionbtn_youwin);
        Button leaderboardsButton = findViewById(R.id.leaderboardbtn_youwin2);

        if (homeButton != null) {
            homeButton.setOnClickListener(v -> {
                // Navigate to home/main menu
                Intent intent = new Intent(easy.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }

        if (instructionsButton != null) {
            instructionsButton.setOnClickListener(v -> {
                // Navigate to instructions screen
                Intent intent = new Intent(easy.this, choosedif.class);
                startActivity(intent);
                finish();
            });
        }

        if (leaderboardsButton != null) {
            leaderboardsButton.setOnClickListener(v -> {
                // Navigate to leaderboards screen
                Intent intent = new Intent(easy.this, leaderboard.class);
                startActivity(intent);
                finish();
            });
        }
    }

    // Show great job screen with clickable buttons (for completing in under 30 seconds)
    private void showGreatJobScreen() {
        // Calculate completion time
        long endTime = System.currentTimeMillis();
        long startTime = getIntent().getLongExtra("startTime", 0);
        long completionTimeMs = endTime - startTime;

        // Convert to a readable format (e.g., minutes:seconds)
        int seconds = (int) (completionTimeMs / 1000) % 60;
        int minutes = (int) ((completionTimeMs / (1000 * 60)) % 60);
        String timeString = String.format("%d:%02d", minutes, seconds);

        // Save the completion time
        SharedPreferences sharedPreferences = getSharedPreferences("JungleQuestPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("completionTime", timeString);
        editor.apply();

        // Now change layout
        setContentView(R.layout.activity_greatjob);

        // Set up button click listeners on the great job screen
        Button homeButton = findViewById(R.id.homebtn_greatjob);
        Button instructionsButton = findViewById(R.id.playagainbtn_greatjob);
        Button leaderboardsButton = findViewById(R.id.leaderoardbtn_greatjob);

        if (homeButton != null) {
            homeButton.setOnClickListener(v -> {
                // Navigate to home/main menu
                Intent intent = new Intent(easy.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }

        if (instructionsButton != null) {
            instructionsButton.setOnClickListener(v -> {
                // Navigate to instructions screen
                Intent intent = new Intent(easy.this, choosedif.class);
                startActivity(intent);
                finish();
            });
        }

        if (leaderboardsButton != null) {
            leaderboardsButton.setOnClickListener(v -> {
                // Navigate to leaderboards screen
                Intent intent = new Intent(easy.this, leaderboard.class);
                startActivity(intent);
                finish();
            });
        }
    }

    // Initialize pause dialog
    private void initializePauseDialog() {
        pauseDialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        pauseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pauseDialog.setContentView(R.layout.activity_pause);
        pauseDialog.setCancelable(false);

        // Setup buttons in the pause menu
        Button resumeButton = pauseDialog.findViewById(R.id.continuebtn_pause);
        Button restartButton = pauseDialog.findViewById(R.id.restartbtn_pause);
        Button quitButton = pauseDialog.findViewById(R.id.exitbtn_pause);

        // Resume button returns to game
        resumeButton.setOnClickListener(v -> {
            pauseDialog.dismiss();
            resumeGame();
        });

        // Restart button resets the game
        restartButton.setOnClickListener(v -> {
            pauseDialog.dismiss();
            restartGame();
        });

        // Quit button returns to main menu
        quitButton.setOnClickListener(v -> {
            pauseDialog.dismiss();
            quitToMainMenu();
        });
    }

    // Show pause menu dialog
    private void showPauseMenu() {
        // Pause the game
        pauseGame();

        // Show the pause dialog
        pauseDialog.show();
    }

    // Pause the game
    private void pauseGame() {
        if (!isPaused) {
            if (gameTimer != null) {
                gameTimer.cancel();
            }
            isPaused = true;
        }
    }

    // Resume the game
    private void resumeGame() {
        if (isPaused) {
            startTimer();
            isPaused = false;
        }
    }

    // Restart the game
    private void restartGame() {
        // Reset the timer
        timeLeftInMillis = 60000;
        warningShown = false;
        isPaused = false;

        // Restart the activity
        recreate();
    }

    // Return to main menu
    private void quitToMainMenu() {
        // Navigate to the main menu activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close this activity
    }

    // Helper method to set up drag functionality
    private void setupDraggable(ImageView view, int correctZoneId) {
        // Store the correct zone for this animal
        correctPlacements.put(view.getId(), correctZoneId);

        view.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE);
            return true;
        });
    }

    // Check if the animal is placed in its correct zone
    private void checkAnimalPlacement(View animal) {
        int animalId = animal.getId();
        if (!correctPlacements.containsKey(animalId)) {
            return; // Skip if this isn't a tracked animal
        }

        int correctZoneId = correctPlacements.get(animalId);

        // Create rectangles to check for intersection
        Rect animalRect = new Rect();
        animal.getHitRect(animalRect);

        boolean isCorrectlyPlaced = false;

        // Check if the animal intersects with its correct target zone
        View targetZone = targetZones.get(correctZoneId);
        if (targetZone != null) {
            Rect zoneRect = new Rect();
            targetZone.getHitRect(zoneRect);

            // Check if animal is mostly within its correct zone
            if (Rect.intersects(animalRect, zoneRect)) {
                int intersectionArea = getIntersectionArea(animalRect, zoneRect);
                int animalArea = animalRect.width() * animalRect.height();

                // If more than 50% of the animal is in the correct zone
                if (intersectionArea > (animalArea * 0.5)) {
                    isCorrectlyPlaced = true;
                }
            }
        }

        // Update the placement status for this animal
        animal.setTag(isCorrectlyPlaced);

        // Check if all animals are correctly placed
        checkAllPlacements();
    }

    // Calculate the area of intersection between two rectangles
    private int getIntersectionArea(Rect r1, Rect r2) {
        Rect intersection = new Rect();
        if (intersection.setIntersect(r1, r2)) {
            return intersection.width() * intersection.height();
        }
        return 0;
    }

    // Check if all animals are placed correctly
    private void checkAllPlacements() {
        boolean allCorrect = true;

        // Check each animal
        for (Integer animalId : correctPlacements.keySet()) {
            View animal = findViewById(animalId);

            // If any animal is not in its correct place, don't enable the button
            if (animal != null && (animal.getTag() == null || !(Boolean)animal.getTag())) {
                allCorrect = false;
                break;
            }
        }

        // Enable or disable the finish button based on correctness
        if (finishButton != null) {
            finishButton.setEnabled(allCorrect);

            // Optional: provide feedback to the user
            if (allCorrect) {
                Toast.makeText(this, "Great job! All animals are in their correct habitats!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Start the countdown timer
    private void startTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }

        gameTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();

                // Change the text color to red when less than 15 seconds remain (25% of easy mode time)
                if (millisUntilFinished < 15000 && !warningShown) {
                    warningShown = true;
                    if (timerTextView != null) {
                        timerTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                }
            }

            @Override
            public void onFinish() {
                // Handle game over when timer expires
                setContentView(R.layout.activity_timerunout);

                // Add click listeners to buttons on time run out screen
                Button homeButton = findViewById(R.id.homebtn_timerunout);
                Button restartButton = findViewById(R.id.instructionbtn_timerunout);

                if (homeButton != null) {
                    homeButton.setOnClickListener(v -> {
                        Intent intent = new Intent(easy.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    });
                }

                if (restartButton != null) {
                    restartButton.setOnClickListener(v -> {
                        restartGame();
                    });
                }
            }
        }.start();
    }

    // Update the timer display
    private void updateTimerText() {
        if (timerTextView != null) {
            int minutes = (int) (timeLeftInMillis / 1000) / 60;
            int seconds = (int) (timeLeftInMillis / 1000) % 60;
            String timeFormatted = String.format("%02d:%02d", minutes, seconds);
            timerTextView.setText(timeFormatted);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPaused) {
            // Don't restart timer if game is deliberately paused
            return;
        }
        startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        if (pauseDialog != null && pauseDialog.isShowing()) {
            pauseDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        // Show pause menu when back button is pressed instead of exiting
        showPauseMenu();
    }
}