package com.example.jac.place.frontend.firm;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jac.place.R;
import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.frontend.base.BaseAppCompatActivity;

public class FirmListActivity extends BaseAppCompatActivity {

    private FirmListViewModel firmListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.action_firms);
        setContentView(R.layout.firm_list_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_firm_list);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddFirm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FirmListActivity.this, FirmEditActivity.class);
                intent.putExtra(FirmEditActivity.EXTRA_KEY_SELECTED_RECORD_ID, 0);
                startActivity(intent);
            }
        });

        ListView firmListView = findViewById(R.id.firmListView);
        firmListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Firm selectedFirm = (Firm) adapterView.getItemAtPosition(position);
            Intent intent = new Intent(FirmListActivity.this, FirmEditActivity.class);
            intent.putExtra(FirmEditActivity.EXTRA_KEY_SELECTED_RECORD_ID, selectedFirm.getFirmId());
            startActivity(intent);
        });

        firmListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                showDialogDeleteCurrentRecord(adapterView, view, position);
                return true;
            }
        });

        firmListViewModel = ViewModelProviders.of(this).get(FirmListViewModel.class);
        firmListViewModel.getFirms().observe(this, firmList -> {
            if (firmList.isEmpty()) {
                prepareSampleFirms();
            }

            ArrayAdapter<Firm> firmArrayAdapter = new ArrayAdapter<Firm>(FirmListActivity.this,
                    R.layout.firm_list_item, firmList) {

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View initialView = super.getView(position, convertView, parent);
                    TextView textView = initialView.findViewById(R.id.item_text);
                    Firm firm = (Firm) getItem(position);
                    textView.setTextColor(firm.getDisabled() == 0? Color.BLACK : Color.GRAY);
                    textView.setTypeface(null, firm.getDisabled() == 0? Typeface.BOLD : Typeface.NORMAL);
                    return initialView;
                }
            };

            firmListView.setAdapter(firmArrayAdapter);

        });

    }



    private void prepareSampleFirms() {
        Firm sample1= new Firm();
        sample1.setFirmName("FIRMA A");
        sample1.setAccidentRate(2.45);

        Firm sample2 = new Firm();
        sample2.setFirmName("FIRMA B");
        sample2.setAccidentRate(2.45);

        getBackgroundExecutor().execute(db -> {
            db.firmsDao().insertOrUpdate(sample1, sample2);
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
