package com.example.jerome.niche.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerome.niche.classes.FieldHelper;
import com.example.jerome.niche.R;

import java.util.ArrayList;


/**
 * Created by Jerome on 21/10/2016.
 */

public class TenantProfileActivity extends AppCompatActivity {
    private TextView editName;
    private TextView editDob;
    private TextView editPhone;
    private TextView editMobile;
    private TextView editAdd1;
    private TextView editAdd2;
    private TextView editSuburb;
    private TextView editCity;
    private TextView editCountry;
    private TextView editPassport;
    private TextView editIdNum;
    private TextView editPrevCountry;
    private TextView editRelName;
    private TextView editRelRelationship;
    private TextView editRelAdd;
    private TextView editRelPhoneMobile;
    private RadioButton rdioMale;
    private RadioButton rdioFemale;
    private ArrayList<TextView> fields = new ArrayList<>();
    private FieldHelper fh = new FieldHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_profile);
        setTitle("Personal Information");

        editName = (TextView) findViewById(R.id.editName);
        editDob = (TextView) findViewById(R.id.editDob);
        editPhone = (TextView) findViewById(R.id.editPhone);
        editMobile = (TextView) findViewById(R.id.editMobile);
        editAdd1 = (TextView) findViewById(R.id.editAdd1);
        editAdd2 = (TextView) findViewById(R.id.editAdd2);
        editSuburb = (TextView) findViewById(R.id.editSuburb);
        editCity = (TextView) findViewById(R.id.editCity);
        editCountry = (TextView) findViewById(R.id.editCountry);
        editPassport = (TextView) findViewById(R.id.editPassport);
        editIdNum = (TextView) findViewById(R.id.editIdNum);
        editPrevCountry = (TextView) findViewById(R.id.editPrevCountry);
        editRelName = (TextView) findViewById(R.id.editRelName);
        editRelRelationship = (TextView) findViewById(R.id.editRelRelationship);
        editRelAdd = (TextView) findViewById(R.id.editRelAdd);
        editRelPhoneMobile = (TextView) findViewById(R.id.editRelPhoneMobile);
        rdioMale = (RadioButton) findViewById(R.id.rdioMale);
        rdioFemale = (RadioButton) findViewById(R.id.rdioFemale);

        fields.add(editName);
        fields.add(editDob);
        fields.add(editPhone);
        fields.add(editMobile);
        fields.add(editAdd1);
        fields.add(editAdd2);
        fields.add(editSuburb);
        fields.add(editCity);
        fields.add(editCountry);
        fields.add(editPassport);
        fields.add(editIdNum);
        fields.add(editPrevCountry);
        fields.add(editRelName);
        fields.add(editRelRelationship);
        fields.add(editRelAdd);
        fields.add(editRelPhoneMobile);
        fields.add(rdioMale);
        fields.add(rdioFemale);

        for(TextView field : fields){
            field.setEnabled(false);
        }

        rdioMale.setClickable(false);
        rdioFemale.setClickable(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.actionbar_tenant_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.editPersonalInfo) {
            fh.setEditableTrue(fields);
            rdioMale.setClickable(true);
            rdioFemale.setClickable(true);

        }else if(id == R.id.savePersonalInfo){
            fh.setEditableFalse(fields);
            rdioMale.setClickable(false);
            rdioFemale.setClickable(false);
        }
        return super.onOptionsItemSelected(item);
    }
}
