package com.example.junglequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class leaderboard extends AppCompatActivity {

    // Define constant for SharedPreferences name and key (same as in latestsignup)
    public static final String PREF_NAME = "JungleQuestPrefs";
    public static final String KEY_USERNAME = "currentUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hard_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get references to the TextViews in your layout where you'll display the data
        TextView playerNameTextView = findViewById(R.id.player_name_textview);
        TextView completionTimeTextView = findViewById(R.id.completion_time_textview);

        // Retrieve the saved player name using the same key as in latestsignup
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String playerName = sharedPreferences.getString(KEY_USERNAME, "");

        // If no username was found, try getting it from the latestsignup class
        if (playerName == null || playerName.isEmpty()) {
            playerName = latestsignup.getUsername(this);
        }

        // If still no username, use a default value
        if (playerName == null || playerName.isEmpty()) {
            playerName = "No player";
        }

        String completionTime = sharedPreferences.getString("completionTime", "No time recorded");

        // Debug toast to help troubleshoot
        Toast.makeText(this, "Retrieved username: " + playerName, Toast.LENGTH_SHORT).show();

        // Display the values in the TextViews
        playerNameTextView.setText(playerName);
        completionTimeTextView.setText(completionTime);

        Button homeButton = findViewById(R.id.homebtn_leaderboard);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainActivity
                Intent intent = new Intent(leaderboard.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button instructionsButton = findViewById(R.id.instructionbtn_leaderboard);
        instructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to aboutgame
                Intent intent = new Intent(leaderboard.this, aboutgame.class);
                startActivity(intent);
            }
        });

        Button playButton = findViewById(R.id.playbtn_leaderboard);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to choosedif
                Intent intent = new Intent(leaderboard.this, choosedif.class);
                startActivity(intent);
            }
        });
    }
}