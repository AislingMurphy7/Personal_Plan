package colin.personalplan;

import android.app.Activity;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

public class Categories extends ListActivity implements TextToSpeech.OnInitListener {

    ListView list;
    TextToSpeech tts;
    int position;
    String[] titles =
            {
                    "My Information Gather",
                    "My Dreams",
                    "My Plan",
                    "Implementation",
                    "Review Of My Plan"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        //Resources res = getResources();


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
                return false;
            }});

    }


    @Override
    protected void onListItemClick(ListView list, View v, int position, long id) {



    }

    @Override
    public void onInit(int status)
    {
        tts.setLanguage(Locale.US);
        tts.speak(titles[position], TextToSpeech.QUEUE_ADD, null);
    }
}
