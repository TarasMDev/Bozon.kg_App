package bozon.kg.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;


public class My_Private_Profile extends MainActivity {

    private Button button_revoke, button_logout;
    private TextView textView_name, textView_email;
    private RelativeLayout profile_layout;
    private ImageView imageView_profile_image;

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


}
