// src/main/java/com/copteam22/afterwordsapp/adapters/RecipientAdapter.java

package com.copteam22.afterwordsapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.copteam22.afterwordsapp.R;
import com.copteam22.afterwordsapp.network.GenericResponse;
import com.copteam22.afterwordsapp.models.Recipient;
import com.copteam22.afterwordsapp.network.ApiService;
import com.copteam22.afterwordsapp.network.RetrofitClient;
import com.copteam22.afterwordsapp.SharedPrefManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * RecyclerView Adapter for displaying a list of Recipients.
 * Allows editing and deleting recipients via API calls.
 */
public class RecipientAdapter extends RecyclerView.Adapter<RecipientAdapter.RecipientViewHolder> {

    private List<Recipient> recipientList;
    private Context context;
    private ApiService apiService;
    private SharedPrefManager sharedPrefManager;

    /**
     * Constructor for RecipientAdapter.
     *
     * @param recipientList List of Recipient objects to display.
     * @param context       Context from the calling Activity or Fragment.
     */
    public RecipientAdapter(List<Recipient> recipientList, Context context) {
        this.recipientList = recipientList;
        this.context = context;
        // Initialize Retrofit and ApiService
        this.apiService = RetrofitClient.getClient("http://161.35.116.218:5000").create(ApiService.class);
        // Initialize SharedPrefManager
        this.sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    @Override
    public RecipientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the recipient item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipient, parent, false);
        return new RecipientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipientViewHolder holder, int position) {
        Recipient recipient = recipientList.get(position);
        holder.textViewRecipientName.setText(recipient.getRecipientName());
        holder.textViewRecipientEmail.setText(recipient.getRecipientEmail());

        // Set up Edit button click listener
        holder.buttonEditRecipient.setOnClickListener(v -> {
            showEditRecipientDialog(recipient, position);
        });

        // Set up Delete button click listener
        holder.buttonDeleteRecipient.setOnClickListener(v -> {
            deleteRecipient(recipient.getRecipientId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return recipientList.size();
    }

    /**
     * ViewHolder class for Recipient items.
     */
    public class RecipientViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRecipientName, textViewRecipientEmail;
        Button buttonEditRecipient, buttonDeleteRecipient;

        public RecipientViewHolder(View itemView) {
            super(itemView);
            textViewRecipientName = itemView.findViewById(R.id.textViewRecipientName);
            textViewRecipientEmail = itemView.findViewById(R.id.textViewRecipientEmail);
            buttonEditRecipient = itemView.findViewById(R.id.buttonEditRecipient);
            buttonDeleteRecipient = itemView.findViewById(R.id.buttonDeleteRecipient);
        }
    }

    /**
     * Displays a dialog to edit recipient details.
     *
     * @param recipient Recipient object to edit.
     * @param position  Position of the recipient in the list.
     */
    private void showEditRecipientDialog(Recipient recipient, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Recipient");

        // Inflate the dialog layout
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_edit_recipient, null);
        final EditText inputName = viewInflated.findViewById(R.id.editTextEditRecipientName);
        final EditText inputEmail = viewInflated.findViewById(R.id.editTextEditRecipientEmail);

        // Pre-fill with existing data
        inputName.setText(recipient.getRecipientName());
        inputEmail.setText(recipient.getRecipientEmail());

        builder.setView(viewInflated);

        // Set up the Save button
        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedName = inputName.getText().toString().trim();
            String updatedEmail = inputEmail.getText().toString().trim();

            if (!updatedName.isEmpty() && !updatedEmail.isEmpty()) {
                // Prepare request body using a Map
                Map<String, Object> request = new HashMap<>();
                request.put("recipientId", recipient.getRecipientId());
                request.put("messageId", recipient.getMessageId());
                request.put("recipientName", updatedName);
                request.put("recipientEmail", updatedEmail);

                // Make API call to edit recipient
                Call<GenericResponse> call = apiService.editRecipient(request);
                call.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        if (response.isSuccessful()) {
                            GenericResponse res = response.body();
                            if (res != null && res.getError() == null) {
                                Toast.makeText(context, res.getMessage(), Toast.LENGTH_SHORT).show();
                                // Update local list and notify adapter
                                recipient.setRecipientName(updatedName);
                                recipient.setRecipientEmail(updatedEmail);
                                notifyItemChanged(position);
                            } else {
                                Toast.makeText(context, res != null ? res.getError() : "Unknown error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Failed to edit recipient", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, "All fields are required.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the Cancel button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    /**
     * Deletes a recipient after user confirmation.
     *
     * @param recipientId ID of the recipient to delete.
     * @param position    Position of the recipient in the list.
     */
    private void deleteRecipient(int recipientId, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Recipient")
                .setMessage("Are you sure you want to delete this recipient?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Prepare request body using a Map
                    Map<String, Object> request = new HashMap<>();
                    request.put("recipientId", recipientId);
                    request.put("messageId", getMessageIdForRecipient(recipientId));

                    // Make API call to delete recipient
                    Call<GenericResponse> call = apiService.deleteRecipient(request);
                    call.enqueue(new Callback<GenericResponse>() {
                        @Override
                        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                            if (response.isSuccessful()) {
                                GenericResponse res = response.body();
                                if (res != null && res.getError() == null) {
                                    Toast.makeText(context, res.getMessage(), Toast.LENGTH_SHORT).show();
                                    // Remove from local list and notify adapter
                                    recipientList.remove(position);
                                    notifyItemRemoved(position);
                                } else {
                                    Toast.makeText(context, res != null ? res.getError() : "Unknown error", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Failed to delete recipient", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GenericResponse> call, Throwable t) {
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Retrieves the messageId associated with a recipient.
     * Assuming each recipient has a single messageId.
     *
     * @param recipientId ID of the recipient.
     * @return The messageId associated with the recipient.
     */
    private int getMessageIdForRecipient(int recipientId) {
        for (Recipient recipient : recipientList) {
            if (recipient.getRecipientId() == recipientId) {
                return recipient.getMessageId();
            }
        }
        // Return a default value or handle appropriately if not found
        return -1;
    }
}
