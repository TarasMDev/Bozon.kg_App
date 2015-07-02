package bozon.kg.app;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProductActivity extends Activity {

    EditText txtName;
    EditText txtPrice;
    EditText txtDesc;
    Button btnSave;
    Button btnDelete;

    String pid;
    String name;
    String price;
    String description;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // адрес для описания одного товара
    private static final String url_product_details = "http://tarasplusanny.hol.es/get_product_details.php";

    // адрес для обновления базы данных
    private static final String url_update_product = "http://tarasplusanny.hol.es/update_product.php";

    // адрес для удаления из базы данных
    private static final String url_delete_product = "http://tarasplusanny.hol.es/delete_product.php";

    // имена узлов JSON
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // берем информацию через намерение
        Intent intentDetails = getIntent();

        // получим product id (pid) через намерение
        pid = intentDetails.getStringExtra(TAG_PID);

        // информацию о товаре получаем через фоновую задачу
        new GetProductDetailsTask().execute();

        // кнопка Сохранить
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // фоновая задача по обновлению данных о товаре
                new SaveProductDetailsTask().execute();
            }
        });

        // кнопка Удалить
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // фоновая задача по удалению товара из базы данных
                new DeleteProductTask().execute();
            }
        });
    }

    // AsyncTask для получения информации о товаре
    class GetProductDetailsTask extends AsyncTask<String, String, String> {
        // Запускаем Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Загружается информация о товаре. Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // проверяем тег success
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("pid", pid));

                // получаем информацию через запрос HTTP GET
                JSONObject json = jsonParser.makeHttpRequest(
                        url_product_details, "GET", params);

                // ответ от json о товаре
                // Log.d("Single Product Details", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // если получили информацию о товаре
                    JSONArray productObj = json.getJSONArray(TAG_PRODUCT);

                    // получим первый объект из массива JSON Array
                    JSONObject product = productObj.getJSONObject(0);

                    name = product.getString(TAG_NAME);
                    price = product.getString(TAG_PRICE);
                    description = product.getString(TAG_DESCRIPTION);
                } else {
                    // не нашли товар по pid
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // закрываем диалоговое окно с индикатором
            pDialog.dismiss();

            txtName = (EditText) findViewById(R.id.inputName);
            txtPrice = (EditText) findViewById(R.id.inputPrice);
            txtDesc = (EditText) findViewById(R.id.inputDesc);

            txtName.setText(name);
            txtPrice.setText(price);
            txtDesc.setText(description);

        }
    }

    // AsyncTask для сохранения данных
    class SaveProductDetailsTask extends AsyncTask<String, String, String> {
        // сначала выводим Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Saving product ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // getting updated data from EditTexts
            String name = txtName.getText().toString();
            String price = txtPrice.getText().toString();
            String description = txtDesc.getText().toString();

            List params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_PID, pid));
            params.add(new BasicNameValuePair(TAG_NAME, name));
            params.add(new BasicNameValuePair(TAG_PRICE, price));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

            // посылаем обновленные данные через запрос POST
            JSONObject json = jsonParser.makeHttpRequest(url_update_product,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // обновление прошло успешно
                    Intent i = getIntent();
                    // посылаем код 100 для уведомления об обновлении товара
                    setResult(100, i);
                    finish();
                } else {
                    // не получилось обновить товар
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // закрываем диалоговое окно с индикатором
            pDialog.dismiss();
        }
    }

    // AsyncTask для удаления товара
    class DeleteProductTask extends AsyncTask<String, String, String> {
        // Сначала выводим Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Товар удаляется...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // проверяем success tag
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("pid", pid));

                // получаем информацию о товаре через запрос
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_product, "POST", params);

                // пишем в лог ответ json
                // Log.d("Delete Product", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // товар успешно удален
                    // уведомляем предыдущую активность через код 100
                    Intent i = getIntent();
                    setResult(100, i);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // закрываем диалоговое окно
            pDialog.dismiss();
        }
    }
}