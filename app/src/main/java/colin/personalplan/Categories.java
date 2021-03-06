package colin.personalplan;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

public class Categories extends ListActivity implements TextToSpeech.OnInitListener {

    ListView list;
    TextToSpeech tts;
    int position;
    String[] titles =
            {
                    "Gather My Information",
                    "My Dreams",
                    "My Plan",
                    "Implementation",
                    "Review Of My Plan"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        Integer[] imgid =
        {
                R.drawable.info,
                R.drawable.dreams,
                R.drawable.myplan,
                R.drawable.implementation,
                R.drawable.review,
        };

        CustomListAdapter adapter=new CustomListAdapter(this, titles, imgid);
        list = (ListView)findViewById(android.R.id.list);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {

                String itemValue = (String) list.getItemAtPosition(index);

                Toast.makeText(getApplicationContext(),
                        itemValue, Toast.LENGTH_LONG)
                        .show();
                position = index;
                tts = new TextToSpeech(Categories.this, Categories.this);
                return true;
            }});

    }


    @Override
    protected void onListItemClick(ListView list, View v, int position, long id)
    {
        if(position == 0)
        {
            Intent intent = new Intent(Categories.this, Info.class);
            startActivity(intent);
        }
        if(position == 1)
        {
            Intent intent = new Intent(Categories.this, Implementation.class);
            startActivity(intent);
        }
        if(position == 2)
        {
            Intent intent = new Intent(Categories.this, Plan.class);
            startActivity(intent);
        }
        if(position == 3)
        {
            Intent intent = new Intent(Categories.this, Implementation.class);
            startActivity(intent);
        }
        if(position == 4)
        {
            Intent intent = new Intent(Categories.this, Review.class);
            startActivity(intent);
        }


    }

    @Override
    public void onInit(int status)
    {
        tts.setLanguage(Locale.US);
        tts.speak(titles[position], TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    }

}
