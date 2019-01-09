package hva.recipe;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApiService {
    String BASE_URL = "https://www.food2fork.com/api/";
    String API_KEY = "db406ac8cefe6f6c0eeac6d56ab2a1da";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("search?key=" + API_KEY + "&sort=r")
    Call<RecipeWrapper> getTopRecipes();

    @GET("get?key=" + API_KEY)
    Call<RecipeWrapper> getIngredients (@Query("rId") String rId);
}
