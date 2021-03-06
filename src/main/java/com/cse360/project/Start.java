package com.cse360.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.parse.Parse;
import com.parse.ParseObject;


public class Start extends Activity {
	SharedPreferences prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = this.getSharedPreferences("com.cse360.project",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

		//prefs.edit().clear().commit();

		if(prefs.getBoolean("firsttime",true)){ //If this is the first time app has been opened, send to add user page
			startActivity(new Intent(Start.this, AddUser.class));
			Start.this.finish();
		}
		else if(prefs.getInt("user_type", 0)==1){ //If current user is a patient, send to patient activity
			//This is where we would check the time against time of last assessment
            startActivity(new Intent(Start.this, Assessment.class));
			Start.this.finish();
		}
		else if(prefs.getInt("user_type", 0)==2){ //If current user is a doctor, send to doctor activity
			startActivity(new Intent(Start.this, Doctor_Main.class));
			Start.this.finish();
		}
		else{									//If no current user is found, send to page to add user
			startActivity(new Intent(Start.this, AddUser.class));
			Start.this.finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
