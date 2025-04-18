package com.example.junglequest;

import android.content.ClipData;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_easy);

        // Find the correct main layout ID (should be main, not hard_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hard_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up timer
        timerTextView = findViewById(R.id.timer); // Make sure to add this TextView to your layout

        // Initialize and start the timer
        startTimer();

        // Set up pause button
        Button pauseButton = findViewById(R.id.pausebtn_easy);
        pauseButton.setOnClickListener(v -> togglePause());

        // Get the draggable animal ImageViews
        ImageView draggableLion = findViewById(R.id.drag_lion);
        ImageView draggableTiger = findViewById(R.id.drag_tiger);
        ImageView draggableElephant = findViewById(R.id.drag_elephant);
        ImageView draggableGiraffe = findViewById(R.id.drag_giraffe);
        ImageView draggableZebra = findViewById(R.id.drag_zebra);

        // Set up drag functionality for all images
        setupDraggable(draggableLion);
        setupDraggable(draggableTiger);
        setupDraggable(draggableElephant);
        setupDraggable(draggableGiraffe);
        setupDraggable(draggableZebra);

        // Get the container layout - use the correct ID
        ConstraintLayout dropZone = findViewById(R.id.hard_main);

        // Set drag listener on the container layout
        dropZone.setOnDragListener((v, event) -> {
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
                    if (dropX > dropZone.getWidth() - draggedView.getWidth())
                        dropX = dropZone.getWidth() - draggedView.getWidth();
                    if (dropY > dropZone.getHeight() - draggedView.getHeight())
                        dropY = dropZone.getHeight() - draggedView.getHeight();

                    // Set the new position
                    draggedView.setX(dropX);
                    draggedView.setY(dropY);

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

    // Helper method to set up drag functionality
    private void setupDraggable(ImageView view) {
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
        gameTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                // Handle game over when timer expires
                timerTextView.setText("Time's Up!");
                // You can add game over logic here
            }
        }.start();
    }

    // Update the timer display
    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }

    // Toggle pause state
    private void togglePause() {
        if (isPaused) {
            // Resume the game
            startTimer();
            isPaused = false;
        } else {
            // Pause the game
            gameTimer.cancel();
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
    protected void onDestroy() {
        super.onDestroy();
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }
}