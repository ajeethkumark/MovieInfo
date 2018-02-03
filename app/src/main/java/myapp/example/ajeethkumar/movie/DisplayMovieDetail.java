package myapp.example.ajeethkumar.movie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DisplayMovieDetail extends AppCompatActivity {
    ImageView image;
    TextView title,date,overView,voteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie_detail);
        image=(ImageView)findViewById(R.id.image);
        title=(TextView)findViewById(R.id.title);
        date=(TextView)findViewById(R.id.date);
        overView=(TextView)findViewById(R.id.overview);
        voteCount=(TextView)findViewById(R.id.vote);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            Glide.with(DisplayMovieDetail.this).load("http://image.tmdb.org/t/p/w500/"+bundle.getString("url")).into(image);
            title.setText(bundle.getString("title"));
            date.setText("ReleaseDate: "+bundle.getString("date"));
            overView.setText("overview:           "+bundle.getString("overView"));
            voteCount.setText("VoteCount:"+bundle.getString("vote"));
            //vote
            //release_date
        }

    }
}
