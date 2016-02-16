package bozon.kg.app;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class Main_screen extends ActionBarActivity {

    //LOG_TAG variable
    private static final String LOG_TAG = "test";
    PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_bell).withBadge("99").withIdentifier(1).withSelectable(false);
    PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_calendar).withBadge("15").withSelectable(false);
    SecondaryDrawerItem item3 = new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(0).withSelectable(false);
    PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName(R.string.drawer_item_profile_information).withIcon(FontAwesome.Icon.faw_user).withSelectable(false);
    private Button scB;

    //Modifiezierte OnCreate Methode fur bessere ActionBar visualisation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        scB = (Button) findViewById(R.id.scan_button);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        new DrawerBuilder().withActivity(this).build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem(),
                        item4,
                        new DividerDrawerItem(),
                        item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem == item1) {
                            Toast.makeText(Main_screen.this, "Это Уведомления", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Main_screen.this, NotificationBozon.class);
                            startActivity(intent);
                        } else if (drawerItem == item2) {
                            Toast.makeText(Main_screen.this, "Это Сообщения", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(Main_screen.this, Messages.class);
                            startActivity(intent2);
                        } else if (drawerItem == item4) {
                            Intent intent3 = new Intent(Main_screen.this, My_Private_Profile.class);
                            startActivity(intent3);
                        }
                        return true;
                    }
                })

                .build();

    }


    //Initialisation fur Tasten
    public void goToMyProfileScreenView(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, My_profile_bozon.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    public void goToCategoryScreenView(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(this, AppProductsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

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

    public void onClickScan(View v) {
        if (v.getId() == R.id.button7) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Main_screen.this);
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            builder.setTitle("Инфо о продукте.")
                    .setMessage("FORMAT: " + scanFormat + "\n" + "CONTENT: " + scanContent)
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.show();

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (null != searchManager) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Поиск в Bozon.kg");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }


}
