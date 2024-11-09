// app/src/main/java/com/yourdomain/afterwordsapp/VerificationActivity.java
package com.copteam22.afterwordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.copteam22.afterwordsapp.models.VerificationRequest;
import com.copteam22.afterwordsapp.models.VerificationResponse;
import com.copteam22.afterwordsapp.network.ApiService;
import com.copteam22.afterwordsapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {

    private EditText editTextVerificationCode;
    private Button buttonVerify, buttonReturnToLogin;
    private TextView textViewVerificationMessage;
    private ApiService apiService;
    private static final String BASE_URL = "http://161.35.116.218:5000"; // Replace with your server's IP and port
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        // Initialize views
        editTextVerificationCode = findViewById(R.id.editTextVerificationCode);
        buttonVerify = findViewById(R.id.buttonVerify);
        buttonReturnToLogin = findViewById(R.id.buttonReturnToLogin);
        textViewVerificationMessage = findViewById(R.id.textViewVerificationMessage);

        // Initialize Retrofit
        apiService = RetrofitClient.getClient(BASE_URL).create(ApiService.class);

        // Get username from intent
        username = getIntent().getStringExtra("Username");

        // Set listeners
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();
            }
        });

        buttonReturnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerificationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void verifyUser(){
        String code = editTextVerificationCode.getText().toString().trim();

        if(code.isEmpty()){
            textViewVerificationMessage.setText("Please enter the verification code.");
            return;
        }

        VerificationRequest verificationRequest = new VerificationRequest(username, code);
        Call<VerificationResponse> call = apiService.verifyUser(verificationRequest);
        call.enqueue(new Callback<VerificationResponse>() {
            @Override
            public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                if(response.isSuccessful()){
                    VerificationResponse verificationResponse = response.body();
                    if(verificationResponse != null){
                        if(verificationResponse.getError() != null && !verificationResponse.getError().isEmpty()){
                            textViewVerificationMessage.setText(verificationResponse.getError());
                        } else {
                            textViewVerificationMessage.setText(verificationResponse.getMessage());
                            buttonReturnToLogin.setVisibility(View.VISIBLE);
                        }
                    } else {
                        textViewVerificationMessage.setText("Unexpected response from server.");
                    }
                } else {
                    textViewVerificationMessage.setText("Verification failed. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<VerificationResponse> call, Throwable t) {
                textViewVerificationMessage.setText("Error: " + t.getMessage());
            }
        });
    }
}
