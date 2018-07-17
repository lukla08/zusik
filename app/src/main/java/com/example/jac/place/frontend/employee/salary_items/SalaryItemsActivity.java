package com.example.jac.place.frontend.employee.salary_items;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.app.utils.StringUtils;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.backend.model.SalaryItems;
import com.example.jac.place.backend.model.utils.SalaryItemsCalcUtils;

public class SalaryItemsActivity extends AppCompatActivity {

    public static String EXTRA_KEY_SELECTED_EMPLOYEE_ID = "selected_employee_id";

    private long selectedEmployeeId;
    private Employee selectedEmployee;

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

    private CheckBox cEditable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedEmployeeId = getIntent().getLongExtra(EXTRA_KEY_SELECTED_EMPLOYEE_ID, 0);
        Log.d(AppConst.APP_TAG, "selectedEmployeeId : " + selectedEmployeeId);

        setContentView(R.layout.salary_items_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_employee_edit);
        setSupportActionBar(toolbar);

        setTitle("Składniki");

        cEditable = findViewById(R.id.cEditable);
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
        editSocialTax = findViewById(R.id.editSocialTax);

         TextWatcher socialPartTextWatcher = new TextWatcher() {
             @Override  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
             @Override  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
             @Override  public void afterTextChanged(Editable editable) {
                double total = StringUtils.toDouble(editSocialPension) + StringUtils.toDouble(editSocialRent) + StringUtils.toDouble(editSocialIllness);
                editSocialTax.setText(Double.toString(total));
             }
         };

        editSocialPension.addTextChangedListener(socialPartTextWatcher);
        editSocialRent.addTextChangedListener(socialPartTextWatcher);
        editSocialIllness.addTextChangedListener(socialPartTextWatcher);

        editBossPension = findViewById(R.id.editBossPension);
        editBossRent = findViewById(R.id.editBossRent);
        editBossAccident = findViewById(R.id.editBossAccident);
        editBossFP = findViewById(R.id.editBossFP);
        editBossFGSP = findViewById(R.id.editBossFGSP);
        editTotalCost = findViewById(R.id.editTotalCost);

        TextWatcher bossPartTextWatcher = new TextWatcher() {
            @Override  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override  public void afterTextChanged(Editable editable) {
                double total = StringUtils.toDouble(editBossPension)
                        + StringUtils.toDouble(editBossRent) + StringUtils.toDouble(editBossAccident)
                        + StringUtils.toDouble(editBossFP) + StringUtils.toDouble(editBossFGSP)
                        + StringUtils.toDouble(editAmountDue);
                editTotalCost.setText(Double.toString(total));
            }
        };
        editBossPension.addTextChangedListener(bossPartTextWatcher);
        editBossRent.addTextChangedListener(bossPartTextWatcher);
        editBossAccident.addTextChangedListener(bossPartTextWatcher);
        editBossFP.addTextChangedListener(bossPartTextWatcher);
        editBossFGSP.addTextChangedListener(bossPartTextWatcher);
        editAmountDue.addTextChangedListener(bossPartTextWatcher);

        cEditable.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                updateEditability();
            }
        });

        initializeControls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salary_item, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_save :
                saveCurrentSalary();
                return true;
            case  R.id.action_next:
                selectEmployee(true);
                return true;
            case  R.id.action_prev:
                selectEmployee(false);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void selectEmployee(boolean isSelectNext) {
        new AsyncTask<Void, Void, Employee>() {

            @Override
            protected Employee doInBackground(Void... voids) {
                directStoreSalary();
                SalaryDatabase salaryDatabase = SalaryDatabase.getInstance(SalaryItemsActivity.this);
                Employee nextEmployee = null;
                if (isSelectNext)
                    return salaryDatabase.employeeDao().getNextEmployee(selectedEmployee.getFirmId(), selectedEmployee.getName());
                else
                    return salaryDatabase.employeeDao().getPrevEmployee(selectedEmployee.getFirmId(), selectedEmployee.getName());
            }

            @Override
            protected void onPostExecute(Employee employee) {
                if (employee != null) {
                    getIntent().putExtra(EXTRA_KEY_SELECTED_EMPLOYEE_ID, employee.getEmployeeId());
                    selectedEmployeeId = employee.getEmployeeId();
                    selectedEmployee = employee;
                    initializeControls();
                }
            }
        }.execute();
    }

    private void saveCurrentSalary() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                directStoreSalary();
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                SalaryItemsActivity.this.finish();
            }
        }.execute();

    }

    private void directStoreSalary() {
        SalaryDatabase salaryDatabase = SalaryDatabase.getInstance(SalaryItemsActivity.this);
        SalaryItems storedSalary = getRecordPopulatedWithControls();
        if (storedSalary.getEditable() == 0) {
            Firm selectedFirm = salaryDatabase.firmsDao().getSelectedFirm(selectedEmployee.getFirmId());
            storedSalary = SalaryItemsCalcUtils.prepareSalaryItems4Employee(selectedEmployee, selectedFirm);
        }
        salaryDatabase.salaryItemsDao().insertOrUpdate(storedSalary);
    }

    SalaryItems getRecordPopulatedWithControls() {
        SalaryItems salaryItems = new SalaryItems();
        salaryItems.setEmployeeId(selectedEmployeeId);
        salaryItems.setEditable(cEditable.isChecked()? 1:0);

        salaryItems.setEmployee_salary(selectedEmployee.getSalary());
        salaryItems.setEmployee_salary12m(selectedEmployee.getAvg12MSalary());
        salaryItems.setEmployee_illnessDays(selectedEmployee.getIllnessDays());

        salaryItems.setCalc_costsOfObtaining(StringUtils.toDouble(editCostOfObtaining));
        salaryItems.setCalc_salaray12m_netto(StringUtils.toDouble(editCalcSalary12m));
        salaryItems.setCalc_ilnessDailyRate(StringUtils.toDouble(editIllnessDailyRate));
        salaryItems.setCalc_salaryIllnessPart(StringUtils.toDouble(editIllnessPart));
        salaryItems.setCalc_normalDailyRate(StringUtils.toDouble(editNormalDailyRate));;
        salaryItems.setCalc_salaryNormalPart(StringUtils.toDouble(editSalaryNormalRate));;
        salaryItems.setCalc_salaryTotal(StringUtils.toDouble(editSalaryTotal));;

        salaryItems.setCalc_base4SocialTaxes(StringUtils.toDouble(editBase4SocialTax));;
        salaryItems.setCalc_employeeSocialIllness(StringUtils.toDouble(editSocialIllness));;
        salaryItems.setCalc_employeeSocialPension(StringUtils.toDouble(editSocialPension));;
        salaryItems.setCalc_employeeSocialRent(StringUtils.toDouble(editSocialRent));;
        salaryItems.setCalc_employeeSocialTotal(StringUtils.toDouble(editSocialTax));;

        salaryItems.setCalc_base4healthTax(StringUtils.toDouble(editBase4HealthTax));;
        salaryItems.setCalc_healthTaxToTake(StringUtils.toDouble(editHealthToTake));;
        salaryItems.setCalc_healthTaxToDeduct(StringUtils.toDouble(editHealthToDeduct));;
        salaryItems.setCalc_base4IncomeTax(StringUtils.toLong(editBaseIncomeTax));;
        salaryItems.setCalc_advance4IncomeTaxBrutto(StringUtils.toLong(editAdvanceIncomeTaxBrutto));;
        salaryItems.setCalc_advance4IncomeTax(StringUtils.toLong(editAdvanceIncomeTax));;
        salaryItems.setCalc_AmountDue(StringUtils.toLong(editAmountDue));;

        salaryItems.setCalc_BossSocialPension(StringUtils.toDouble(editBossPension));;
        salaryItems.setCalc_BossSocialRent(StringUtils.toDouble(editBossRent));
        salaryItems.setCalc_BossSocialAccident(StringUtils.toDouble(editBossAccident));
        salaryItems.setCalc_BossFP(StringUtils.toDouble(editBossFP));
        salaryItems.setCalc_BossFGSP(StringUtils.toDouble(editBossFGSP));

        salaryItems.setCalc_TotalCost(StringUtils.toDouble(editTotalCost));

        return salaryItems;
    }
    private void updateEditability() {
        TextView editableEdits[] = {editSalaryTotal, editSocialPension, editSocialRent, editSocialIllness,
                editHealthToTake, editAdvanceIncomeTax, editAmountDue,
                editBossPension, editBossRent, editBossAccident,editBossFP, editBossFGSP};

        boolean isEditable = cEditable.isChecked();
        for (TextView tv : editableEdits) {
            tv.setEnabled(isEditable);

            tv.setTypeface(null, isEditable? Typeface.NORMAL :Typeface.BOLD);
        }

    }

    class SalaryObjects {
        final Employee employee;
        final Firm firm;
        final SalaryItems salaryItems;

        public SalaryObjects(Employee employee, Firm firm, SalaryItems salaryItems) {
            this.employee = employee;
            this.firm = firm;
            this.salaryItems = salaryItems;
        }
    }

    private void initializeControls() {

        new AsyncTask<Void, Void, SalaryObjects>() {
            @Override protected SalaryObjects doInBackground(Void... voids) {
                SalaryDatabase database = SalaryDatabase.getInstance(SalaryItemsActivity.this);

                Employee selectedEmployee = database.employeeDao().getSelectedEmployee(selectedEmployeeId);
                Firm connectedFirm = null;
                if (selectedEmployee != null)
                    connectedFirm = database.firmsDao().getSelectedFirm(selectedEmployee.getFirmId());

                SalaryItems salaryItems = database.salaryItemsDao().getSalaryItems4Employee(selectedEmployeeId);
                if (salaryItems == null) {
                    salaryItems = SalaryItemsCalcUtils.prepareSalaryItems4Employee(selectedEmployee, connectedFirm);
                    database.salaryItemsDao().insertOrUpdate(salaryItems);
                }

                SalaryItemsActivity.this.selectedEmployee = selectedEmployee;
                return new SalaryObjects(selectedEmployee, connectedFirm, salaryItems);
            }

            @Override
            protected void onPostExecute(SalaryObjects salaryObjects) {
                Employee emp = salaryObjects.employee;
                SalaryItems salaryItems = salaryObjects.salaryItems;

                editEmployeeName.setText(emp.getName());
                editSalary.setText(Double.toString(emp.getSalary()));
                editSalary12m.setText(Double.toString(emp.getAvg12MSalary()));
                editIllnessDays.setText(Integer.toString(emp.getIllnessDays()));

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

                cEditable.setChecked(salaryItems.getEditable() == 1);

                updateEditability();
            }
        }.execute();
    }

}
