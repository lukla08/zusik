package com.example.jac.place.frontend.employee;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.backend.model.SalaryItems;
import com.example.jac.place.backend.model.utils.SalaryItemsCalcUtils;
import com.example.jac.place.frontend.employee.salary_items.SalaryItemsActivity;

public class EmployeeEditActivity extends AppCompatActivity {

    public static String EXTRA_KEY_SELECTED_RECORD_ID = "selected_record_id";
    public static String EXTRA_KEY_SELECTED_FIRM_ID = "selected_firm_id";
    private long selectedRecordId;
    private long selectedFirmId;

    private EditText editFirmName;
    private EditText editSalary;
    private EditText editSalary12m;
    private EditText editIllness;
    private CheckBox cEmployeeEnabled;

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
        editIllness = findViewById(R.id.editIllness);

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
                editSalary.setText(Double.toString(emp.getSalary()));
                editSalary12m.setText(Double.toString(emp.getAvg12MSalary()));
                editIllness.setText(Integer.toString(emp.getIllnessDays()));
            }
        }.execute();
    }

    private Employee getRecordPopulatedWithControls() {
        Employee emp = new Employee();
        emp.setName(editFirmName.getText().toString());
        emp.setSalary(StringUtils.toDouble(editSalary.getText().toString()));
        emp.setAvg12MSalary(StringUtils.toDouble(editSalary12m.getText().toString()));


        emp.setIllnessDays(StringUtils.toInt(editIllness.getText().toString()));

        emp.setFirmId(selectedFirmId);
        emp.setEmployeeId(selectedRecordId);
        emp.setDisabled(cEmployeeEnabled.isChecked()? 0 : 1);

        return emp;
    }

    private void saveCurrentRecord() {

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                storeEmployee();
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                EmployeeEditActivity.this.finish();
            }
        }.execute();
    }

    private void storeEmployee() {
        Employee emp = getRecordPopulatedWithControls();
        SalaryDatabase salaryDatabase = SalaryDatabase.getInstance(EmployeeEditActivity.this);
        salaryDatabase.employeeDao().insertOrUpdate(emp);
        SalaryItems salaryItems = salaryDatabase.salaryItemsDao().getSalaryItems4Employee(emp.getEmployeeId());
        if (salaryItems == null || salaryItems.getEditable() == 0) {
            Firm selectedFirm = salaryDatabase.firmsDao().getSelectedFirm(emp.getFirmId());
            salaryItems = SalaryItemsCalcUtils.prepareSalaryItems4Employee(emp, selectedFirm);
            salaryDatabase.salaryItemsDao().insertOrUpdate(salaryItems);
        }
    }

    private boolean isInsertMode() {
        return selectedRecordId <= 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_firm_employee, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_save :
                saveCurrentRecord();
                return true;

            case R.id.action_show_calc_items :
                showSalaryItems();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private void showSalaryItems() {
        Employee emp = getRecordPopulatedWithControls();

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                storeEmployee();
                SalaryDatabase.getInstance(EmployeeEditActivity.this).employeeDao().insertOrUpdate(emp);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                Intent intent = new Intent(EmployeeEditActivity.this, SalaryItemsActivity.class);
                intent.putExtra(SalaryItemsActivity.EXTRA_KEY_SELECTED_EMPLOYEE_ID, emp.getEmployeeId());
                startActivity(intent);
            }
        }.execute();

    }

    //    action_save_firm
}
