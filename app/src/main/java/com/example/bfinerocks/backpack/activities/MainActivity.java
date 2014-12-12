package com.example.bfinerocks.backpack.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bfinerocks.backpack.R;
import com.example.bfinerocks.backpack.fragments.LogInFragment;
import com.example.bfinerocks.backpack.parse.ParseUserObject;
import com.parse.Parse;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "K7x39gpZ124odhnnoxMGj4B9TzrIoTNZfGRl1djm", "E4kpb79zcWQ63uWaEQFjzH0lLsEhd2QAFD9xfA4O");
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LogInFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            ParseUserObject parseUser = new ParseUserObject();
            parseUser.logOutCurrentUser();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new LogInFragment())
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
