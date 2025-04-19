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

        // Pre-fill the username if it exists in SharedPreferences
        loadSavedUsername();

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

                // Save username in SharedPreferences
                saveUsername(username);

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
        editor.commit(); // Using commit() instead of apply() for immediate writing
    }

    // Method to load saved username if it exists
    private void loadSavedUsername() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedUsername = preferences.getString(KEY_USERNAME, "");
        if (!savedUsername.isEmpty()) {
            usernameEditText.setText(savedUsername);
        }
    }

    // Add this static method to easily get username from any activity
    public static String getUsername(AppCompatActivity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return preferences.getString(KEY_USERNAME, "Unknown Player");
    }
}