package com.example.app20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app20.Adapters.IngredientsAdapter;
import com.example.app20.Listeners.RecipeDetailListener;
import com.example.app20.Models.RecipeDetailsResponse;
import com.squareup.picasso.Picasso;

public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    TextView textView_meal_name,textView_meal_source,textView_meal_summary;
    ImageView ImageView_meal_image;
    RecyclerView recycler_meal_ingredients;
    RequestManager manager;
    IngredientsAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        findViews();
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailListener,id);
    }

    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        ImageView_meal_image = findViewById(R.id.ImageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);

    }
    private final RecipeDetailListener recipeDetailListener = new RecipeDetailListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText((response.summary));
            Picasso.get().load(response.image).into(ImageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL,false));
            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this,response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    };
}