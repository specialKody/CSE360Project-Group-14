package com.cse360.project;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.SpinnerAdapter;
import android.widget.ArrayAdapter;

public class AddUser extends Activity {
	SharedPreferences prefs;
	LinearLayout form_frag, doctor_ll, submit;
	Spinner spin_doctor;
	RadioButton rb_doctor, rb_patient;
	EditText lastname, firstname, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user);
		prefs = this.getSharedPreferences("com.cse360.project",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		form_frag = (LinearLayout) findViewById(R.id.form_frag);
		doctor_ll = (LinearLayout) findViewById(R.id.doctor_ll);
		spin_doctor = (Spinner) findViewById(R.id.spin_doctor);
        SpinnerAdapter mSpinnerAdapter;
        mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.doctor_array, android.R.layout.simple_spinner_dropdown_item);
        spin_doctor.setAdapter(mSpinnerAdapter);
		submit = (LinearLayout) findViewById(R.id.submit);
		rb_doctor = (RadioButton) findViewById(R.id.rb_doctor);
		rb_patient = (RadioButton) findViewById(R.id.rb_patient);
		firstname = (EditText) findViewById(R.id.first_name_txt);
		lastname = (EditText) findViewById(R.id.last_name_txt);
		password = (EditText) findViewById(R.id.pw_txt);

		RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				form_frag.setVisibility(View.VISIBLE);
				submit.setVisibility(View.VISIBLE);

				switch (checkedId) {
				case R.id.rb_doctor:
					doctor_ll.setVisibility(View.GONE);
					break;
				case R.id.rb_patient:
					doctor_ll.setVisibility(View.VISIBLE);
					break;
				}
			}
		});

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                if(firstname.getText().length()<2 || lastname.getText().length()<2  || password.getText().length()<2 ){
                    Toast.makeText(getBaseContext(), "You must complete all fields to continue", Toast.LENGTH_LONG).show();
                }
                else {
                    if (rb_doctor.isChecked()) {
                        Doctor curUser = new Doctor(firstname.getText().toString(),
                                lastname.getText().toString(), password.getText()
                                .toString());
                        //FOR THE SAKE OF TESTING, ADDING A COUPLE DUMMY PATIENTS
                        Patient tempPt = new Patient("Typhoid", "Mary", "", curUser.last_name + curUser.first_name);
                        int tempArr[] = {1, 2, 3};
                        tempPt.addValues(tempArr);
                        int tempArr1[] = {3, 3, 3};
                        tempPt.addValues(tempArr1);
                        curUser.addPatient(tempPt);

                        tempPt = new Patient("Patient", "Zero", "", curUser.last_name + curUser.first_name);
                        int tempArr2[] = {3, 2, 1};
                        tempPt.addValues(tempArr2);
                        int tempArr3[] = {1, 1, 1};
                        tempPt.addValues(tempArr3);
                        curUser.addPatient(tempPt);

                        tempPt = new Patient("Some", "Dude", "", curUser.last_name + curUser.first_name);
                        int tempArr4[] = {2, 4, 6};
                        tempPt.addValues(tempArr4);
                        int tempArr5[] = {8, 3, 1};
                        tempPt.addValues(tempArr5);
                        curUser.addPatient(tempPt);

                        try {
                            InternalStorage
                                    .writeObject(getBaseContext(),
                                            curUser.last_name + curUser.first_name,
                                            curUser);
                        } catch (IOException e) {
                            Log.e("ERR", e.getMessage());
                        }

                        prefs.edit().putString("curUser",
                                curUser.last_name + curUser.first_name).commit();
                        prefs.edit().putInt("user_type", 2).commit();

                    } else {
                        String derp = spin_doctor.getSelectedItem().toString();
                        Patient curUser = new Patient(firstname.getText().toString(),
                                lastname.getText().toString(), password.getText()
                                .toString(), derp);
                        //FOR THE SAKE OF TESTING, ADDING A COUPLE DUMMY PATIENT OBJECTS
                        int tempArr[] = {1, 2, 3};
                        curUser.addValues(tempArr);
                        int tempArr1[] = {3, 3, 3};
                        curUser.addValues(tempArr1);


                        //curUser.addPatient(new Patient("Typhoid", "Mary", "", curUser.last_name + curUser.first_name));
                        //curUser.addPatient(new Patient("Patient", "Zero", "", curUser.last_name + curUser.first_name));
                        //curUser.addPatient(new Patient("Some", "Dude", "", curUser.last_name + curUser.first_name));

                        try {
                            InternalStorage
                                    .writeObject(getBaseContext(),
                                            curUser.last_name + curUser.first_name,
                                            curUser);
                        } catch (IOException e) {
                            Log.e("ERR", e.getMessage());
                        }

                        prefs.edit().putString("curUser",
                                curUser.last_name + curUser.first_name).commit();
                        prefs.edit().putInt("user_type", 1).commit();

                    }

                    prefs.edit().putBoolean("firsttime", false).commit();

                    startActivity(new Intent(AddUser.this, Start.class));
                    AddUser.this.finish();
                }
			}
		});
	}
}
