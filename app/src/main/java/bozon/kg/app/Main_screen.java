package bozon.kg.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Main_screen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    public void goToMyProfileScreenView(View v){
        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, my_profile.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void goToNotificationScreenView(View v){
        switch (v.getId()) {
            case R.id.imageButton:
                Intent intent = new Intent(this, NotificationBozon.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    public void goToCategoryScreenView(View v){
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(this, Category.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void goToSellGoodsScreenView(View v){
        switch (v.getId()) {
            case R.id.button3:
                Intent intent = new Intent(this, Sell_goods.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
