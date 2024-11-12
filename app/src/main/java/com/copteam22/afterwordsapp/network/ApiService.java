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

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body Map<String, String> loginRequest);

    @POST("/api/searchcards")
    Call<SearchCardsResponse> searchCards(@Body SearchCardsRequest searchCardsRequest);

    @POST("/api/addcard")
    Call<AddCardResponse> addCard(@Body AddCardRequest addCardRequest);

    @POST("/api/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("/api/verify")
    Call<VerificationResponse> verifyUser(@Body VerificationRequest verificationRequest);

    @POST("/api/getUserMessages")
    Call<MessageResponse> getUserMessages(@Body Map<String, Integer> request);

    @POST("/api/addmessage")
    Call<GenericResponse> addMessage(@Body Map<String, Object> request);

    @POST("/api/editmessage")
    Call<GenericResponse> editMessage(@Body Map<String, Object> request);

    @POST("/api/deletemessage")
    Call<GenericResponse> deleteMessage(@Body Map<String, Object> request);

    @POST("/api/addRecipient")
    Call<GenericResponse> addRecipient(@Body Map<String, Object> request);

    @POST("/api/editRecipient")
    Call<GenericResponse> editRecipient(@Body Map<String, Object> request);

    @POST("/api/deleteRecipient")
    Call<GenericResponse> deleteRecipient(@Body Map<String, Object> request);

    @POST("/api/deleteUsers")
    Call<GenericResponse> deleteUser(@Body Map<String, Integer> request);
}