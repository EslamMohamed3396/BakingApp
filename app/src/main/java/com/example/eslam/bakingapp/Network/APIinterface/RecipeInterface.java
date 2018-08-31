package com.example.eslam.bakingapp.Network.APIinterface;

import com.example.eslam.bakingapp.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeInterface {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> recipelist();

}
