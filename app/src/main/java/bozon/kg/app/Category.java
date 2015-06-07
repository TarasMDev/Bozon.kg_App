package bozon.kg.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Category extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        // Новый листвью ListView
        ListView listView = (ListView)findViewById(R.id.listView2);

// Категории String
        final String[] categoryTest = new String[] {
                "Computers", "Phones", "TV", "Processors", "Shoes",
                "clothing", "Food", "Category_1", "Category_2", "Category_3",
                "Category_4", "Category_5", "Категория_такая6"
        };

// Адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1,categoryTest);

        listView.setAdapter(adapter);

    }


}
