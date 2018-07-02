package com.example.jac.place.frontend.employee.salary_items;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.app.utils.StringUtils;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.SalaryItems;
import com.example.jac.place.backend.model.utils.SalaryItemsCalcUtils;
import com.example.jac.place.frontend.employee.EmployeeEditActivity;

public class SalaryItemsActivity extends AppCompatActivity {

    public static String EXTRA_KEY_SELECTED_EMPLOYEE_ID = "selected_employee_id";

    private long selectedEmployeeId;

    private TextView editEmployeeName;
    private TextView editSalary;
    private TextView editSalary12m;
    private TextView editIllnessDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedEmployeeId = getIntent().getLongExtra(EXTRA_KEY_SELECTED_EMPLOYEE_ID, 0);
        Log.d(AppConst.APP_TAG, "selectedEmployeeId : " + selectedEmployeeId);

        setContentView(R.layout.salary_items_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_employee_edit);
        setSupportActionBar(toolbar);

        setTitle("Składniki płacowe");

        editEmployeeName = findViewById(R.id.editEmployeeName);
        editSalary = findViewById(R.id.editSalary);
        editSalary12m = findViewById(R.id.editSalary12M);
        editIllnessDays = findViewById(R.id.editIllness);
        
        initializeControls();
    }

    private void initializeControls() {
        new AsyncTask<Void, Void, Employee>() {
            @Override protected Employee doInBackground(Void... voids) {
                return SalaryDatabase.getInstance(SalaryItemsActivity.this).employeeDao().getSelectedEmployee(selectedEmployeeId);
            }

            @Override
            protected void onPostExecute(Employee emp) {
                editEmployeeName.setText(emp.getName());
                editSalary.setText(Double.toString(emp.getSalary()));
                editSalary12m.setText(Double.toString(emp.getAvg12MSalary()));
                editIllnessDays.setText(Integer.toString(emp.getIllnessDays()));
            }
        }.execute();
    }

}
