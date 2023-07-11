package com.example.app20.Listeners;

import com.example.app20.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse Response, String message);
    void didError(String message);
}
