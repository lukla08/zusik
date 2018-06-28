package com.example.jac.place.frontend.employee.list;

import android.arch.persistence.room.util.StringUtil;
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
import com.example.jac.place.app.utils.StringUtils;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Employee;

public class EmployeeEditActivity extends AppCompatActivity {

    public static String EXTRA_KEY_SELECTED_RECORD_ID = "selected_record_id";
    public static String EXTRA_KEY_SELECTED_FIRM_ID = "selected_firm_id";
    private long selectedRecordId;
    private long selectedFirmId;

    private EditText editFirmName;
    private EditText editSalary;
    private EditText editSalary12m;
    private EditText editHolidays;
    private EditText editIllness;
    private CheckBox cEmployeeEnabled;
    private CheckBox cEmployeeOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedRecordId = getIntent().getLongExtra(EXTRA_KEY_SELECTED_RECORD_ID, 0);
        selectedFirmId = getIntent().getLongExtra(EXTRA_KEY_SELECTED_FIRM_ID, 0);
        Log.d(AppConst.APP_TAG, "selectedId : " + selectedRecordId);

        setContentView(R.layout.employee_edit_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_employee_edit);
        setSupportActionBar(toolbar);

        editFirmName = findViewById(R.id.editEmployeeName);
        editSalary = findViewById(R.id.editSalary);
        editSalary12m = findViewById(R.id.editSalary12M);
        editHolidays = findViewById(R.id.editHolidays);
        editIllness = findViewById(R.id.editIllness);

        cEmployeeOwner = findViewById(R.id.checkEmployeeOwner);
        cEmployeeEnabled = findViewById(R.id.checkEmployeeEnabled);
        cEmployeeEnabled.setChecked(true);

        setTitle(isInsertMode()?
                "Dopisanie pracownika" : "Edycja pracownika");

        if (!isInsertMode())
            initializeControls();
    }

    private void initializeControls() {
        new AsyncTask<Void, Void, Employee>() {
            @Override protected Employee doInBackground(Void... voids) {
                return SalaryDatabase.getInstance(EmployeeEditActivity.this).employeeDao().getSelectedEmployee(selectedRecordId);
            }

            @Override
            protected void onPostExecute(Employee emp) {
                editFirmName.setText(emp.getName());
                cEmployeeEnabled.setChecked(emp.getDisabled() == 0);
                cEmployeeOwner.setChecked(emp.isOwner());
                editSalary.setText(Double.toString(emp.getSalary()));
                editSalary12m.setText(Double.toString(emp.getAvg12MSalary()));
                editHolidays.setText(Integer.toString(emp.getHolidayDays()));
                editIllness.setText(Integer.toString(emp.getIllnessDays()));
            }
        }.execute();
    }

    private void saveCurrentRecord() {
        Employee emp = new Employee();
        emp.setName(editFirmName.getText().toString());
        emp.setSalary(StringUtils.toDouble(editSalary.getText().toString()));
        emp.setAvg12MSalary(StringUtils.toDouble(editSalary12m.getText().toString()));

        emp.setHolidayDays(StringUtils.toInt(editHolidays.getText().toString()));
        emp.setIllnessDays(StringUtils.toInt(editIllness.getText().toString()));

        emp.setFirmId(selectedFirmId);
        emp.setEmployeeId(selectedRecordId);
        emp.setDisabled(cEmployeeEnabled.isChecked()? 0 : 1);
        emp.setOwner(cEmployeeOwner.isChecked());


        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                SalaryDatabase.getInstance(EmployeeEditActivity.this).employeeDao().insertOrUpdate(emp);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                EmployeeEditActivity.this.finish();
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
            case R.id.action_save :
                saveCurrentRecord();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    action_save_firm
}
