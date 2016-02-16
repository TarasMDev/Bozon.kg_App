package bozon.kg.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fragments.FiveFragment;
import fragments.FourFragment;
import fragments.OneFragment;
import fragments.SixFragment;
import fragments.ThreeFragment;
import fragments.TwoFragment;


public class My_Private_Profile extends MainActivity {

    private Button button_revoke, button_logout;
    private TextView textView_name, textView_email;
    private RelativeLayout profile_layout;
    private ImageView imageView_profile_image;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__private__profile);

        button_revoke = (Button) findViewById(R.id.button_revoke);
        button_revoke.setOnClickListener(this);

        button_logout = (Button) findViewById(R.id.button_logout);
        button_logout.setOnClickListener(this);

        imageView_profile_image = (ImageView) findViewById(R.id.imageView_profile_image);
        textView_name = (TextView) findViewById(R.id.textView_name);
        textView_email = (TextView) findViewById(R.id.textView_email);
        profile_layout = (RelativeLayout) findViewById(R.id.profile_layout);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_logout:
                // logout button clicked

                signOutFromGplus();
                break;

            case R.id.button_revoke:
                // revoke button clicked
                revokeGplusAccess();
                break;
        }

    }

    public void pressSendMessageButton(View v) {
        switch (v.getId()) {
            case R.id.imageButton_send_message:
                Toast.makeText(getApplicationContext(),
                        "Message Send...Work in progress", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    public void onConnected(Bundle connectionHint) {
        getProfileInformation();
    }

    public void getProfileInformation() {

        Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        Person currentPerson = Plus.PeopleApi
                .getCurrentPerson(mGoogleApiClient);
        String personName = currentPerson.getDisplayName();
        String personPhotoUrl = currentPerson.getImage().getUrl();
        String personGooglePlusProfile = currentPerson.getUrl();
        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
        textView_name.setText(personName);
        textView_email.setText(email);

        // by default the profile url gives 50x50 px image only
        // we can replace the value with whatever dimension we want by
        // replacing sz=X
        personPhotoUrl = personPhotoUrl.substring(0,
                personPhotoUrl.length() - 2)
                + 400;

        new LoadProfileImage(imageView_profile_image).execute(personPhotoUrl);
        Toast.makeText(getApplicationContext(),
                "Information is loaded", Toast.LENGTH_LONG).show();
    }


    /**
     * Sign-out from google
     */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            Toast.makeText(getApplicationContext(),
                    "SignOUT succes", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Revoking access from google
     */
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e("some user", "User access revoked!");
                            mGoogleApiClient.connect();
                            Toast.makeText(getApplicationContext(),
                                    "Revoked success", Toast.LENGTH_LONG).show();

                        }

                    });
        }
    }

    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.tab1,
                R.drawable.tab2,
                R.drawable.tab3,
                R.drawable.tab4,
                R.drawable.tab5,
                R.drawable.tab6

        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "ONE");
        adapter.addFrag(new TwoFragment(), "TWO");
        adapter.addFrag(new ThreeFragment(), "THREE");
        adapter.addFrag(new FourFragment(), "FOUR");
        adapter.addFrag(new FiveFragment(), "FIVE");
        adapter.addFrag(new SixFragment(), "SIX");
        viewPager.setAdapter(adapter);
    }

    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null;
        }
    }


}
