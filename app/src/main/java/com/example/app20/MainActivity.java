package com.example.app20;

import android.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app20.Adapters.RandomRecipeAdapter;
import com.example.app20.Listeners.RandomRecipeResponseListener;
import com.example.app20.Listeners.RecipeClickListener;
import com.example.app20.Models.RandomRecipeApiResponse;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity{
    ProgressBar dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(progressBar);
        builder.setTitle("Loading...");
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.dismiss();

        searchView = findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                tags.clear();
                tags.add(s);
                manager.getRandomRecipes(randomRecipeResponseListener,tags);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );

        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new RequestManager(this);


//        manager.getRandomRecipes(randomRecipeResponseListener);
//        dialog.dismiss();


    }
    RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse Response, String message) {
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this,Response.recipes,recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };



    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());

            manager.getRandomRecipes(randomRecipeResponseListener,tags);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivities(new Intent[]{new Intent(MainActivity.this, RecipeDetailsActivity.class).putExtra("id", id)});

        }
    };
}
