// src/main/java/com/copteam22/afterwordsapp/activities/LoginActivity.java

package com.copteam22.afterwordsapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.copteam22.afterwordsapp.R;
import com.copteam22.afterwordsapp.UserProfileActivity;
import com.copteam22.afterwordsapp.models.LoginResponse;
import com.copteam22.afterwordsapp.User;
import com.copteam22.afterwordsapp.network.ApiService;
import com.copteam22.afterwordsapp.network.RetrofitClient;
import com.copteam22.afterwordsapp.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private TextView textViewRegister;
    private ApiService apiService;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Ensure this is the correct layout

        // Initialize views using findViewById
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Initialize Retrofit and SharedPrefManager
        apiService = RetrofitClient.getClient("http://161.35.116.218:5000").create(ApiService.class);
        sharedPrefManager = SharedPrefManager.getInstance(this);

        // Check if user is already logged in
        if (sharedPrefManager.getUser() != null) {
            // Navigate to UserProfileActivity
            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }

        // Set OnClickListener for buttonRegister
        buttonRegister.setOnClickListener(v -> {
            Log.d("FadeMove", "Register Button clicked");
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Set up login button click listener
        buttonLogin.setOnClickListener(v -> {
            Log.d(TAG, "loginButton clicked");

            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            Log.d("FadeMove", "Username: " + editTextUsername.getText().toString().trim());
            Log.d("FadeMove", "Password: " + editTextPassword.getText().toString().trim());
            // Input validation
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both username and password.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Prepare request body
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("Username", username);
            loginRequest.put("Password", password);

            // Make API call
            Call<LoginResponse> call = apiService.loginUser(loginRequest);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Log.d(TAG, "onResponse called");

                    if (response.isSuccessful()) {
                        Log.d(TAG, "Response is successful");

                        LoginResponse loginResponse = response.body();
                        if (loginResponse != null) {
                            Log.d(TAG, "LoginResponse received");
                            Log.d("FadeMove", "LoginResponse ID: " + loginResponse.getId());
                            Log.d("FadeMove", "LoginResponse Error: " + loginResponse.getError());
                            if (loginResponse.getId() != -1  && TextUtils.isEmpty(loginResponse.getError())) {
                                // Handle successful login
                                User user = new User(
                                        loginResponse.getId(),
                                        loginResponse.getFirstName(),
                                        loginResponse.getLastName(),
                                        loginResponse.getUsername(),
                                        loginResponse.getEmail(),
                                        loginResponse.getCheckInFreq(),
                                        loginResponse.isVerified(),
                                        loginResponse.isDeceased(),
                                        loginResponse.getCreatedAt(),
                                        loginResponse.getLastLogin()
                                );
                                // Save user to Shared Preferences
                                sharedPrefManager.saveUser(user);
                                Log.d("FadeMove", "User saved to Shared Preferences");

                                // Navigate to UserProfileActivity
                                Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                                startActivity(intent);
                                finish();
                                Log.d("FadeMove", "Navigated to UserProfileActivity");

                            } else {
                                // Handle login errors
                                Log.d("FadeMove", "Login failed with error: " + loginResponse.getError());

                                Toast.makeText(LoginActivity.this, loginResponse.getError(), Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Log.d("FadeMove", "LoginResponse is null");

                            Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("FadeMove", "Response unsuccessful: " + response.code());

                        Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("FadeMove", "onFailure called with message: " + t.getMessage());

                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


    }
}
