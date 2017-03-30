package colin.personalplan;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Colin on 20/03/2017.
 */

public class Info extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView capture;
    ImageView display;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setOnTouchListener(new OnSwipeTouchListener(Info.this) {
            public void onSwipeRight()
            {
                Intent intent = new Intent(Info.this, Video.class);
                intent.putExtra("r", "https://player.vimeo.com/video/210621803");
                startActivity(intent);
            }
            public void onSwipeLeft()
            {
                Intent intent = new Intent(Info.this, Video.class);
                intent.putExtra("r", "https://player.vimeo.com/video/210621803");
                startActivity(intent);
            }

        });

        capture = (ImageView) findViewById(R.id.photo);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dispatchTakePictureIntent();
            }
        });

        display = (ImageView) findViewById(R.id.camera);
        String path = "/data/user/0/colin.personalplan/app_imageDir/";
        String name = "profile.jpg";
        try
        {
            File f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            display.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            saveToInternalStorage(imageBitmap);
            display.setImageBitmap(imageBitmap);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        //make the file name album + artwork, lowercase with no spaces.
        //String name = "profile.jpg";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String name = timeStamp + ".jpg";

        File mypath = new File(directory,name);

        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return directory.getAbsolutePath();
    }
}
