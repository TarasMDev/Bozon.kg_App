package com.bozon.kg.app;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//import com.google.zxing.integration.android.IntentResult;


public class Main_screen extends ActionBarActivity {

    private ImageView[] imageViews = null;
    private ImageView imageView = null;
    private ViewPager advPager = null;
    private AtomicInteger what = new AtomicInteger(0);
    private boolean isContinue = true;

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
        initViewPager();


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


    private void initViewPager() {
        advPager = (ViewPager) findViewById(R.id.adv_pager);
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
// 这里存放的是四张广告背景
        List<View> advPics = new ArrayList<View>();
        ImageView img1 = new ImageView(this);
        img1.setBackgroundResource(R.drawable.slider);
        advPics.add(img1);
        ImageView img2 = new ImageView(this);
        img2.setBackgroundResource(R.drawable.slidertwo);
        advPics.add(img2);
        ImageView img3 = new ImageView(this);
        img3.setBackgroundResource(R.drawable.sliderthree);
        advPics.add(img3);
        ImageView img4 = new ImageView(this);
        img4.setBackgroundResource(R.drawable.sliderfour);
        advPics.add(img4);

// 对imageviews进行填充
        imageViews = new ImageView[advPics.size()];
//小图标
        for (int i = 0; i < advPics.size(); i++) {
            imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            imageView.setPadding(5, 5, 5, 5);
            imageViews[i] = imageView;
            if (i == 0) {
                imageViews[i]
                        .setBackgroundResource(R.drawable.blackdot);
            } else {
                imageViews[i]
                        .setBackgroundResource(R.drawable.orange);
            }
            group.addView(imageViews[i]);
        }
        advPager.setAdapter(new AdvAdapter(advPics));
        advPager.setOnPageChangeListener(new GuidePageChangeListener());
        advPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isContinue = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isContinue = true;
                        break;
                    default:
                        isContinue = true;
                        break;
                }
                return false;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(what.get());
                        whatOption();
                    }
                }
            }
        }).start();
    }

    private void whatOption() {
        what.incrementAndGet();
        if (what.get() > imageViews.length - 1) {
            what.getAndAdd(-4);
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {

        }
    }
    private final Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            advPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }
    };
    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageSelected(int arg0) {
            what.getAndSet(arg0);
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.drawable.orange);
                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.blackdot);
                }
            }
        }
    }
    private final class AdvAdapter extends PagerAdapter {
        private List<View> views = null;
        public AdvAdapter(List<View> views) {
            this.views = views;
        }
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }
        @Override
        public void finishUpdate(View arg0) {
        }
        @Override
        public int getCount() {
            return views.size();
        }
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            return views.get(arg1);
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }
        @Override
        public Parcelable saveState() {
            return null;
        }
        @Override
        public void startUpdate(View arg0) {
        }
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
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivity(intent);

        }

    }


//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//
//        if (scanningResult != null) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(Main_screen.this);
//            String scanContent = scanningResult.getContents();
//            String scanFormat = scanningResult.getFormatName();
//
//            builder.setTitle("Инфо о продукте.")
//                    .setMessage("FORMAT: " + scanFormat + "\n" + "CONTENT: " + scanContent)
//                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//            builder.show();
//
//        } else {
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "No scan data received!", Toast.LENGTH_SHORT);
//            toast.show();
//
//        }
//
//
//    }





    public void goToScanView(View v) {
        switch (v.getId()) {
            case R.id.button7:
                Intent intent = new Intent(this, CaptureActivity.class);
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
