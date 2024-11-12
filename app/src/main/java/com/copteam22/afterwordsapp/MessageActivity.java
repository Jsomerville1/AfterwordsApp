package com.copteam22.afterwordsapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.copteam22.afterwordsapp.adapters.MessageAdapter;
import com.copteam22.afterwordsapp.models.Message;
import com.copteam22.afterwordsapp.models.MessageResponse;
import com.copteam22.afterwordsapp.network.ApiService;
import com.copteam22.afterwordsapp.network.GenericResponse;
import com.copteam22.afterwordsapp.network.RetrofitClient;
import com.copteam22.afterwordsapp.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMessages;
    private MessageAdapter messageAdapter;
    private ApiService apiService;
    private SharedPrefManager sharedPrefManager;
    private Button buttonAddMessage, buttonReturnToProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        buttonAddMessage = findViewById(R.id.buttonAddMessage);
        buttonReturnToProfile = findViewById(R.id.buttonReturnToProfile);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getClient("http://161.35.116.218:5000").create(ApiService.class);
        sharedPrefManager = SharedPrefManager.getInstance(this);

        fetchMessages();

        buttonAddMessage.setOnClickListener(v -> showAddMessageDialog());
        buttonReturnToProfile.setOnClickListener(view -> showProfile());
    }

    private void showProfile() {
        Intent intent = new Intent(MessageActivity.this, UserProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchMessages() {
        int userId = sharedPrefManager.getUser().getId();
        Map<String, Integer> request = new HashMap<>();
        request.put("userId", userId);

        Call<MessageResponse> call = apiService.getUserMessages(request);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    if (messageResponse != null && messageResponse.getError() == null) {
                        List<Message> messages = messageResponse.getMessages();
                        messageAdapter = new MessageAdapter(messages, MessageActivity.this);
                        recyclerViewMessages.setAdapter(messageAdapter);
                    } else {
                        Toast.makeText(MessageActivity.this, messageResponse != null ? messageResponse.getError() : "Unknown error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MessageActivity.this, "Failed to fetch messages", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(MessageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to show Add Message Dialog
    private void showAddMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Message");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_message, null);
        final EditText inputContent = viewInflated.findViewById(R.id.editTextEditMessageContent);
        inputContent.setHint("Enter message content");

        builder.setView(viewInflated);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String content = inputContent.getText().toString().trim();

            if (!content.isEmpty()) {
                // Prepare request body
                Map<String, Object> request = new HashMap<>();
                request.put("userId", sharedPrefManager.getUser().getId());
                request.put("content", content);

                // Make API call to add message
                Call<GenericResponse> call = apiService.addMessage(request);
                call.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        if (response.isSuccessful()) {
                            GenericResponse res = response.body();
                            if (res != null && res.getError() == null) {
                                Toast.makeText(MessageActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                                // Refresh messages list
                                fetchMessages();
                            } else {
                                Toast.makeText(MessageActivity.this, res != null ? res.getError() : "Unknown error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MessageActivity.this, "Failed to add message", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        Toast.makeText(MessageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Content cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}