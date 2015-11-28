package bozon.kg.app;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import bozon.kg.app.My_Private_Profile;

import java.lang.reflect.Field;


public class Main_screen extends ActionBarActivity {

    //LOG_TAG variable
    private static final String LOG_TAG = "test";
    Button button2;

    //Modifiezierte OnCreate Methode fur bessere ActionBar visualisation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 11) {
            requestFeature();
        }
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);

        setContentView(R.layout.activity_main_screen);
        button2 = (Button) findViewById(R.id.button2);
        // обработчики
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Запускаем активность для просмотра все товаров
                Intent intentAllProducts = new Intent(getApplicationContext(),
                        AllProductsActivity.class);
                startActivity(intentAllProducts);
            }
        });
    }

    //Modification fur ActionBar
    private void requestFeature() {
        try {
            Field fieldImpl = ActionBarActivity.class.getDeclaredField("mImpl");
            fieldImpl.setAccessible(true);
            Object impl = fieldImpl.get(this);

            Class cls = Class.forName("android.support.v7.app.ActionBarActivityDelegate");

            Field fieldHasActionBar = cls.getDeclaredField("mHasActionBar");
            fieldHasActionBar.setAccessible(true);
            fieldHasActionBar.setBoolean(impl, true);

        } catch (NoSuchFieldException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        }
    }

    //Initialisation fur Tasten
    public void goToMyProfileScreenView(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, My_Private_Profile.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


//    public void goToCategoryScreenView(View v){
//        switch (v.getId()) {
//            case R.id.button2:
//                Intent intent = new Intent(this, AllProductsActivity.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//    }

    public void goToSellGoodsScreenView(View v) {
        switch (v.getId()) {
            case R.id.button3:
                Intent intent = new Intent(this, Sell_goods.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void goToMessagesView(View v) {
        switch (v.getId()) {
            case R.id.button5:
                Intent intent = new Intent(this, Messages.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void goToPrivateProfileView(View v) {
        switch (v.getId()) {
            case R.id.button6:
                Intent intent = new Intent(this, My_Private_Profile.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void goToScanView(View v) {
        switch (v.getId()) {
            case R.id.button7:
                Intent intent = new Intent(this, ScanningActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.menu_save:
                Intent intent = new Intent(this, NotificationBozon.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
