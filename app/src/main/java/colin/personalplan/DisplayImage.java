package colin.personalplan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Colin on 18/04/2017.
 */

public class DisplayImage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayimage);
        Intent i = getIntent();

        String image = i.getStringExtra("r");
        String path = "/data/user/0/colin.personalplan/app_imageDir/";

        ImageView display = (ImageView) findViewById(R.id.imagedis);

        try {

            File f = new File(path, image);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            display.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
