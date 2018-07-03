package com.example.jac.place.frontend.employee.salary_items;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.SalaryItems;
import com.example.jac.place.backend.model.utils.SalaryItemsCalcUtils;

public class SalaryItemsActivity extends AppCompatActivity {

    public static String EXTRA_KEY_SELECTED_EMPLOYEE_ID = "selected_employee_id";

    private long selectedEmployeeId;

    private TextView editEmployeeName;
    private TextView editSalary;
    private TextView editSalary12m;
    private TextView editIllnessDays;
    private TextView editCostOfObtaining;
    private TextView editCalcSalary12m;
    private TextView editIllnessDailyRate;
    private TextView editIllnessPart;

    private TextView editNormalDailyRate;
    private TextView editSalaryNormalRate;
    private TextView editSalaryTotal;
    private TextView editBase4SocialTax;
    private TextView editSocialTax;
    private TextView editBase4HealthTax;
    private TextView editHealthToTake;
    private TextView editHealthToDeduct;
    private TextView editBaseIncomeTax;
    private TextView editAdvanceIncomeTaxBrutto;
    private TextView editAdvanceIncomeTax;
    private TextView editAmountDue;


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
        editCostOfObtaining = findViewById(R.id.editCostOfObtaining);
        editCalcSalary12m = findViewById(R.id.editCalcSalary12m);
        editIllnessDailyRate =  findViewById(R.id.editIllnessDailyRate);
        editIllnessPart =  findViewById(R.id.editIllnessPart);
        editNormalDailyRate = findViewById(R.id.editNormalDailyRate);
        editSalaryNormalRate = findViewById(R.id.editSalaryNormalPart);
        editSalaryTotal = findViewById(R.id.editSalaryTotal);
        editBase4SocialTax = findViewById(R.id.editBase4SocialTax);
        editSocialTax = findViewById(R.id.editSocialTax);
        editBase4HealthTax = findViewById(R.id.editBase4HealthTax);
        editHealthToTake = findViewById(R.id.editHealthToTake);
        editHealthToDeduct = findViewById(R.id.editHealthToDeduct);
        editBaseIncomeTax = findViewById(R.id.editBaseIncomeTax);
        editAdvanceIncomeTaxBrutto = findViewById(R.id.editAdvanceIncomeTaxBrutto);
        editAdvanceIncomeTax = findViewById(R.id.editAdvanceIncomeTax);
        editAmountDue = findViewById(R.id.editAmountDue);

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

                SalaryItems salaryItems = SalaryItemsCalcUtils.prepareSalaryItems4Employee(emp);

                editCostOfObtaining.setText(Double.toString(salaryItems.getCalc_costsOfObtaining()));
                editCalcSalary12m.setText(Double.toString(salaryItems.getCalc_salaray12m_netto()));
                editIllnessDailyRate.setText(Double.toString(salaryItems.getCalc_ilnessDailyRate()));
                editIllnessPart.setText(Double.toString(salaryItems.getCalc_salaryIllnessPart()));
                editNormalDailyRate.setText(Double.toString(salaryItems.getCalc_normalDailyRate()));
                editSalaryNormalRate.setText(Double.toString(salaryItems.getCalc_salaryNormalPart()));
                editSalaryTotal.setText(Double.toString(salaryItems.getCalc_salaryTotal()));

                editBase4SocialTax.setText(Double.toString(salaryItems.getCalc_base4SocialTaxes()));
                editSocialTax.setText(Double.toString(salaryItems.getCalc_employeeSocialTotal()));

                editBase4HealthTax.setText(Double.toString(salaryItems.getCalc_base4healthTax()));
                editHealthToTake.setText(Double.toString(salaryItems.getCalc_healthTaxToTake()));
                editHealthToDeduct.setText(Double.toString(salaryItems.getCalc_healthTaxToDeduct()));
                editBaseIncomeTax.setText(Long.toString(salaryItems.getCalc_base4IncomeTax()));
                editAdvanceIncomeTaxBrutto.setText(Double.toString(salaryItems.getCalc_advance4IncomeTaxBrutto()));
                editAdvanceIncomeTax.setText(Long.toString(salaryItems.getCalc_advance4IncomeTax()));
                editAmountDue.setText(Double.toString(salaryItems.getCalc_AmountDue()));
            }
        }.execute();
    }

}
