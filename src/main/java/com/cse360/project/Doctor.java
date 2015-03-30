package com.cse360.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Doctor implements Serializable {
	String first_name;
	String last_name;
	String password;
	List<Patient> patients;

	public Doctor() {
		first_name = "";
		last_name = "";
		password = "";
		patients = new ArrayList<Patient>();
	}

	public Doctor(String fn, String ln, String pw) {
		first_name = fn;
		last_name = ln;
		password = pw;
		patients = new ArrayList<Patient>();

	}

	public void setFirstName(String fn) {
		first_name = fn;
	}

	public void setLastName(String ln) {
		last_name = ln;
	}

	public void setPassword(String pw) {
		password = pw;
	}
	public void addPatient(Patient pt){
		patients.add(pt);
	}
	public List<Patient> getPts(){
		return patients;
	}
}
