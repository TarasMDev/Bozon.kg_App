package com.bozon.kg.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;




public abstract class Drawer extends MainActivity {


    private TextView Drawer_textView_name, drawer_textView_email;
    private ImageView drawerImageProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_header);

//        drawerImageProfile = (ImageView) findViewById(R.id.drawerImageProfile);
//        Drawer_textView_name = (TextView) findViewById(R.id.Drawer_textView_name);
//        drawer_textView_email = (TextView) findViewById(R.id.drawer_textView_email);

    }


    public void onConnected(Bundle connectionHint) {
        getProfileInformation2();
    }

    public void getProfileInformation2() {

        Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        Person currentPerson = Plus.PeopleApi
                .getCurrentPerson(mGoogleApiClient);
        String personName = currentPerson.getDisplayName();
        String personPhotoUrl = currentPerson.getImage().getUrl();
        String personGooglePlusProfile = currentPerson.getUrl();
        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
        Drawer_textView_name.setText(personName);
        drawer_textView_email.setText(email);

        // by default the profile url gives 50x50 px image only
        // we can replace the value with whatever dimension we want by
        // replacing sz=X
        personPhotoUrl = personPhotoUrl.substring(0,
                personPhotoUrl.length() - 2)
                + 400;

        new LoadProfileImage3(drawerImageProfile).execute(personPhotoUrl);
        Toast.makeText(getApplicationContext(),
                "Information is loaded in Nav_drawer", Toast.LENGTH_LONG).show();
    }

    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage3 extends AsyncTask<String, Void, Bitmap> {
        ImageView bmiImage;

        public LoadProfileImage3(ImageView bmImage) {
            this.bmiImage = bmImage;
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
            bmiImage.setImageBitmap(result);
        }
    }

}
