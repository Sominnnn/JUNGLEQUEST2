package com.example.junglequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class latestsignup extends AppCompatActivity {

    private EditText usernameEditText;
    private Button startButton;
    // Define constant for SharedPreferences name and key
    public static final String PREF_NAME = "JungleQuestPrefs";
    public static final String KEY_USERNAME = "currentUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_latestsignup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        usernameEditText = findViewById(R.id.username_edit_text);
        startButton = findViewById(R.id.startbtn_latestsignup);

        // Set click listener for start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();

                // Validate username
                if (username.isEmpty()) {
                    Toast.makeText(latestsignup.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Debug toast to verify username
                Toast.makeText(latestsignup.this, "Username saved: " + username, Toast.LENGTH_SHORT).show();

                // Save username in SharedPreferences
                saveUsername(username);

                // Verify the save worked immediately
                String savedName = getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(KEY_USERNAME, "Not Saved");
                if (!savedName.equals(username)) {
                    // Log the issue or handle it
                    Toast.makeText(latestsignup.this, "Warning: Username save verification failed", Toast.LENGTH_SHORT).show();
                }

                // Start the game activity
                Intent intent = new Intent(latestsignup.this, choosedif.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

    // Method to save username to SharedPreferences
    private void saveUsername(String username) {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USERNAME, username);
        // Use commit() for immediate writing to make absolutely sure it's saved before continuing
        boolean success = editor.commit();

        if (!success) {
            Toast.makeText(this, "Failed to save username", Toast.LENGTH_SHORT).show();
        }
    }

    // Static method to get username from any activity
    public static String getUsername(AppCompatActivity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String username = preferences.getString(KEY_USERNAME, "");

        // If username is empty, return a default value
        if (username == null || username.isEmpty()) {
            return "Player";
        }
        return username;
    }
}