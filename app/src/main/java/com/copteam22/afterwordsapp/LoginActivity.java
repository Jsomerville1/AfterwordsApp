// app/src/main/java/com/yourdomain/afterwordsapp/LoginActivity.java
package com.copteam22.afterwordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.copteam22.afterwordsapp.models.LoginRequest;
import com.copteam22.afterwordsapp.models.LoginResponse;
import com.copteam22.afterwordsapp.network.ApiService;
import com.copteam22.afterwordsapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private TextView textViewMessage;
    private ApiService apiService;
    private static final String BASE_URL = "http://161.35.116.218:5000"; // Replace with your server's IP and port

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewMessage = findViewById(R.id.textViewMessage);

        // Initialize Retrofit
        apiService = RetrofitClient.getClient(BASE_URL).create(ApiService.class);

        // Set listeners
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(){
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){
            textViewMessage.setText("Please enter both username and password.");
            return;
        }


        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> call = apiService.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    if(loginResponse != null){
                        if(loginResponse.getError() != null && !loginResponse.getError().isEmpty()){
                            textViewMessage.setText(loginResponse.getError());
                        } else if(!loginResponse.isVerified()){
                            textViewMessage.setText("Please verify your account");
                        } else {
                            // Save user data and navigate to CardActivity
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .saveUser(loginResponse.getId(), loginResponse.getFirstName(),
                                            loginResponse.getLastName());

                            Intent intent = new Intent(LoginActivity.this, CardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } else {
                        textViewMessage.setText("Unexpected response from server.");
                    }
                } else {
                    textViewMessage.setText("Login failed. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                textViewMessage.setText("Error: " + t.getMessage());
            }
        }); 
    }
}
