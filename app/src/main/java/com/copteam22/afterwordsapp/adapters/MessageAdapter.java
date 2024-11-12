// src/main/java/com/yourpackage/adapters/MessageAdapter.java

package com.copteam22.afterwordsapp.adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.copteam22.afterwordsapp.R;
import com.copteam22.afterwordsapp.network.GenericResponse;
import com.copteam22.afterwordsapp.models.Message;
import com.copteam22.afterwordsapp.network.ApiService;
import com.copteam22.afterwordsapp.network.RetrofitClient;
import com.copteam22.afterwordsapp.SharedPrefManager;
import com.copteam22.afterwordsapp.adapters.RecipientAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;
    private Context context;
    private ApiService apiService;
    private SharedPrefManager sharedPrefManager;
    private RecipientAdapter recipientAdapter;

    // Constructor
    public MessageAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
        this.apiService = RetrofitClient.getClient("http://161.35.116.218:5000").create(ApiService.class);
        this.sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.textViewContent.setText(message.getContent());
        holder.textViewCreatedAt.setText(MessageFormat.format("Created At: {0}", message.getCreatedAt()));

        // Initialize RecipientAdapter
        recipientAdapter = new RecipientAdapter(message.getRecipients(), context);
        holder.recyclerViewRecipients.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerViewRecipients.setAdapter(recipientAdapter);

        // Toggle recipients visibility
        holder.itemView.setOnClickListener(v -> {
            if(holder.recyclerViewRecipients.getVisibility() == View.GONE){
                holder.recyclerViewRecipients.setVisibility(View.VISIBLE);
            } else {
                holder.recyclerViewRecipients.setVisibility(View.GONE);
            }
        });

        // Edit Message Button
        holder.buttonEditMessage.setOnClickListener(v -> {
            showEditMessageDialog(message, position);
        });

        // Delete Message Button
        holder.buttonDeleteMessage.setOnClickListener(v -> {
            deleteMessage(message.getMessageId(), position);
        });

        // Add Recipient Button
        holder.buttonAddRecipient.setOnClickListener(v -> {
            showAddRecipientDialog(message.getMessageId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // ViewHolder Class
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent, textViewCreatedAt;
        Button buttonEditMessage, buttonDeleteMessage, buttonAddRecipient;
        RecyclerView recyclerViewRecipients;

        public MessageViewHolder(View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewCreatedAt = itemView.findViewById(R.id.textViewCreatedAt);
            buttonEditMessage = itemView.findViewById(R.id.buttonEditMessage);
            buttonDeleteMessage = itemView.findViewById(R.id.buttonDeleteMessage);
            buttonAddRecipient = itemView.findViewById(R.id.buttonAddRecipient);
            recyclerViewRecipients = itemView.findViewById(R.id.recyclerViewRecipients);
        }
    }

    // Method to show Edit Message Dialog
    private void showEditMessageDialog(Message message, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Message");

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_edit_message, null);
        final EditText inputContent = viewInflated.findViewById(R.id.editTextEditMessageContent);
        inputContent.setText(message.getContent());

        builder.setView(viewInflated);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedContent = inputContent.getText().toString().trim();

            if (!updatedContent.isEmpty()) {
                // Prepare request body
                Map<String, Object> request = new HashMap<>();
                request.put("messageId", message.getMessageId());
                request.put("userId", sharedPrefManager.getUser().getId());
                request.put("content", updatedContent);

                // Make API call to edit message
                Call<GenericResponse> call = apiService.editMessage(request);
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {
                        if (response.isSuccessful()) {
                            GenericResponse res = response.body();
                            if (res != null && res.getError() == null) {
                                Toast.makeText(context, res.getMessage(), Toast.LENGTH_SHORT).show();
                                // Update local list and notify adapter
                                message.setContent(updatedContent);
                                notifyItemChanged(position);
                            } else {
                                Toast.makeText(context, res != null ? res.getError() : "Unknown error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Failed to edit message", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, "Content cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Method to delete a message
    private void deleteMessage(int messageId, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Message")
                .setMessage("Are you sure you want to delete this message?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Prepare request body
                    Map<String, Object> request = new HashMap<>();
                    request.put("messageId", messageId);
                    request.put("userId", sharedPrefManager.getUser().getId());

                    // Make API call to delete message
                    Call<GenericResponse> call = apiService.deleteMessage(request);
                    call.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {
                            if (response.isSuccessful()) {
                                GenericResponse res = response.body();
                                if (res != null && res.getError() == null) {
                                    Toast.makeText(context, res.getMessage(), Toast.LENGTH_SHORT).show();
                                    // Remove from local list and notify adapter
                                    messageList.remove(position);
                                    notifyItemRemoved(position);
                                } else {
                                    Toast.makeText(context, res != null ? res.getError() : "Unknown error", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Failed to delete message", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Method to show Add Recipient Dialog
    private void showAddRecipientDialog(int messageId, int messagePosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Recipient");

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_add_recipient, null);
        final EditText inputName = viewInflated.findViewById(R.id.editTextRecipientName);
        final EditText inputEmail = viewInflated.findViewById(R.id.editTextRecipientEmail);

        builder.setView(viewInflated);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String recipientName = inputName.getText().toString().trim();
            String recipientEmail = inputEmail.getText().toString().trim();

            if (!recipientName.isEmpty() && !recipientEmail.isEmpty()) {
                // Prepare request body
                Map<String, Object> request = new HashMap<>();
                request.put("username", sharedPrefManager.getUser().getUsername());
                request.put("recipientName", recipientName);
                request.put("recipientEmail", recipientEmail);
                request.put("messageId", messageId);

                // Make API call to add recipient
                Call<GenericResponse> call = apiService.addRecipient(request);
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {
                        if (response.isSuccessful()) {
                            GenericResponse res = response.body();
                            if (res != null && res.getError() == null) {
                                Toast.makeText(context, res.getMessage(), Toast.LENGTH_SHORT).show();
                                // Optionally, refresh the message list or update UI
                                // For simplicity, you might want to notify the parent activity to refresh
                            } else {
                                Toast.makeText(context, res != null ? res.getError() : "Unknown error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Failed to add recipient", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, "All fields are required.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
