package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView description;
    private List<String> ingredientsList;
    private TextView ingredients;
    private TextView placeOfOriginTv;
    private List<String> akaList;
    private TextView akaTv;
    private TextView akaLabel;
    private TextView ingredientsLabel;
    private TextView placeOfOriginLabel;
    private TextView descriptionLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        description = findViewById(R.id.description_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        akaTv = findViewById(R.id.also_known_tv);
        akaLabel = findViewById(R.id.aka_label);
        ingredientsLabel = findViewById(R.id.ingredients_label);
        placeOfOriginLabel = findViewById(R.id.place_of_origin_label);
        descriptionLabel = findViewById(R.id.description_label);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            Toast.makeText(this, "sandwichnull", Toast.LENGTH_SHORT);
            closeOnError();
            return;
        }

        populateUI(sandwich);
        hideLabels(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        description.setText(sandwich.getDescription());
        ingredientsList = sandwich.getIngredients();
        String ingredientFromIndex;
        for (int i = 0; i<ingredientsList.size(); i++) {
            ingredientFromIndex = ingredientsList.get(i);
            ingredients.append(ingredientFromIndex + "\n");
        }
        akaList = sandwich.getAlsoKnownAs();
        String akaFromIndex;
        for (int i = 0; i<akaList.size(); i++) {
            akaFromIndex = akaList.get(i);
            akaTv.append(akaFromIndex + "\n");
        }
        String placeOfOriginReturned = sandwich.getPlaceOfOrigin();
        if (placeOfOriginReturned == ""){
            placeOfOriginTv.setText(placeOfOriginReturned);
        }
        else {
            placeOfOriginTv.setText(placeOfOriginReturned + "\n");
        }

    }

    private void hideLabels(Sandwich sandwich) {
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            akaLabel.setVisibility(View.VISIBLE);
        }
        if (!sandwich.getIngredients().isEmpty()) {
            ingredientsLabel.setVisibility(View.VISIBLE);
        }
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginLabel.setVisibility(View.VISIBLE);
        }
        if (!sandwich.getDescription().isEmpty()) {
            descriptionLabel.setVisibility(View.VISIBLE);
        }
    }
}
