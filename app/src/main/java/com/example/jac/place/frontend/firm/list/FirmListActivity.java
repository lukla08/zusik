package com.example.jac.place.frontend.firm.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.frontend.base.BaseAppCompatActivity;
import com.example.jac.place.frontend.firm.edit.FirmEditActivity;
import com.example.jac.place.frontend.firm.list.viewmodel.FirmListViewModel;

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
                intent.putExtra("id", 12);
                startActivity(intent);
            }
        });

        firmListViewModel = ViewModelProviders.of(this).get(FirmListViewModel.class);
        firmListViewModel.getFirms().observe(this, firmList -> {
            int size = 0;
            if (firmList != null)
                size = firmList.size();

            Log.d(AppConst.APP_TAG, "firmList update, size :" + size);
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        Firm f = new Firm();
//        f.setFirmCode("code");
//
//        getBackgroundExecutor().execute(db -> {
//            db.firmsDao().insertOrUpdate(f);
//        });
//    }


}
