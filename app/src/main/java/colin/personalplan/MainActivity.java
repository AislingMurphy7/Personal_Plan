package colin.personalplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Colin on 07/03/2017.
 */

public class MainActivity extends AppCompatActivity
{
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
    }
}