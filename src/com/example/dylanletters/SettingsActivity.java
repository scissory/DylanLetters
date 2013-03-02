package com.example.dylanletters;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

public class SettingsActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dylan_keyboard, menu);
		menu.removeItem(R.id.menu_settings);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

	  /*
	   * Because it's onlt ONE option in the menu.
	   * In order to make it simple, We always start SetPreferenceActivity
	   * without checking.
	   */
	  
	  Intent intent = new Intent();
	        intent.setClass(SettingsActivity.this, DylanKeyboardActivity.class);
	        startActivityForResult(intent, 0); 
	  
	        return true;
	 }
	
		
}
