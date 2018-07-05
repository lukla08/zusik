package com.example.jac.place.frontend.employee.salary_items;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.Firm;
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
    private TextView editSocialPension;
    private TextView editSocialRent;
    private TextView editSocialIllness;
    private TextView editSocialTax;

    private TextView editBase4HealthTax;
    private TextView editHealthToTake;
    private TextView editHealthToDeduct;
    private TextView editBaseIncomeTax;
    private TextView editAdvanceIncomeTaxBrutto;
    private TextView editAdvanceIncomeTax;
    private TextView editAmountDue;

    private TextView editBossPension;
    private TextView editBossRent;
    private TextView editBossAccident;
    private TextView editBossFP;
    private TextView editBossFGSP;
    private TextView editTotalCost;


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

        editSocialPension = findViewById(R.id.editSocialPension);
        editSocialRent = findViewById(R.id.editSocialRent);
        editSocialIllness = findViewById(R.id.editSocialIllness);

        editBossPension = findViewById(R.id.editBossPension);
        editBossRent = findViewById(R.id.editBossRent);
        editBossAccident = findViewById(R.id.editBossAccident);
        editBossFP = findViewById(R.id.editBossFP);
        editBossFGSP = findViewById(R.id.editBossFGSP);
        editTotalCost = findViewById(R.id.editTotalCost);

        initializeControls();

    }

    private void initializeControls() {

        new AsyncTask<Void, Void, Pair<Employee, Firm >>() {
            @Override protected Pair<Employee, Firm > doInBackground(Void... voids) {
                SalaryDatabase database = SalaryDatabase.getInstance(SalaryItemsActivity.this);
                Employee selectedEmployee = database.employeeDao().getSelectedEmployee(selectedEmployeeId);
                Firm connectedFirm = null;
                if (selectedEmployee != null)
                    connectedFirm = database.firmsDao().getSelectedFirm(selectedEmployee.getFirmId());

                Pair<Employee, Firm > res = new Pair<>(selectedEmployee, connectedFirm);
                return res;
            }

            @Override
            protected void onPostExecute(Pair<Employee, Firm> combinedData) {
                Employee emp = combinedData.first;
                Firm firm = combinedData.second;
                editEmployeeName.setText(emp.getName());
                editSalary.setText(Double.toString(emp.getSalary()));
                editSalary12m.setText(Double.toString(emp.getAvg12MSalary()));
                editIllnessDays.setText(Integer.toString(emp.getIllnessDays()));

                SalaryItems salaryItems = SalaryItemsCalcUtils.prepareSalaryItems4Employee(emp, firm);

                editCostOfObtaining.setText(Double.toString(salaryItems.getCalc_costsOfObtaining()));
                editCalcSalary12m.setText(Double.toString(salaryItems.getCalc_salaray12m_netto()));
                editIllnessDailyRate.setText(Double.toString(salaryItems.getCalc_ilnessDailyRate()));
                editIllnessPart.setText(Double.toString(salaryItems.getCalc_salaryIllnessPart()));
                editNormalDailyRate.setText(Double.toString(salaryItems.getCalc_normalDailyRate()));
                editSalaryNormalRate.setText(Double.toString(salaryItems.getCalc_salaryNormalPart()));
                editSalaryTotal.setText(Double.toString(salaryItems.getCalc_salaryTotal()));

                editBase4SocialTax.setText(Double.toString(salaryItems.getCalc_base4SocialTaxes()));
                editSocialIllness.setText(Double.toString(salaryItems.getCalc_employeeSocialIllness()));
                editSocialPension.setText(Double.toString(salaryItems.getCalc_employeeSocialPension()));
                editSocialRent.setText(Double.toString(salaryItems.getCalc_employeeSocialRent()));

                editSocialTax.setText(Double.toString(salaryItems.getCalc_employeeSocialTotal()));

                editBase4HealthTax.setText(Double.toString(salaryItems.getCalc_base4healthTax()));
                editHealthToTake.setText(Double.toString(salaryItems.getCalc_healthTaxToTake()));
                editHealthToDeduct.setText(Double.toString(salaryItems.getCalc_healthTaxToDeduct()));
                editBaseIncomeTax.setText(Long.toString(salaryItems.getCalc_base4IncomeTax()));
                editAdvanceIncomeTaxBrutto.setText(Double.toString(salaryItems.getCalc_advance4IncomeTaxBrutto()));
                editAdvanceIncomeTax.setText(Long.toString(salaryItems.getCalc_advance4IncomeTax()));
                editAmountDue.setText(Double.toString(salaryItems.getCalc_AmountDue()));

                editBossPension.setText(Double.toString(salaryItems.getCalc_BossSocialPension()));
                editBossRent.setText(Double.toString(salaryItems.getCalc_BossSocialRent()));
                editBossAccident.setText(Double.toString(salaryItems.getCalc_BossSocialAccident()));
                editBossFP.setText(Double.toString(salaryItems.getCalc_BossFP()));
                editBossFGSP.setText(Double.toString(salaryItems.getCalc_BossFGSP()));
                editTotalCost.setText(Double.toString(salaryItems.getCalc_TotalCost()));

            }
        }.execute();
    }

}
