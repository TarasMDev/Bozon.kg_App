package bozon.kg.app;

import android.app.ActionBar;
import android.app.TabActivity;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;


public class Messages extends ActionBarActivity {
    TabHost.TabSpec tabSpec;
    protected static final String LOG_TAG = "my_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        // ListView
        ListView listView3 = (ListView)findViewById(R.id.listView2);
        ListView listView4 = (ListView)findViewById(R.id.listView3);

//  String
        final String[] messageTest = new String[] {
                "Notification_1", "Notification_2", "Notification_3", "Notification_4", "Notification_5",
                "Notification_6", "Notification_7", "Notification_8", "Notification_9", "Notification_10",
                "Notification_11", "Notification_12","Notification_8", "Notification_9", "Notification_10",
                "Notification_11", "Notification_12"
        };

        final String[] messageTest2 = new String[] {
                "TestMessage_1", "Send_2", "Notification_3", "Notification_4", "Notification_5",
                "Notification_6", "Notification_7", "Notification_8", "Notification_9", "Notification_10",
                "Notification_11", "Notification_12","Notification_8", "Notification_9", "Notification_10",
                "Notification_11", "Notification_12"
        };

// Адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1,messageTest);

        listView3.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1,messageTest2);

        listView4.setAdapter(adapter2);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        // инициализация
        tabHost.setup();

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Принятые");
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Отправленные");
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);



        // вторая вкладка по умолчанию активна
        tabHost.setCurrentTabByTag("tag1");

        // логгируем переключение вкладок
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.d(LOG_TAG, "tabId = " + tabId);

            }
        });

    }

}
