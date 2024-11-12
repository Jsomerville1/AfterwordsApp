// src/main/java/com/copteam22/afterwordsapp/models/MessageResponse.java

package com.copteam22.afterwordsapp.models;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

/**
 * Model class representing the response received when fetching messages.
 */
public class MessageResponse {

    @SerializedName("messages")
    private List<Message> messages;

    @SerializedName("error")
    private String error;

    /**
     * Default Constructor
     */
    public MessageResponse() {}

    /**
     * Parameterized Constructor
     *
     * @param messages List of Message objects.
     * @param error    Error message, if any.
     */
    public MessageResponse(List<Message> messages, String error) {
        this.messages = messages;
        this.error = error;
    }

    /**
     * Returns an unmodifiable list of messages to prevent external modification.
     *
     * @return List of Message objects.
     */
    public List<Message> getMessages() {
        return messages == null ? Collections.emptyList() : Collections.unmodifiableList(messages);
    }

    /**
     * Sets the list of messages.
     *
     * @param messages List of Message objects.
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Returns the error message, if any.
     *
     * @return Error message as a String.
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error message.
     *
     * @param error Error message as a String.
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Checks if the response contains an error.
     *
     * @return true if there's an error, false otherwise.
     */
    public boolean hasError() {
        return error != null && !error.isEmpty();
    }
}
