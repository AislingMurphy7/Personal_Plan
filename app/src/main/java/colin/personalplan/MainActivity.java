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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Colin on 07/03/2017.
 */

public class MainActivity extends AppCompatActivity
{
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView capture;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button2 = (Button)findViewById(R.id.button2 );
        Button button1 = (Button)findViewById(R.id.button1);

        ImageButton imgButton1;
        ImageButton imgButton2;

        imgButton1 = (ImageButton)findViewById(R.id.imageView2);
        imgButton2 = (ImageButton)findViewById(R.id.imageView3);

        imgButton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(MainActivity.this, Categories.class);
                startActivity(intent);
            }
        });

        imgButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        button2.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        finish();
                    }
                });

        button1.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(MainActivity.this, Categories.class);
                        startActivity(intent);
                    }
                });

        capture = (ImageView) findViewById(R.id.camera);
        capture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Change picture")
                        .setMessage("Are you sure you want to change your profile picture?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dispatchTakePictureIntent();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_camera)
                        .show();
                return true;
            }
        });

        String path = "/data/user/0/colin.personalplan/app_imageDir/";
        String name = "profile.jpg";
        try
        {
            File f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            capture.setImageBitmap(b);
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
            capture.setImageBitmap(imageBitmap);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        //make the file name album + artwork, lowercase with no spaces.
        String name = "profile.jpg";

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