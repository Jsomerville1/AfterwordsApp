// app/src/main/java/com/yourdomain/afterwordsapp/network/ApiService.java
package com.copteam22.afterwordsapp.network;

import com.copteam22.afterwordsapp.models.*;

import com.copteam22.afterwordsapp.models.AddCardRequest;
import com.copteam22.afterwordsapp.models.LoginRequest;
import com.copteam22.afterwordsapp.models.LoginResponse;
import com.copteam22.afterwordsapp.models.RegisterRequest;
import com.copteam22.afterwordsapp.models.RegisterResponse;
import com.copteam22.afterwordsapp.models.SearchCardsRequest;
import com.copteam22.afterwordsapp.models.VerificationRequest;
import com.copteam22.afterwordsapp.models.VerificationResponse;
import com.copteam22.afterwordsapp.models.AddCardResponse;
import com.copteam22.afterwordsapp.models.SearchCardsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("/api/verify")
    Call<VerificationResponse> verifyUser(@Body VerificationRequest verificationRequest);

    @POST("/api/addcard")
    Call<AddCardResponse> addCard(@Body AddCardRequest addCardRequest);

    @POST("/api/searchcards")
    Call<SearchCardsResponse> searchCards(@Body SearchCardsRequest searchCardsRequest);
}
