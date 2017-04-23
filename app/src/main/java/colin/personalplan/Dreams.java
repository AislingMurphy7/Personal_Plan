package colin.personalplan;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Colin on 20/03/2017.
 *
 * Video recording has been adapted from https://github.com/codepath/android_guides/wiki/Video-Playback-and-Recording
 */

public class Dreams extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    ImageView picCapture;
    ImageView vidCapture;
    ImageView micCapture;
    ImageView picDisplay;
    ImageView vidDisplay;
    ImageView micDisplay;
    VideoView vid;
    String name;
    private static final int VIDEO_CAPTURE = 101;
    Uri videoUri;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);



        toolbar.setOnTouchListener(new OnSwipeTouchListener(Dreams.this)
        {
            public void onSwipeRight()
            {
                Intent intent = new Intent(Dreams.this, WebVideo.class);
                intent.putExtra("r", "https://player.vimeo.com/video/210621803");
                startActivity(intent);
            }
            public void onSwipeLeft()
            {
                new AlertDialog.Builder(Dreams.this)
                        .setTitle("Change picture")
                        .setMessage("Are you sure you want to change the icon?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                name = "1.jpg";
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
                new AlertDialog.Builder(Dreams.this)
                        .setTitle("Change picture")
                        .setMessage("Are you sure you want to change the icon?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                name = "1.jpg";
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
                name = "dreamspic.jpg";
                dispatchTakePictureIntent();
            }
        });

        picDisplay = (ImageView) findViewById(R.id.camera);
        String path = "/data/user/0/colin.personalplan/app_imageDir/";
        try
        {

            File f = new File(path, "dreamspic.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            picDisplay.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


        picDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dreams.this, DisplayImage.class);
                intent.putExtra("r", "dreamspic.jpg");
                startActivity(intent);
            }
        });

        vidCapture = (ImageView) findViewById(R.id.videocamera);
        vidCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                name = "dreamsvid.mp4";
                startRecordingVideo();
            }
        });

        vidDisplay = (ImageView) findViewById(R.id.video);
        try
        {

            File f = new File(path, "dreamsopic.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            picDisplay.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


        vidDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dreams.this, DisplayVideo.class);
                intent.putExtra("r", "dreamsvid.mp4");
                startActivity(intent);
            }
        });
        String vidpath = "/data/user/0/colin.personalplan/app_imageDir/infovid.mp4";
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
        vidDisplay.setImageBitmap(thumb);

        micCapture = (ImageView) findViewById(R.id.microphone);

        micDisplay = (ImageView) findViewById(R.id.mic);

        micCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dreams.this, RecordAudio.class);
                startActivity(intent);
            }
        });
    }//end oncreate

    public void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
    public void startRecordingVideo() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            File mediaFile = new File("/data/user/0/colin.personalplan/app_imageDir/" + "dreamsvid.mp4");
            videoUri = Uri.fromFile(mediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
            startActivityForResult(intent, VIDEO_CAPTURE);
        }
        else
        {
            Toast.makeText(this, "No camera on device", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            saveToInternalStorage(imageBitmap);
            if(name == "dreamspic.jpg")
            {
                picDisplay.setImageBitmap(imageBitmap);

            }//end if
        }
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video has been saved ", Toast.LENGTH_LONG).show();
                //playbackRecordedVideo();

                String path = "/data/user/0/colin.personalplan/app_imageDir/";
                String video = "dreamsvid.mp4";


            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",  Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",  Toast.LENGTH_LONG).show();
            }
        }
    }


    private String saveToInternalStorage(Bitmap bitmapImage)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

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


}
