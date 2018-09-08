package com.app.tuskan.moviestage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.poster_image_view)
    ImageView posterImageView;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.date_text_view)
    TextView dateTextView;

    @BindView(R.id.vote_text_view)
    TextView voteTextView;

    @BindView(R.id.overview_text_view)
    TextView overviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        String poster = intent.getStringExtra("poster");
        String title = intent.getStringExtra("title");
        String release_date = intent.getStringExtra("release_date");
        String vote = intent.getStringExtra("vote");
        String overview = intent.getStringExtra("overview");

        Picasso.with(this)
                .load(poster)
                .error(R.mipmap.ic_launcher)
                .into(posterImageView);
        titleTextView.setText(title);
        dateTextView.setText(release_date);
        voteTextView.setText(vote);
        overviewTextView.setText(overview);
    }
}
