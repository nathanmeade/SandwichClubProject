package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            String sandwichName1 = name.getString("mainName");
            JSONArray sandwichName2 = name.getJSONArray("alsoKnownAs");
            List<String> akas = new ArrayList<>();
            for (int i=0; i<sandwichName2.length(); i++) {
                akas.add(sandwichName2.getString(i));
            }
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");
            JSONArray ingredients =  jsonObject.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i=0; i<ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }
            sandwich = new Sandwich(sandwichName1, akas, placeOfOrigin, description, image, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
