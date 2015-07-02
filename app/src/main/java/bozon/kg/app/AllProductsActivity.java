package bozon.kg.app;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


public class AllProductsActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Создаем объект JSON Parser
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> productsList;

    // укажите свой адрес
    private static String url_all_products = "http://tarasplusanny.hol.es/get_all_products.php";

    // Имена узлов JSON
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";

    // массив товаров JSONArray
    JSONArray products = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();

        // Загружаем товары в другом потоке
        new LoadAllProductsTask().execute();

        // Получим listview
        ListView lv = getListView();

        // при выборе отдельного товара
        // запускаем активность редактирования товара
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // получим значение выбранного элемента списка
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                // Создаем намерение для запуска активности
                Intent intentEdit = new Intent(getApplicationContext(),
                        EditProductActivity.class);
                // посылаем в другую активность доп.информацию - pid
                intentEdit.putExtra(TAG_PID, pid);

                // запускаем активность и ждем результата
                startActivityForResult(intentEdit, 100);
            }
        });
    }

    // Ответ от активности редактирования товара
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ждем ответа с кодом 100
        if (resultCode == 100) {
            // если получили ответ с кодом 100
            // значит пользователь изменил или удалил товар
            // поэтому перезагрузим экран
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    // Задача в другом потоке для загрузки всех товаров через HTTP Request
    class LoadAllProductsTask extends AsyncTask<String, String, String> {

        // Сначала покажем диалоговое окно прогресса
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllProductsActivity.this);
            pDialog.setMessage("Загружаем список. Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        // получим все товары через url
        protected String doInBackground(String... args) {
            // Строим параметры
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // получим строку JSON из URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET",
                    params);

            // Check your log cat for JSON reponse
            // Log.d("All Products: ", json.toString());

            try {
                // Проверяем переменную SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // товар найден
                    // получаем массив товаров
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // проходим в цикле через все товары
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // сохраняем каждый json-элемент в переменной
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);

                        // создаем HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // добавляем каждый узел в HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);

                        // добавляем в ArrayList
                        productsList.add(map);
                    }
                } else {
                    // ничего не найдено
                    // Запускаем активность Добавления нового товара
                    Intent i = new Intent(getApplicationContext(),
                            Sell_goods.class);
                    // Закрываем все предыдущие активности
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // закрываем диалоговое окно с индикатором прогресса
            pDialog.dismiss();
            // Добавляем данные в ListView
            ListAdapter adapter = new SimpleAdapter(AllProductsActivity.this,
                    productsList, R.layout.list_item, new String[] { TAG_PID,
                    TAG_NAME }, new int[] { R.id.pid, R.id.name });
            setListAdapter(adapter);
        }
    }
}