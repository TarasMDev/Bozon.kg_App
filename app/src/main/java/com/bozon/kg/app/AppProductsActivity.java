package com.bozon.kg.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class AppProductsActivity extends AppCompatActivity {

    private static final String url = "http://www.bozon.kg/catalog/api/json/category_tree";
    private ProgressDialog loadingDialog;
    private List<AppItem> arrayCategory = new ArrayList<AppItem>();
    private ListView listViewCategory;
    private AppAdapter adapterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        listViewCategory = (ListView) findViewById(R.id.list_item);
        adapterCategory = new AppAdapter(this, arrayCategory);
        listViewCategory.setAdapter(adapterCategory);

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Загрузка категорий...");
        loadingDialog.show();

        //Creat volley request obj
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();
                //parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        AppItem item = new AppItem();
                        item.setTitle(obj.getString("title"));
                        item.setIcon(obj.getString("icon"));
                        item.setId(((Number) obj.get("id")).intValue());

                        //add to array
                        arrayCategory.add(item);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                adapterCategory.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Bad internet connection...", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        AppControl.getmInstance().addToRequesQueue(jsonArrayRequest);
    }

    public void hideDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

}
