package com.example.jerome.niche.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jerome.niche.classes.FieldHelper;
import com.example.jerome.niche.R;
import com.example.jerome.niche.classes.NicheUser;
import com.example.jerome.niche.classes.Tenant;
import com.example.jerome.niche.dao.AlterTenantInformation;
import com.example.jerome.niche.dao.LoadTenantInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Jerome on 21/10/2016.
 */

public class TenantProfileActivity extends AppCompatActivity {

    String urlAddressLoad = "http://kappatid.co.nf/loadTenantInformation.php";
    String urlAddressAlter = "http://kappatid.co.nf/alterTenantInformation.php";
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
    private TextView editRelAdd1;
    private TextView editRelAdd2;
    private TextView editRelSuburb;
    private TextView editRelCity;
    private TextView editRelPhoneMobile;
    private RadioButton rdioMale;
    private RadioButton rdioFemale;
    private ArrayList<TextView> fields = new ArrayList<>();
    private FieldHelper fh = new FieldHelper();
    private String name;
    private String dob;
    private String phone;
    private String mobile;
    private String address;
    private String country;
    private String passport;
    private String idNum;
    private String prevCountry;
    private String relName;
    private String relRelationship;
    private String relAddress;
    private String relPhoneOrMobile;
    private JSONObject tenantInfo;
    //private Tenant tenant;



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
        editRelAdd1 = (TextView) findViewById(R.id.editRelAdd1);
        editRelAdd2 = (TextView) findViewById(R.id.editRelAdd2);
        editRelSuburb = (TextView) findViewById(R.id.editRelSuburb);
        editRelCity = (TextView) findViewById(R.id.editRelCity);
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
        fields.add(editRelAdd1);
        fields.add(editRelAdd2);
        fields.add(editRelSuburb);
        fields.add(editRelCity);
        fields.add(editRelPhoneMobile);
        fields.add(rdioMale);
        fields.add(rdioFemale);

        for(TextView field : fields){
            field.setEnabled(false);
        }

        rdioMale.setClickable(false);
        rdioFemale.setClickable(false);

        LoadTenantInformation lti = new LoadTenantInformation(this, getIntent().getExtras().getString("userID"), editName, editDob, editPhone, editMobile, editCountry, editPassport,
                editIdNum, editPrevCountry, editRelName, editRelRelationship, editRelPhoneMobile);
        lti.execute(urlAddressLoad);

    }

    public String getTenantJsonObject(){
        try {
            tenantInfo = new JSONObject();
            tenantInfo.put("name", editName.getText().toString());
            tenantInfo.put("dob", editDob.getText().toString());
            tenantInfo.put("phone", editPassport.getText().toString());
            tenantInfo.put("mobile", editMobile.getText().toString());
            tenantInfo.put("country", editCountry.getText().toString());
            tenantInfo.put("passport", editPassport.getText().toString());
            tenantInfo.put("idNum", editIdNum.getText().toString());
            tenantInfo.put("previousCountry", editPrevCountry.getText().toString());
            tenantInfo.put("relName", editRelName.getText().toString());
            tenantInfo.put("relPhone", editRelPhoneMobile.getText().toString());

        }catch(JSONException e){
            e.printStackTrace();
        }
        return tenantInfo.toString();
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
            AlterTenantInformation eti = new AlterTenantInformation(this);
            eti.execute(urlAddressAlter);

        }
        return super.onOptionsItemSelected(item);
    }
}
