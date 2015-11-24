package bozon.kg.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;


public class My_profile_bozon extends ActionBarActivity {
    TabHost.TabSpec tabSpecs;
    protected static final String LOG_TAG = "my_tagi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // ListView
        ListView listView6 = (ListView)findViewById(R.id.listView6);
        ListView listView7 = (ListView)findViewById(R.id.listView7);

//  String
        final String[] productTest = new String[] {
                "Porduct_2", "Porduct_1", "Porduct_3", "Porduct_5", "Porduct_6",
                "Porduct_1", "Porduct_1", "Porduct_122", "Porduct_9", "Porduct_7",
                "Porduct_1", "Notification_12","Notification_8", "Notification_9", "Notification_10",
                "Notification_11", "Notification_12"
        };

        final String[] productTest2 = new String[] {
                "Selled_Porduct_2", "Selled_Porduct_1", "Selled_Porduct_3", "Porduct_5", "Porduct_6",
                "Porduct_1", "Porduct_1", "Porduct_122", "Porduct_9", "Porduct_7",
                "Porduct_1", "Notification_12","Notification_8", "Notification_9", "Notification_10",
                "Notification_11", "Notification_12"
        };

// Адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1,productTest);

        listView6.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1,productTest2);

        listView7.setAdapter(adapter2);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        // инициализация
        tabHost.setup();

        tabSpecs = tabHost.newTabSpec("tag3");
        tabSpecs.setIndicator("Продажа");
        tabSpecs.setContent(R.id.tab3);
        tabHost.addTab(tabSpecs);

        tabSpecs = tabHost.newTabSpec("tag4");
        tabSpecs.setIndicator("Покупка");
        tabSpecs.setContent(R.id.tab4);
        tabHost.addTab(tabSpecs);



        // вторая вкладка по умолчанию активна
        tabHost.setCurrentTabByTag("tag3");

        // логгируем переключение вкладок
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.d(LOG_TAG, "tabId = " + tabId);

            }
        });

    }

}