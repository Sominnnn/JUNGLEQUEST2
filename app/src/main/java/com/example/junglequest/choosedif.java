package com.example.junglequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class choosedif extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choosedif);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.startbtn_choosedif), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button homeButton = findViewById(R.id.homebtn_choosedif);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CardCatalogActivity
                Intent intent = new Intent(choosedif.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button instructionsButton = findViewById(R.id.instructionbtn_choosedif);
        instructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CardCatalogActivity
                Intent intent = new Intent(choosedif.this, aboutgame.class);
                startActivity(intent);
            }
        });

        Button easyButton = findViewById(R.id.easybtn_choosedif);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CardCatalogActivity
                Intent intent = new Intent(choosedif.this, easy.class);
                startActivity(intent);
            }
        });

        Button mediumButton = findViewById(R.id.mediumbtn_choosedif);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CardCatalogActivity
                Intent intent = new Intent(choosedif.this, medium.class);
                startActivity(intent);
            }
        });

    }
}