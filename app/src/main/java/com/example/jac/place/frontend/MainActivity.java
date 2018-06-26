package com.example.jac.place.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.backend.model.Settings;
import com.example.jac.place.frontend.base.BaseAppCompatActivity;
import com.example.jac.place.frontend.firm.list.FirmListActivity;

import java.util.List;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_firms) {
            Intent intent = new Intent(this, FirmListActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_settings) {
            Settings settings = new Settings();
            settings.setWorkingDays(11);

            getBackgroundExecutor().execute(database -> database.settingsDao().insertOrUpdate(settings));
            getBackgroundExecutor().execute(database -> {
                List<Settings> allSettings = database.settingsDao().getAllSettings();
                Log.d(AppConst.APP_TAG, "settingsCount:" + allSettings.size());
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
