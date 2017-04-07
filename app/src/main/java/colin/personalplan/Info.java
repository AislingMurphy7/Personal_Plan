package colin.personalplan;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static colin.personalplan.R.id.video;

/**
 * Created by Colin on 20/03/2017.
 */

public class Info extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    ImageView picCapture;
    ImageView vidCapture;
    ImageView display;
    VideoView vid;
    String name;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);



        toolbar.setOnTouchListener(new OnSwipeTouchListener(Info.this)
        {
            public void onSwipeRight()
            {
                Intent intent = new Intent(Info.this, Video.class);
                intent.putExtra("r", "https://player.vimeo.com/video/210621803");
                startActivity(intent);
            }
            public void onSwipeLeft()
            {
                new AlertDialog.Builder(Info.this)
                        .setTitle("Change picture")
                        .setMessage("Are you sure you want to change the icon?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                name = "0.jpg";
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

            }
        });

        toolbar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                new AlertDialog.Builder(Info.this)
                        .setTitle("Change picture")
                        .setMessage("Are you sure you want to change the icon?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                name = "0.jpg";
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


        picCapture = (ImageView) findViewById(R.id.photo);
        picCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = "infopic.jpg";
                dispatchTakePictureIntent();
            }
        });

        display = (ImageView) findViewById(R.id.camera);
        String path = "/data/user/0/colin.personalplan/app_imageDir/";
        try
        {

                File f = new File(path, "infopic.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                display.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        vidCapture = (ImageView) findViewById(R.id.videocamera);
        vidCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = "infovid.mp4";
                dispatchTakeVideoIntent();
            }
        });

        vid = (VideoView) findViewById(video);

        String url = "/data/user/0/colin.personalplan/app_imageDir/infovid.mp4";
        Uri uri = Uri.parse(url); //Declare your url here.

        vid.setMediaController(new MediaController(this));
        vid.setVideoURI(uri);
        vid.requestFocus();
        vid.start();
    }

    public void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            saveToInternalStorage(imageBitmap);
            if(name == "infopic.jpg")
            {
                display.setImageBitmap(imageBitmap);

            }//end if
        }

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            vid.setVideoURI(videoUri);
            saveVideo(videoUri);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        //make the file name album + artwork, lowercase with no spaces.
        //String name = "profile.jpg";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

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

    protected void saveVideo(final Uri uriVideo){

        boolean success = false;
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // make the directory
        File mypath = new File(directory,name);


        try {
            mypath.createNewFile();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (success) {
            Toast.makeText(getApplicationContext(), "Video saved!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Error during video saving", Toast.LENGTH_LONG).show();
        }

    }


}
