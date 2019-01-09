package hva.recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hva.recipe.Recipe;

public class RecipeWrapper {
    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipes;

    @SerializedName("recipe")
    @Expose
    private Recipe recipe;

    public RecipeWrapper() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "RecipeWrapper{" +
                "count=" + count +
                ", recipes=" + recipes +
                ", recipe=" + recipe +
                '}';
    }
}
