package com.example.junglequest;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class hard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hard_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the draggable animal ImageViews
        ImageView draggableAnimal = findViewById(R.id.draghard_lion);
        ImageView draggableTiger = findViewById(R.id.draghard_tiger);
        ImageView draggableElephant = findViewById(R.id.draghard_elephant);
        ImageView draggableGiraffe = findViewById(R.id.draghard_giraffe);
        ImageView draggableZebra = findViewById(R.id.draghard_zebra);
        ImageView draggableJellyfish = findViewById(R.id.draghard_jellyfish);
        ImageView draggableOctopus = findViewById(R.id.draghard_octopus);
        ImageView draggableShark = findViewById(R.id.draghard_shark);
        ImageView draggableStingray = findViewById(R.id.draghard_stingray);
        ImageView draggableWhale = findViewById(R.id.draghard_whale);
        ImageView draggableEagle = findViewById(R.id.draghard_eagle);
        ImageView draggableFalcon = findViewById(R.id.draghard_falcon);
        ImageView draggablePigeon = findViewById(R.id.draghard_pigeon);
        ImageView draggableHummingbird = findViewById(R.id.draghard_hummingbird);
        ImageView draggableSwift = findViewById(R.id.draghard_swift);

        // Set long click listener for lion
        draggableAnimal.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for tiger
        draggableTiger.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });
        // Set long click listener for Elephant
        draggableElephant.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });
        // Set long click listener for Giraffe
        draggableGiraffe.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for Zebra
        draggableZebra.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for JELLYFISH
        draggableJellyfish.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for OCTOPUS
        draggableOctopus.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });
        // Set long click listener for SHARK
        draggableShark.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });
        // Set long click listener for STINGRAY
        draggableStingray.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for WHALE
        draggableWhale.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for EAGLE
        draggableEagle.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for FALCON
        draggableFalcon.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for PIGEON
        draggablePigeon.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for HUMMINGBIRD
        draggableHummingbird.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Set long click listener for SWIFT
        draggableSwift.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE); // Hide the original view during drag
            return true;
        });

        // Get the container layout - main is a ConstraintLayout, not an ImageView
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

                    // Set the new position
                    draggedView.setX(dropX);
                    draggedView.setY(dropY);

                    // Make the view visible again
                    draggedView.setVisibility(View.VISIBLE);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Ensure the view is visible if the drag ended without a drop
                    View view = (View) event.getLocalState();
                    if (view.getVisibility() == View.INVISIBLE) {
                        view.setVisibility(View.VISIBLE);
                    }
                    return true;
                default:
                    return false;
            }
        });
    }
}