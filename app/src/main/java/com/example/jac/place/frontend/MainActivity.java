package com.example.jac.place.frontend;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.backend.model.Settings;
import com.example.jac.place.frontend.base.BaseAppCompatActivity;
import com.example.jac.place.frontend.employee.EmployeeListActivity;
import com.example.jac.place.frontend.firm.FirmListActivity;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    // If do not grant write external storage permission.
        if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            int requestCode  = 0;
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
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

        if (id == R.id.action_employees) {
            showDialogSelectFirm();
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
    private void showDialogSelectFirm() {

        new AsyncTask<Void, Void, List<Firm>>() {
            @Override  protected List<Firm> doInBackground(Void... voids) {
                List<Firm> allFirms = SalaryDatabase.getInstance(MainActivity.this).firmsDao().getFirmsData();
                return allFirms.stream().filter(firm -> firm.getDisabled() == 0)
                        .collect(Collectors.toList());
            }
            @Override protected void onPostExecute(List<Firm> firms) {
                CharSequence[] names = new CharSequence[firms.size()];
                for (int index = 0; index < firms.size(); index ++) {
                    names[index] = firms.get(index).getFirmName();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        Intent intent = new Intent(MainActivity.this, EmployeeListActivity.class);
                        intent.putExtra(EmployeeListActivity.EXTRA_KEY_SELECTED_FIRM_ID, firms.get(index).getFirmId());
                        intent.putExtra(EmployeeListActivity.EXTRA_KEY_SELECTED_FIRM_NAME, firms.get(index).getFirmName());
                        startActivity(intent);
                    }
                });
                builder.create().show();
            }
        }.execute();

    }

}
