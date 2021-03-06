package com.example.jac.place.frontend.employee;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.app.utils.HtmlGenerator;
import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.frontend.base.BaseAppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployeeListActivity extends BaseAppCompatActivity {

    public static String EXTRA_KEY_SELECTED_FIRM_ID = "selected_firm_id";
    public static String EXTRA_KEY_SELECTED_FIRM_NAME = "selected_firm_name";

    private EmployeeListViewModel employeeListViewModel;

    private long selectedFirmId;
    private String selectedFirmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedFirmId = getIntent().getLongExtra(EXTRA_KEY_SELECTED_FIRM_ID, 0);
        selectedFirmName = getIntent().getStringExtra(EXTRA_KEY_SELECTED_FIRM_NAME);

        setContentView(R.layout.employee_list_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_employee_list);
        setSupportActionBar(toolbar);

        setTitle(selectedFirmName + " - pracownicy");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddEmployee);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EmployeeListActivity.this, EmployeeEditActivity.class);
                intent.putExtra(EmployeeEditActivity.EXTRA_KEY_SELECTED_RECORD_ID, 0);
                intent.putExtra(EmployeeEditActivity.EXTRA_KEY_SELECTED_FIRM_ID, selectedFirmId);
                startActivity(intent);
            }
        });

        ListView firmListView = findViewById(R.id.employee_list_view);
        firmListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Employee selected = (Employee) adapterView.getItemAtPosition(position);
            Intent intent = new Intent(EmployeeListActivity.this, EmployeeEditActivity.class);
            intent.putExtra(EmployeeEditActivity.EXTRA_KEY_SELECTED_RECORD_ID, selected.getEmployeeId());
            intent.putExtra(EmployeeEditActivity.EXTRA_KEY_SELECTED_FIRM_ID, selectedFirmId);

            startActivity(intent);
        });

        firmListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                showDialogDeleteCurrentRecord(adapterView, view, position);
                return true;
            }
        });

        employeeListViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel.class);
        employeeListViewModel.getEmployeesByFirm(selectedFirmId).observe(this, employeeList -> {
            if (employeeList.isEmpty()) {
                prepareSampleEmployees();
            }

            ArrayAdapter<Employee> firmArrayAdapter = new ArrayAdapter<Employee>(EmployeeListActivity.this,
                    R.layout.employee_list_item, employeeList) {

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View initialView = super.getView(position, convertView, parent);
                    TextView textView = initialView.findViewById(R.id.item_text);
                    Employee firm = (Employee) getItem(position);
                    textView.setTextColor(firm.getDisabled() == 0? Color.BLACK : Color.GRAY);
                    textView.setTypeface(null, firm.getDisabled() == 0? Typeface.BOLD : Typeface.NORMAL);
                    return initialView;
                }
            };

            firmListView.setAdapter(firmArrayAdapter);

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_send :
                new AsyncTask<Void, Void, Object>() {
                    @Override
                    protected Object doInBackground(Void... voids) {
                        try {
                            return new HtmlGenerator().generateHtml(EmployeeListActivity.this, selectedFirmId);
                        } catch (Throwable e) {
                            return e;
                        }
                    }

                    @Override
                    protected void onPostExecute(Object returnedData) {
                        if (returnedData instanceof File) {
                            try {
                                File destFile = (File) returnedData;
                                Uri uri = Uri.fromFile(destFile);
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("*/*");
                                intent.putExtra(Intent.EXTRA_EMAIL, "jarochyb@gmail.com");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "dane płacowe");
                                intent.putExtra(Intent.EXTRA_STREAM, uri);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                returnedData = e;
                            }
                        }

                        String message = "Operacja zakończona powodzeniem";
                        int duration = Toast.LENGTH_SHORT;
                        if (returnedData instanceof  Throwable) {
                            Throwable caughtException = (Throwable) returnedData;
                            message = caughtException.getMessage();
                            duration = Toast.LENGTH_LONG;
                            caughtException.printStackTrace();
                            Log.e(AppConst.APP_TAG, Log.getStackTraceString(caughtException));
                        }

                        Toast.makeText(EmployeeListActivity.this, message, duration).show();

                    }
                }.execute();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void prepareSampleEmployees() {
        Employee emp1 = new Employee();
        emp1.setName("Pracownik 1");
        emp1.setSalary(1680);
        emp1.setAvg12MSalary(1620);
        emp1.setIllnessDays(3);

        Employee emp2 = new Employee();
        emp2.setName("Pracownik 2");
        emp2.setAvg12MSalary(2600);
        emp2.setSalary(2500);

        Employee emp3 = new Employee();
        emp3.setName("Pracownik 3");
        emp3.setSalary(3000);
        emp3.setAvg12MSalary(4400);

        Employee emp4 = new Employee();
        emp4.setName("Pracownik 4");
        emp4.setAvg12MSalary(1600);
        emp4.setSalary(1800);

        getBackgroundExecutor().execute(db -> {
            List<Firm> firms = db.firmsDao().getFirmsData();
            if (firms.size() > 0) {
                emp1.setFirmId(firms.get(0).getFirmId());
                emp2.setFirmId(firms.get(0).getFirmId());
                emp3.setFirmId(firms.get(0).getFirmId());
                emp4.setFirmId(firms.get(0).getFirmId());
            }
            if (firms.size() > 1) {
                emp3.setFirmId(firms.get(1).getFirmId());
                emp4.setFirmId(firms.get(1).getFirmId());
            }
            db.employeeDao().insertOrUpdate(emp1, emp2, emp3, emp4);
        });
    }

    private void showDialogDeleteCurrentRecord(AdapterView<?> adapterView, View view, int position) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy skasowac firmę ?")
                .setPositiveButton("Tak", dialogClickListener)
                .setNegativeButton("Nie", dialogClickListener).show();
    }


}
