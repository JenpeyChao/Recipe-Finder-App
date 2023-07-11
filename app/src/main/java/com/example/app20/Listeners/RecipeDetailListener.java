package com.example.app20.Listeners;

import com.example.app20.Models.RecipeDetailsResponse;

public interface RecipeDetailListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
