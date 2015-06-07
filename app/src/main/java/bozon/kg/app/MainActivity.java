package bozon.kg.app;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        //Hide actionBar for better GUI
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    //Action for buttons
    public void goToRegisterView(View v){
        switch (v.getId()) {
            case R.id.btnLinkToRegisterScreen:
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void goHomeScreenView(View v){
        switch (v.getId()) {
            case R.id.btnLogin:
                Intent intent = new Intent(this, Main_screen.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
