package com.copteam22.afterwordsapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.copteam22.afterwordsapp.network.ApiService;
import com.copteam22.afterwordsapp.network.GenericResponse;
import com.copteam22.afterwordsapp.network.RetrofitClient;
import com.copteam22.afterwordsapp.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textViewUserInfo;
    private Button buttonLogout, buttonDeleteAccount, buttonMessages;
    private SharedPrefManager sharedPrefManager;
    private ApiService apiService;
    private static final String BASE_URL = "http://161.35.116.218:5000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        textViewUserInfo = findViewById(R.id.textViewUserId);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonDeleteAccount = findViewById(R.id.buttonDeleteAccount);
        buttonMessages = findViewById(R.id.buttonMessages);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        apiService = RetrofitClient.getClient(BASE_URL).create(ApiService.class);

        User user = sharedPrefManager.getUser();
        if(user != null) {
            displayUserInfo(user);
        } else {
            // User not logged in, redirect to LoginActivity
            Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        buttonLogout.setOnClickListener(view -> logout());
        buttonMessages.setOnClickListener(view -> viewMessages());
        buttonDeleteAccount.setOnClickListener(view -> deleteAccount());
    }

    private void viewMessages() {
        Intent intent = new Intent(UserProfileActivity.this, MessageActivity.class);
        startActivity(intent);
        finish();
    }
    private void displayUserInfo(User user) {
        String userInfo = "Welcome, " + user.getFirstName() + " " + user.getLastName() + "\n\n" +
                "User ID: " + user.getId() + "\n" +
                "Username: " + user.getUsername() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Check-In Frequency: " + user.getCheckInFreq() + " seconds\n" +
                "Verified: " + (user.isVerified() ? "Yes" : "No") + "\n" +
                "Deceased: " + (user.isDeceased() ? "Yes" : "No") + "\n" +
                "Account Created At: " + user.getCreatedAt() + "\n" +
                "Last Login: " + user.getLastLogin();

        textViewUserInfo.setText(userInfo);
    }

    private void logout() {
        sharedPrefManager.logout();
        Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", (dialog, id) -> performDeleteAccount())
                .setNegativeButton("No", null)
                .show();
    }

    private void performDeleteAccount() {
        int userId = sharedPrefManager.getUser().getId();
        Map<String, Integer> request = new HashMap<>();
        request.put("userId", userId);

        Call<GenericResponse> call = apiService.deleteUser(request);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if(response.isSuccessful()) {
                    sharedPrefManager.logout();
                    Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UserProfileActivity.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
