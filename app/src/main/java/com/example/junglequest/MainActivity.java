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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button choosedifButton = findViewById(R.id.startbtn_main);
        choosedifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CardCatalogActivity
                Intent intent = new Intent(MainActivity.this, latestsignup.class);
                startActivity(intent);
            }
        });

        Button instructionButton = findViewById(R.id.instructionbtn_main);
        instructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CardCatalogActivity
                Intent intent = new Intent(MainActivity.this, howtoplay.class);
                startActivity(intent);
            }
        });

        Button categoriesButton = findViewById(R.id.categoriesbtn_main);
        categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CardCatalogActivity
                Intent intent = new Intent(MainActivity.this, land.class);
                startActivity(intent);
            }
        });

        Button leaderboardButton = findViewById(R.id.leaderboardbtn_main);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CardCatalogActivity
                Intent intent = new Intent(MainActivity.this, leaderboard.class);
                startActivity(intent);
            }
        });

    }
}