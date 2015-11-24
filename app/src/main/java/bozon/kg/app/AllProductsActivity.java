package bozon.kg.app;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class AllProductsActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        ArrayList<ItemDetails> image_details = GetSearchResults();

        final ListView lv1 = (ListView) findViewById(R.id.listView4);
        lv1.setAdapter(new ItemListBaseAdapter(this, image_details));

        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                ItemDetails obj_itemDetails = (ItemDetails)o;
                Toast.makeText(AllProductsActivity.this, "Вы выбрали : " + " " + obj_itemDetails.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private ArrayList<ItemDetails> GetSearchResults(){
        ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();

        ItemDetails item_details = new ItemDetails();
        item_details.setName("Недвижимость");
        item_details.setItemDescription("Описание");
        item_details.setPrice("---------");
        item_details.setImageNumber(1);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Транспорт");
        item_details.setItemDescription("Описание");
        item_details.setPrice("---------");
        item_details.setImageNumber(2);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Одежда");
        item_details.setItemDescription("Описание");
        item_details.setPrice("---------");
        item_details.setImageNumber(3);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Фото и техника");
        item_details.setItemDescription("Описание");
        item_details.setPrice("---------");
        item_details.setImageNumber(4);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Компьютеры");
        item_details.setItemDescription("Описание");
        item_details.setPrice("---------");
        item_details.setImageNumber(5);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Отдых и Туризм");
        item_details.setItemDescription("Описание");
        item_details.setPrice("---------");
        item_details.setImageNumber(6);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Ремонт и Сантехника");
        item_details.setItemDescription("Описание");
        item_details.setPrice("---------");
        item_details.setImageNumber(7);
        results.add(item_details);


        return results;
    }
}