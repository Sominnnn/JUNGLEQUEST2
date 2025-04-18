package com.example.junglequest;

import android.content.ClipData;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class easy extends AppCompatActivity {

    private CountDownTimer gameTimer;
    private TextView timerTextView;
    private boolean isPaused = false;
    private long timeLeftInMillis = 60000; // 60 seconds

    // Rectangle area for animals (will define this based on screen dimensions)
    private Rect whiteRectangleArea;

    // Track which animals are in the target area
    private boolean lionInTarget = false;
    private boolean tigerInTarget = false;
    private boolean elephantInTarget = false;
    private boolean giraffeInTarget = false;
    private boolean zebraInTarget = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_easy);

        // Find the correct main layout ID - changed from hard_main to easy_main
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
        pauseButton.setOnClickListener(v -> togglePause());

        // Set up finish button - ADD THIS BUTTON TO YOUR LAYOUT XML
        Button finishButton = findViewById(R.id.donebtn_easy);
        finishButton.setOnClickListener(v -> checkWinCondition());

        // Get the draggable animal ImageViews
        ImageView draggableLion = findViewById(R.id.drag_lion);
        ImageView draggableTiger = findViewById(R.id.drag_tiger);
        ImageView draggableElephant = findViewById(R.id.drag_elephant);
        ImageView draggableGiraffe = findViewById(R.id.drag_giraffe);
        ImageView draggableZebra = findViewById(R.id.drag_zebra);

        // Set up drag functionality for all images
        setupDraggable(draggableLion, "lion");
        setupDraggable(draggableTiger, "tiger");
        setupDraggable(draggableElephant, "elephant");
        setupDraggable(draggableGiraffe, "giraffe");
        setupDraggable(draggableZebra, "zebra");

        // Get the container layout - changed from hard_main to easy_main
        ConstraintLayout mainLayout = findViewById(R.id.hard_main);

        // Define white rectangle area relative to screen size (adjust these values to match your layout)
        mainLayout.post(() -> {
            int screenWidth = mainLayout.getWidth();
            int screenHeight = mainLayout.getHeight();

            // Define white rectangle area (adjust these percentages to match your layout)
            int rectLeft = (int)(screenWidth * 0.05);  // 5% from left
            int rectTop = (int)(screenHeight * 0.25);  // 25% from top
            int rectRight = (int)(screenWidth * 0.95); // 95% from left (5% from right)
            int rectBottom = (int)(screenHeight * 0.45); // 45% from top

            whiteRectangleArea = new Rect(rectLeft, rectTop, rectRight, rectBottom);
        });

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
                    String animalType = (String) draggedView.getTag();

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

                    // Check if animal is in the white rectangle area (but don't trigger win condition automatically)
                    updateAnimalPosition(draggedView, animalType, dropX, dropY);

                    // Make the view visible again
                    draggedView.setVisibility(View.VISIBLE);
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
    }

    // Update animal position status without checking win condition
    private void updateAnimalPosition(View view, String animalType, float x, float y) {
        if (whiteRectangleArea == null) return;

        // Calculate if the view overlaps substantially with the white rectangle
        // This is more reliable than just checking the center point
        Rect viewRect = new Rect(
                (int)x,
                (int)y,
                (int)(x + view.getWidth()),
                (int)(y + view.getHeight())
        );

        // Calculate overlap percentage between view and target rectangle
        Rect intersection = new Rect();
        boolean intersects = intersection.setIntersect(whiteRectangleArea, viewRect);
        boolean isInWhiteArea = false;

        if (intersects) {
            float overlap = (intersection.width() * intersection.height()) /
                    (float)(view.getWidth() * view.getHeight());
            // Consider it "in target" if at least 50% of the view is in the white rectangle
            isInWhiteArea = overlap >= 0.5f;
        }

        // Update the status for this animal
        switch (animalType) {
            case "lion":
                lionInTarget = isInWhiteArea;
                break;
            case "tiger":
                tigerInTarget = isInWhiteArea;
                break;
            case "elephant":
                elephantInTarget = isInWhiteArea;
                break;
            case "giraffe":
                giraffeInTarget = isInWhiteArea;
                break;
            case "zebra":
                zebraInTarget = isInWhiteArea;
                break;
        }
    }

    // Check if player has won (all animals in the white rectangle) - Called when finish button is clicked
    private void checkWinCondition() {
        if (lionInTarget && tigerInTarget && elephantInTarget && giraffeInTarget && zebraInTarget) {
            // All animals are in the white rectangle, show winning screen
            if (gameTimer != null) {
                gameTimer.cancel();  // Stop the timer
            }
            // Save the current time for scoring or display purposes if needed
            long timeTaken = 60000 - timeLeftInMillis;

            // Show winner screen
            setContentView(R.layout.activity_youwin);
        }
    }

    // Helper method to set up drag functionality
    private void setupDraggable(ImageView view, String animalType) {
        // Set tag to identify animal type
        view.setTag(animalType);

        view.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE);
            return true;
        });
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
            }

            @Override
            public void onFinish() {
                // Handle game over when timer expires
                setContentView(R.layout.activity_timerunout);
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

    // Toggle pause state
    private void togglePause() {
        if (isPaused) {
            // Resume the game
            startTimer();
            isPaused = false;
        } else {
            // Pause the game
            if (gameTimer != null) {
                gameTimer.cancel();
            }
            isPaused = true;
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
    }
}