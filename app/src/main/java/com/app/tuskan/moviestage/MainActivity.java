package com.app.tuskan.moviestage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.tuskan.moviestage.adapters.MovieAdapter;
import com.app.tuskan.moviestage.models.Movie;
import com.app.tuskan.moviestage.utilities.JsonUtils;
import com.app.tuskan.moviestage.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final static String MVDB_POPULAR_BASE_URL =
            "https://api.themoviedb.org/3/movie/popular?";

    private final static String MVDB_TOPRATED_BASE_URL =
            "https://api.themoviedb.org/3/movie/top_rated?";

    @BindView(R.id.loading_indicator_progressbar)
    ProgressBar mLoadingIndicator;

    @BindView(R.id.movie_list_recycler_view)
    RecyclerView movieListRecyclerView;

    private ArrayList<Movie> movies = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public class MVDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String mvdbSearchResults = null;
            try {
                mvdbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mvdbSearchResults;
        }

        @Override
        protected void onPostExecute(String mvdbSearchResults) {

            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (mvdbSearchResults != null && !mvdbSearchResults.equals("")) {

                try {
                    movies = JsonUtils.parseSandwichJson(mvdbSearchResults);
                    initRecyclerView(movies);
                    makeMvdbSearchQuery(MVDB_POPULAR_BASE_URL, "2820a34e396320469b1919f4a4f0bb97");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void makeMvdbSearchQuery(String sortType, String apiKey) {
        URL mvdbSearchUrl = NetworkUtils.buildUrl(sortType, apiKey);
        new MVDBQueryTask().execute(mvdbSearchUrl);
    }

    private void initRecyclerView(ArrayList<Movie> list) {
        movieAdapter = new MovieAdapter(this, list);
        movieListRecyclerView.setAdapter(movieAdapter);
        layoutManager = new GridLayoutManager(this, numberOfColumns());
        movieListRecyclerView.setLayoutManager(layoutManager);
        movieListRecyclerView.setHasFixedSize(true);
        movieListRecyclerView.scrollToPosition(1);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_higgest_rate:
                makeMvdbSearchQuery(MVDB_TOPRATED_BASE_URL, "2820a34e396320469b1919f4a4f0bb97");
                Toast.makeText(this, R.string.rating_information, Toast.LENGTH_LONG).show();
                break;
            case R.id.action_most_popular:
                makeMvdbSearchQuery(MVDB_POPULAR_BASE_URL, "2820a34e396320469b1919f4a4f0bb97");
                Toast.makeText(this, R.string.popularity_information, Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
