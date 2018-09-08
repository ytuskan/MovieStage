package com.app.tuskan.moviestage.utilities;

import com.app.tuskan.moviestage.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yakup on 25.5.2018.
 */

public class JsonUtils {
    private final static String RESULTS = "results";
    private final static String TITLE = "title";
    private final static String RELEASE_DATE = "release_date";
    private final static String POSTER_PATH = "poster_path";
    private final static String BACKDROP_PATH = "backdrop_path";
    private final static String VOTE_AVARAGE = "vote_average";
    private final static String PLOT_SYNOPSIS = "overview";
    private final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";


    public static ArrayList<Movie> parseSandwichJson(String json) throws JSONException {

        ArrayList<Movie> movies = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonResultArray = jsonObject.optJSONArray(RESULTS);

        for(int i = 0; i < jsonResultArray.length(); i++){

            Movie movie = new Movie();

            JSONObject jsonValueObject = jsonResultArray.getJSONObject(i);

            movie.setTitle(jsonValueObject.optString(TITLE));
            movie.setReleaseDate(jsonValueObject.optString(RELEASE_DATE));
            movie.setMoviePoster(IMAGE_BASE_URL + jsonValueObject.optString(POSTER_PATH));
            movie.setBackdrop_path(IMAGE_BASE_URL + jsonValueObject.optString(BACKDROP_PATH));
            movie.setVoteAvarage(jsonValueObject.optString(VOTE_AVARAGE));
            movie.setPlotSynopsis(jsonValueObject.optString(PLOT_SYNOPSIS));

            movies.add(movie);
        }

        return movies;
    }

}
