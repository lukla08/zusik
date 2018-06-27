package com.example.jac.place.frontend.firm.edit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Firm;

public class FirmEditActivity extends AppCompatActivity {

    public static String EXTRA_KEY_SELECTED_RECORD_ID = "selected_record_id";
    private int selectedRecordId;

    private EditText editFirmCode;
    private EditText editFirmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedRecordId = getIntent().getIntExtra(EXTRA_KEY_SELECTED_RECORD_ID, 0);

        setContentView(R.layout.firm_edit_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_firm_edit);
        setSupportActionBar(toolbar);

        editFirmCode = findViewById(R.id.editFirmCode);
        editFirmName = findViewById(R.id.editFirmName);

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
                editFirmCode.setText(firm.getFirmCode());
                editFirmName.setText(firm.getFirmName());
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
}
