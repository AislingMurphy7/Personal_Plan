package colin.personalplan;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Colin on 20/03/2017.
 */

public class Review extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dreams);

        Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setOnTouchListener(new OnSwipeTouchListener(Review.this) {
            public void onSwipeRight()
            {
                Intent intent = new Intent(Review.this, Video.class);
                intent.putExtra("r", "https://player.vimeo.com/video/210621803");
                startActivity(intent);
            }
            public void onSwipeLeft()
            {
                Intent intent = new Intent(Review.this, Video.class);
                intent.putExtra("r", "https://player.vimeo.com/video/210621803");
                startActivity(intent);
            }

        });
    }

}
