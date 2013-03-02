package com.example.dylanletters;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.SocketException;
import java.util.Locale;


@SuppressLint("DefaultLocale")
public class DylanKeyboardActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	String mHost;
	String mHostPort;
	int mFontIndex;
	int mFontSize;
	static String threadError;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dylan_keyboard);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		getHostPreference();

	}
	
	@Override
	protected void onRestart() {
		getHostPreference();
	}
	
	private void getHostPreference() {

		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		  
		  String host_address = mySharedPreferences.getString("HostAddress", "");
		  String host_port = mySharedPreferences.getString("HostPort", "");
		  mFontIndex = mySharedPreferences.getInt("FontIndex", 0);
		  mFontSize = mySharedPreferences.getInt("FontSize", 0);
		  
		  
		  
		  if(host_address.isEmpty() || host_port.isEmpty()) {
			  
			  showErrorDialogFragment(getString(R.string.no_host_found));
		  } else {
			  mHost = host_address;
			  this.setTitle(getString(R.string.title_activity_dylan_keyboard) + " - " + mHost);
			  mHostPort = host_port;
			  try  {
				  Integer.parseInt(host_port);
			  }
			  catch(Exception ex) {
				  showErrorDialogFragment(getString(R.string.no_port_found));
				  return;
			  }
			  
			 
		  }
		  
		  
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dylan_keyboard, menu);
		menu.removeItem(R.id.menu_keyboard);

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
	        intent.setClass(DylanKeyboardActivity.this, SettingsActivity.class);
	        startActivityForResult(intent, 0); 
	  
	        return true;
	 }
	
	public void showErrorDialogFragment(String errorMessage) {
		DialogFragment newFragment = ErrorDialogFragment.newInstance(errorMessage);
		newFragment.show(getFragmentManager(), errorMessage);
	}
	
	public void sendCharacter(char input) {
		new SenderThread().execute(String.valueOf(input), mHost, mHostPort);
	}
	
	
	
	private class SenderThread extends AsyncTask<String, Integer, Integer> {

	
		@Override
		protected Integer doInBackground(String... params) {
			
			int result = 0;
			
			try {
						
						
				//sender = new Socket(dlloy1, 13000);
				//sender = new Socket("192.168.0.195", 13000);
				
				String host = params[1];
				int port = Integer.valueOf(params[2]);
				
				if(Character.isLetter(host.charAt(0))) 
					host = Inet4Address.getByName(host).toString();
						
						
				
				if(!host.isEmpty() && port != 0 ) {

					Socket sender = new Socket(host, port);
					OutputStream outStream = sender.getOutputStream();
					OutputStreamWriter writer = new OutputStreamWriter(outStream);

					//PrintWriter writer = new PrintWriter(outStream);
					writer.write(params[0].charAt(0));
						
					writer.close();
					sender.close();
					
				}
			} catch (SocketException se) {
				result = 1;
				DylanKeyboardActivity.this.threadError = se.toString();
			} catch (IOException ie) {
				result = 2;
				DylanKeyboardActivity.this.threadError = ie.toString();
			} catch (Exception e) {
				result = 99; 
				DylanKeyboardActivity.this.threadError = e.toString();
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			if(result != null && result.intValue() > 0) {
				DylanKeyboardActivity.this.showErrorDialogFragment(DylanKeyboardActivity.this.threadError);
				DylanKeyboardActivity.this.threadError = null;
			}
		}
	}


	
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private KeyboardSectionFragment keyboard;
		private SymbolSectionFragment symbols;
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			Fragment fragment = null;
			
			switch (position) {
			case 0:
				
				fragment = KeyboardSectionFragment.newInstance(mFontIndex, mFontSize);
				break;
			case 1:
				fragment = SymbolSectionFragment.newInstance(mFontIndex, mFontSize);
				break;
			}
			
			return fragment;
			
			
		}

		@Override
		public int getCount() {
			
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(Locale.US);
			case 1:
				return getString(R.string.title_section2).toUpperCase(Locale.US);
			}
			return null;
		}
	}

	
	
	

}
