// app/src/main/java/com/copteam22/afterwordsapp/CardActivity.java
package com.copteam22.afterwordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.copteam22.afterwordsapp.models.AddCardRequest;
import com.copteam22.afterwordsapp.models.AddCardResponse;
import com.copteam22.afterwordsapp.models.SearchCardsRequest;
import com.copteam22.afterwordsapp.models.SearchCardsResponse;
import com.copteam22.afterwordsapp.network.ApiService;
import com.copteam22.afterwordsapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardActivity extends AppCompatActivity {

    private TextView textViewLoggedInUser;
    private Button buttonLogout;
    private EditText editTextSearch, editTextAddCard;
    private Button buttonSearchCard, buttonAddCard;
    private TextView textViewSearchResult, textViewAddCardMessage;
    private ApiService apiService;
    private static final String BASE_URL = "http://161.35.116.218:5000"; // Replace with your server's IP and port
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // Initialize views
        textViewLoggedInUser = findViewById(R.id.textViewLoggedInUser);
        buttonLogout = findViewById(R.id.buttonLogout);
        editTextSearch = findViewById(R.id.editTextSearch);
        editTextAddCard = findViewById(R.id.editTextAddCard);
        buttonSearchCard = findViewById(R.id.buttonSearchCard);
        buttonAddCard = findViewById(R.id.buttonAddCard);
        textViewSearchResult = findViewById(R.id.textViewSearchResult);
        textViewAddCardMessage = findViewById(R.id.textViewAddCardMessage);

        // Initialize Retrofit
        apiService = RetrofitClient.getClient(BASE_URL).create(ApiService.class);

        // Get user data
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        if(user != null){
            userId = String.valueOf(user.getId());
            textViewLoggedInUser.setText(String.format("%s%s %s", getString(R.string.logged_in_as), user.getFirstName(), user.getLastName()));
        } else {
            // Redirect to login if user data is not found
            Intent intent = new Intent(CardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // Set listeners
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                Intent intent = new Intent(CardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSearchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCard();
            }
        });

        buttonAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCard();
            }
        });
    }

    private void searchCard(){
        String searchQuery = editTextSearch.getText().toString().trim();

        if(searchQuery.isEmpty()){
            textViewSearchResult.setText("Please enter a search query.");
            return;
        }

        SearchCardsRequest searchCardsRequest = new SearchCardsRequest(userId, searchQuery);
        Call<SearchCardsResponse> call = apiService.searchCards(searchCardsRequest);
        call.enqueue(new Callback<SearchCardsResponse>() {
            @Override
            public void onResponse(Call<SearchCardsResponse> call, Response<SearchCardsResponse> response) {
                Log.d("FadeMove", "onResponse called");

                if(response.isSuccessful()){
                    SearchCardsResponse searchCardsResponse = response.body();
                    if(searchCardsResponse != null){
                        if(searchCardsResponse.getError() != null && !searchCardsResponse.getError().isEmpty()){
                            textViewSearchResult.setText(searchCardsResponse.getError());
                        } else {
                            StringBuilder resultBuilder = new StringBuilder();
                            for(String card : searchCardsResponse.getResults()){
                                resultBuilder.append(card).append(", ");
                            }
                            String resultText = resultBuilder.toString();
                            if(resultText.endsWith(", ")){
                                resultText = resultText.substring(0, resultText.length() - 2);
                            }
                            textViewSearchResult.setText("Cards: " + resultText);
                        }
                    } else {
                        textViewSearchResult.setText("Unexpected response from server.");
                    }
                } else {
                    textViewSearchResult.setText("Search failed. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<SearchCardsResponse> call, Throwable t) {
                textViewSearchResult.setText("Error: " + t.getMessage());
            }
        });
    }

    private void addCard(){
        String cardName = editTextAddCard.getText().toString().trim();

        if(cardName.isEmpty()){
            textViewAddCardMessage.setText("Please enter a card name.");
            return;
        }

        AddCardRequest addCardRequest = new AddCardRequest(userId, cardName);
        Call<AddCardResponse> call = apiService.addCard(addCardRequest);
        call.enqueue(new Callback<AddCardResponse>() {
            @Override
            public void onResponse(Call<AddCardResponse> call, Response<AddCardResponse> response) {
                if(response.isSuccessful()){
                    AddCardResponse addCardResponse = response.body();
                    if(addCardResponse != null){
                        if(addCardResponse.getError() != null && !addCardResponse.getError().isEmpty()){
                            textViewAddCardMessage.setText("API Error: " + addCardResponse.getError());
                        } else {
                            textViewAddCardMessage.setText("Card has been added.");
                        }
                    } else {
                        textViewAddCardMessage.setText("Unexpected response from server.");
                    }
                } else {
                    textViewAddCardMessage.setText("Add card failed. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<AddCardResponse> call, Throwable t) {
                textViewAddCardMessage.setText("Error: " + t.getMessage());
            }
        });
    }
}
