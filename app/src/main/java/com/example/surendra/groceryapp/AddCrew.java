package com.example.surendra.groceryapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import customadaptors.ANavigationCustom;
import customadaptors.NavigationCustom;
import webserviceAccess.AppUserService;
import webserviceAccess.CrewService;

public class AddCrew extends AppCompatActivity implements View.OnClickListener {
    private ListView mDrawerList;
    ANavigationCustom n = new ANavigationCustom();
    EditText crewId = null;
    EditText firstname = null;
    EditText role=null;
    EditText lastname = null;
    EditText email=null;
    EditText phone = null;
    EditText address = null;
    EditText dob = null;
    DateFormat dateFormatter = null;
    Button addCrew=null;
    DatePickerDialog dpd = null;
    CrewService cs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crew);
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        mDrawerList = (ListView)findViewById(R.id.navList);
        cs=new CrewService();
        n.addDrawerItems(mDrawerList,this);
        navigateActivity(this);
        findViewsById();
        setDateTimeField();

    }

    private void setCrewValues(){
        String crewId = this.crewId.getText().toString();
        String role = this.role.getText().toString();
        String firstname = this.firstname.getText().toString();
        String lastname = this.lastname.getText().toString();
        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();
        String address = this.address.getText().toString();
        String date = this.dob.getText().toString();
        String status = cs.addCrew(crewId,role,firstname,lastname,email,phone,address,date);
        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
    }

    private void findViewsById() {
        dob = (EditText) findViewById(R.id.dob);
        crewId = (EditText) findViewById(R.id.crewId);
        role = (EditText) findViewById(R.id.role);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        addCrew = (Button) findViewById(R.id.addCrew);
        addCrew.setOnClickListener(this);
    }

    public void setDateTimeField() {
        dob.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dob.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void navigateActivity(final Context context){
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,n.renderPage(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == dob){
            dpd.show();
        }
        if (v==addCrew){
            setCrewValues();
        }
    }
}
