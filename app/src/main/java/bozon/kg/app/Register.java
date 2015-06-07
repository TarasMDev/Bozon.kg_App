package bozon.kg.app;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Register extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
    //Action for buttons
    public void goToLoginView(View v){
        switch (v.getId()) {
            case R.id.btnLinkToLoginScreen:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void goToHomeScreenView(View v){
        switch (v.getId()) {
            case R.id.btnRegister:
                Intent intent = new Intent(this, Main_screen.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
