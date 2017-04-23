package colin.personalplan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;


/**
 * Created by Colin on 18/04/2017.
 */

public class DisplayVideo extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayvideo);
        Intent i = getIntent();

        String video = i.getStringExtra("r");
        String path = "/data/user/0/colin.personalplan/app_imageDir/";

        VideoView display = (VideoView) findViewById(R.id.displayvid);
        File mediaFile = new File(path+video);

        if(mediaFile == null || !mediaFile.exists())
        {
            Toast.makeText(this, "works", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "no work", Toast.LENGTH_LONG).show();
        }

        Uri uri = Uri.fromFile(mediaFile);
        display.setVideoURI(uri);
        display.setMediaController(new MediaController(this));
        display.requestFocus();
        display.start();

    }
}
