package hva.recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {
    @SerializedName("recipe_id")
    @Expose
    private String recipeId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("ingredients")
    @Expose
    private List<String> ingredients;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId='" + recipeId + '\'' +
                ", title='" + title + '\'' +
                ", ingredients=" + ingredients +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
