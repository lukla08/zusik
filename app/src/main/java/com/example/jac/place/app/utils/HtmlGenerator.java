package com.example.jac.place.app.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.jac.place.R;
import com.example.jac.place.app.tread_pool.helper.AppConst;
import com.example.jac.place.backend.SalaryDatabase;
import com.example.jac.place.backend.model.Employee;
import com.example.jac.place.backend.model.Firm;
import com.example.jac.place.backend.model.SalaryItems;
import com.example.jac.place.backend.model.utils.SalaryItemsCalcUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlGenerator {
    public File generateHtml(Context ctx, long firmId) throws IOException {
        List<String> rowTemplateLines = readLines(ctx, R.raw.row_template);
        List<String> mainTemplateLines = readLines(ctx, R.raw.main_template);
        List<ModelItem> modelItems = prepareModel(ctx, firmId);
        ModelItem sumItem = prepareSumItem(modelItems);
        modelItems.add(sumItem);

        List<String> processedRows = processRowTemplate(rowTemplateLines, modelItems);
        String joinedRows = StringUtils.join(processedRows, "");

        List<String> resLines = processMainTemplate(mainTemplateLines, joinedRows, sumItem);

        SalaryDatabase salaryDatabase = SalaryDatabase.getInstance(ctx);
        Firm selectedFirm = salaryDatabase.firmsDao().getSelectedFirm(firmId);
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File destDir = new File(externalStorageDirectory, "tmp_place");
        boolean destDirCreated =  destDir.mkdir();
        Log.d(AppConst.APP_TAG, "destDirCreated:" + destDirCreated);
        File destFile  = new File(destDir, selectedFirm.getFirmName() + ".html");
        if (destFile.exists()) {
            Log.d(AppConst.APP_TAG, "destFileExisted");
            boolean deleted = destFile.delete();
            Log.d(AppConst.APP_TAG, "destFileDeleted:" + deleted);
        }

        String resString = StringUtils.join(resLines, " ");

        try (FileOutputStream fos = new FileOutputStream(destFile)) {
            IOUtils.write(resString, fos);
        }


        return destFile;
    }

    private List<String> processMainTemplate(List<String> template, String joinedRows, ModelItem sumItem) {
        List<String> res = new ArrayList<>();
        for (String templateLine : template) {
            templateLine = templateLine.replace("<!--{row_template}-->", joinedRows);

            Map<String, String> dataModel = sumItem.prepareDataModel();
            for (Map.Entry<String, String> dataItem : dataModel.entrySet()) {
                 String key = "{" + dataItem.getKey()  +"}";
                 templateLine = templateLine.replace(key, dataItem.getValue());
             }
             res.add(templateLine);
        }

        return res;
    }

    private List<String> processRowTemplate(List<String> template, List<ModelItem> modelItems) {
        List<String> res = new ArrayList<>();
        for (ModelItem modelItem : modelItems) {
            Map<String, String> dataModel = modelItem.prepareDataModel();

            for (String templateLine : template) {
                for (Map.Entry<String, String> dataItem : dataModel.entrySet()) {
                    String key = "{" + dataItem.getKey()  +"}";
                    templateLine = templateLine.replace(key, dataItem.getValue());
                }
                res.add(templateLine);
            }

        }

        return res;
    }

    private List<ModelItem> prepareModel(Context ctx,  long firmId) {
        List<ModelItem> resList = new ArrayList<>();
        SalaryDatabase database = SalaryDatabase.getInstance(ctx);
        Firm selectedFirm = database.firmsDao().getSelectedFirm(firmId);
        List<Employee> employees = database.employeeDao().getActiveEmployeesByFirm(firmId);
        for (Employee employee : employees) {
            SalaryItems salaryItem = database.salaryItemsDao().getSalaryItems4Employee(employee.getEmployeeId());
            if (salaryItem == null) {
                salaryItem =  SalaryItemsCalcUtils.prepareSalaryItems4Employee(employee, selectedFirm);
                database.salaryItemsDao().insertOrUpdate(salaryItem);
            }

            resList.add(new ModelItem(employee, salaryItem));
        }

        return resList;
    }

    private ModelItem prepareSumItem(List<ModelItem> resList) {
        SalaryItems total = new SalaryItems();
        total.setEmployee_salary(prepareSum(resList, i -> i.getEmployee_salary()));
        total.setCalc_AmountDue(prepareSum(resList, i -> i.getCalc_AmountDue()));
        total.setCalc_employeeSocialTotal(prepareSum(resList, i -> i.getCalc_employeeSocialTotal()));
        total.setCalc_employeeSocialPension(prepareSum(resList, i -> i.getCalc_employeeSocialPension()));
        total.setCalc_employeeSocialRent(prepareSum(resList, i -> i.getCalc_employeeSocialRent()));
        total.setCalc_employeeSocialIllness(prepareSum(resList, i -> i.getCalc_employeeSocialIllness()));
        total.setCalc_healthTaxToTake(prepareSum(resList, i -> i.getCalc_healthTaxToTake()));
        total.setCalc_healthTaxToDeduct(prepareSum(resList, i -> i.getCalc_healthTaxToDeduct()));
        total.setCalc_advance4IncomeTax((long)prepareSum(resList, i -> i.getCalc_advance4IncomeTax()));

        total.setCalc_BossSocialPension(prepareSum(resList, i -> i.getCalc_BossSocialPension()));
        total.setCalc_BossSocialRent(prepareSum(resList, i -> i.getCalc_BossSocialRent()));
        total.setCalc_BossSocialAccident(prepareSum(resList, i -> i.getCalc_BossSocialAccident()));
        total.setCalc_BossFP(prepareSum(resList, i -> i.getCalc_BossFP()));
        total.setCalc_BossFGSP(prepareSum(resList, i -> i.getCalc_BossFGSP()));
        total.setCalc_TotalCost(prepareSum(resList, i -> i.getCalc_TotalCost()));

        Employee emp = new Employee();
        emp.setName("RAZEM");

        return new ModelItem(emp, total);
    }

    interface ValueGetter {
        double getValue(SalaryItems i);
    }
    private double prepareSum(List<ModelItem> src, ValueGetter getter) {
        double res = 0;
        for (ModelItem item : src) {
            res = DoubleUtils.round2Digits(res + getter.getValue(item.salaryItems));
        }

        return res;
    }

    private List<String> readLines(Context ctx, int id) throws IOException {
        List<String> res = new ArrayList<>();
        InputStream inputStream = ctx.getResources().openRawResource(id);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            res.add(line + "\n");
        }
        return res;
    }
}

class ModelItem {
    final Employee employee;
    final SalaryItems salaryItems;

    public ModelItem(Employee employee, SalaryItems salaryItems) {
        this.employee = employee;
        this.salaryItems = salaryItems;
    }

    public Map<String, String> prepareDataModel() {
        boolean isSum = "RAZEM".equalsIgnoreCase(employee.getName());
        Map<String, String> data = new HashMap<>();
        data.put("emp.name", employee.getName());
        data.put("item.employee_salary", getDouble(salaryItems.getEmployee_salary()));
        data.put("item.calc_amountDue", getDouble(salaryItems.getCalc_AmountDue()));
        data.put("item.calc_employeeSocialTotal", getDouble(salaryItems.getCalc_employeeSocialTotal()));
        data.put("item.calc_employeeSocialPension", getDouble(salaryItems.getCalc_employeeSocialPension()));
        data.put("item.calc_employeeSocialRent", getDouble(salaryItems.getCalc_employeeSocialRent()));
        data.put("item.calc_employeeSocialIllness", getDouble(salaryItems.getCalc_employeeSocialIllness()));
        data.put("item.calc_base4healthTax", isSum? "" : getDouble(salaryItems.getCalc_base4healthTax()));
        data.put("item.calc_healthTaxToTake", getDouble(salaryItems.getCalc_healthTaxToTake()));
        data.put("item.calc_healthTaxToDeduct", getDouble(salaryItems.getCalc_healthTaxToDeduct()));
        data.put("item.calc_advance4IncomeTax", "" + salaryItems.getCalc_advance4IncomeTax());
        data.put("item.calc_BossSocialPension", getDouble(salaryItems.getCalc_BossSocialPension()));
        data.put("item.calc_BossSocialRent", getDouble(salaryItems.getCalc_BossSocialRent()));
        data.put("item.calc_BossSocialAccident", getDouble(salaryItems.getCalc_BossSocialAccident()));
        data.put("item.calc_BossFP", getDouble(salaryItems.getCalc_BossFP()));
        data.put("item.calc_BossFGSP", getDouble(salaryItems.getCalc_BossFGSP()));
        data.put("item.calc_TotalCost", getDouble(salaryItems.getCalc_TotalCost()));

        if (isSum) {
            double totalSocial = DoubleUtils.round2Digits(salaryItems.getCalc_employeeSocialTotal()
                    +salaryItems.getCalc_BossSocialPension() +salaryItems.getCalc_BossSocialRent()
                    +salaryItems.getCalc_BossSocialAccident());

            data.put("total_spoleczne", getDouble(totalSocial));

            data.put("total_zdrow", getDouble(salaryItems.getCalc_healthTaxToTake()));
            data.put("total_FP", getDouble(salaryItems.getCalc_BossFP()));
            data.put("total_FGSP", getDouble(salaryItems.getCalc_BossFGSP()));
            data.put("total_PIT", "" + salaryItems.getCalc_advance4IncomeTax());

            double totalZUS = DoubleUtils.round2Digits(totalSocial + salaryItems.getCalc_healthTaxToTake()
                    +salaryItems.getCalc_BossFP() + salaryItems.getCalc_BossFGSP());

            data.put("total_ZUS", "" + getDouble(totalZUS));
            data.put("total_PIT_ZUS", "" + getDouble(totalZUS + salaryItems.getCalc_advance4IncomeTax()));
        }

        return data;
    }

    private String getDouble(double v) {
        return Double.toString(DoubleUtils.round2Digits(v));
    }
}

