package com.example.jac.place.frontend.firm.edit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Firm;

public class FirmEditActivity extends AppCompatActivity {

    public static String EXTRA_KEY_SELECTED_RECORD_ID = "selected_record_id";
    private long selectedRecordId;

    private EditText editFirmName;
    private CheckBox cFirmEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedRecordId = getIntent().getLongExtra(EXTRA_KEY_SELECTED_RECORD_ID, 0);
        Log.d(AppConst.APP_TAG, "selectedId : " + selectedRecordId);

        setContentView(R.layout.firm_edit_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_firm_edit);
        setSupportActionBar(toolbar);

        editFirmName = findViewById(R.id.editFirmName);
        cFirmEnabled = findViewById(R.id.checkFirmEnabled);
        cFirmEnabled.setChecked(true);

        setTitle(isInsertMode()?
                R.string.title_activity_firm_insert : R.string.title_activity_firm_edit);

        if (!isInsertMode())
            initializeControls();
    }

    private void initializeControls() {
        new AsyncTask<Void, Void, Firm>() {
            @Override protected Firm doInBackground(Void... voids) {
                return SalaryDatabase.getInstance(FirmEditActivity.this).firmsDao().getSelectedFirm(selectedRecordId);
            }

            @Override
            protected void onPostExecute(Firm firm) {
                editFirmName.setText(firm.getFirmName());
                cFirmEnabled.setChecked(firm.getDisabled() == 0);
            }
        }.execute();
    }

    private void saveCurrentRecord() {
        Firm firm = new Firm();
        firm.setFirmName(editFirmName.getText().toString());
        firm.setDisabled(cFirmEnabled.isChecked()? 0 : 1);
        firm.setFirmId(selectedRecordId);

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                SalaryDatabase.getInstance(FirmEditActivity.this).firmsDao().insertOrUpdate(firm);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                FirmEditActivity.this.finish();
            }
        }.execute();
    }

    private boolean isInsertMode() {
        return selectedRecordId <= 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_firm_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_save:
                saveCurrentRecord();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    action_save_firm
}
