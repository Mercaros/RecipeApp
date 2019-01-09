package hva.recipe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static List<Recipe> recipes = new ArrayList<>();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//         Create the adapter that will return a fragment for each of the three
//         primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        requestTopRecipes();

    }

    private void requestTopRecipes() {
        /**
         * Make an a-synchronous call by enqueing and definition of callbacks.
         */
        RecipeApiService service = RecipeApiService.retrofit.create(RecipeApiService.class);
        Call<RecipeWrapper> call = service.getTopRecipes();
        call.enqueue(new Callback<RecipeWrapper>() {
            @Override
            public void onResponse(@NonNull Call<RecipeWrapper> call, @NonNull Response<RecipeWrapper> response) {
                RecipeWrapper recipeWrapper = response.body();
                assert recipeWrapper != null;
                recipes = recipeWrapper.getRecipes();
                if (recipes != null) {
                    if (recipes.size() > 3) {
                        recipes = recipes.subList(0, 3);
                    }
                    mSectionsPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RecipeWrapper> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        ListView ingredients;
        ArrayAdapter<String> adapter;
        TextView title;
        ImageView imageView;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            imageView = rootView.findViewById(R.id.imageView);
            title = rootView.findViewById(R.id.recipe_title);
            ingredients = rootView.findViewById(R.id.ingredients);

            if (recipes.get(getArguments().getInt(ARG_SECTION_NUMBER)).getIngredients() == null) {
                getRecipeIngredients();
            }
            else {
                title.setText(recipes.get(getArguments().getInt(ARG_SECTION_NUMBER)).getTitle());
                Glide.with(this).load(recipes.get(getArguments().getInt(ARG_SECTION_NUMBER)).getImageUrl())
                        .into(imageView);
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,recipes.get(getArguments()
                                .getInt(ARG_SECTION_NUMBER)).getIngredients());
                ingredients.setAdapter(adapter);
            }
            return rootView;
        }

        private void getRecipeIngredients() {
            RecipeApiService service = RecipeApiService.retrofit.create(RecipeApiService.class);
            /**
             * Make an a-synchronous call by enqueing and definition of callbacks.
             */
            Call<RecipeWrapper> call = service.getIngredients(recipes.get(getArguments()
                    .getInt(ARG_SECTION_NUMBER)).getRecipeId());
            call.enqueue(new Callback<RecipeWrapper>() {
                @Override
                public void onResponse(@NonNull Call<RecipeWrapper> call, @NonNull Response<RecipeWrapper> response) {
                    recipes.get(getArguments()
                            .getInt(ARG_SECTION_NUMBER))
                            .setIngredients(response.body().getRecipe().getIngredients());

                    title.setText(recipes.get(getArguments().getInt(ARG_SECTION_NUMBER)).getTitle());
                    Glide.with(getContext())
                            .load(recipes.get(getArguments().getInt(ARG_SECTION_NUMBER)).getImageUrl())
                            .into(imageView);

                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
                            recipes.get(getArguments()
                                    .getInt(ARG_SECTION_NUMBER)).getIngredients());
                    ingredients.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<RecipeWrapper> call, Throwable t) {
                    Log.d("Error", t.toString());
                }
            });

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return recipes.size();
        }
    }
}
