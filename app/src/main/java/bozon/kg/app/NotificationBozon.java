package bozon.kg.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class NotificationBozon extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_bozon);

        // ListView
        ListView listView1 = (ListView)findViewById(R.id.listView);

//  String
        final String[] notificationTest = new String[] {
                "Notification_1", "Notification_2", "Notification_3", "Notification_4", "Notification_5",
                "Notification_6", "Notification_7", "Notification_8", "Notification_9", "Notification_10",
                "Notification_11", "Notification_12"
        };

// Адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1,notificationTest);

        listView1.setAdapter(adapter);
    }


}
