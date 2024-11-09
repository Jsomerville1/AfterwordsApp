// app/src/main/java/com/yourdomain/afterwordsapp/RegisterActivity.java
package com.copteam22.afterwordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.copteam22.afterwordsapp.models.RegisterRequest;
import com.copteam22.afterwordsapp.models.RegisterResponse;
import com.copteam22.afterwordsapp.network.ApiService;
import com.copteam22.afterwordsapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextRegisterUsername, editTextEmail, editTextRegisterPassword;
    private RadioGroup radioGroupCheckInFreq;
    private Button buttonRegisterSubmit;
    private TextView textViewRegisterMessage;
    private ApiService apiService;
    private static final String BASE_URL = "http://161.35.116.218:5000"; // Replace with your server's IP and port

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextRegisterUsername = findViewById(R.id.editTextRegisterUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        radioGroupCheckInFreq = findViewById(R.id.radioGroupCheckInFreq);
        buttonRegisterSubmit = findViewById(R.id.buttonRegisterSubmit);
        textViewRegisterMessage = findViewById(R.id.textViewRegisterMessage);

        // Initialize Retrofit
        apiService = RetrofitClient.getClient(BASE_URL).create(ApiService.class);

        // Set listener
        buttonRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String username = editTextRegisterUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextRegisterPassword.getText().toString().trim();

        // Get selected check-in frequency
        int selectedFreq = 0;
        int checkedRadioButtonId = radioGroupCheckInFreq.getCheckedRadioButtonId();
        if(checkedRadioButtonId != -1){
            RadioButton radioButton = findViewById(checkedRadioButtonId);
            selectedFreq = Integer.parseInt(radioButton.getText().toString().replaceAll("[^0-9]", ""));
            // Alternatively, you can assign values based on radioButton.getId()
        }

        if(firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || selectedFreq == 0){
            textViewRegisterMessage.setText("Please fill out all fields.");
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, username, email, password, selectedFreq);
        Call<RegisterResponse> call = apiService.registerUser(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    RegisterResponse registerResponse = response.body();
                    if(registerResponse != null){
                        if(registerResponse.getError() != null && !registerResponse.getError().isEmpty()){
                            textViewRegisterMessage.setText(registerResponse.getError());
                        } else {
                            // Navigate to VerificationActivity
                            Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                            intent.putExtra("Username", username);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        textViewRegisterMessage.setText("Unexpected response from server.");
                    }
                } else {
                    textViewRegisterMessage.setText("Registration failed. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                textViewRegisterMessage.setText("Error: " + t.getMessage());
            }
        });
    }
}
