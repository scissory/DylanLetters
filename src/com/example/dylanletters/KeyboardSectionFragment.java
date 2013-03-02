package com.example.dylanletters;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;



public class KeyboardSectionFragment extends DylanKeysFragment {
	

	public static KeyboardSectionFragment newInstance(int fontIndex, int fontSize) {
		
		KeyboardSectionFragment dkFragment = new KeyboardSectionFragment();
		dkFragment.mFontIndex = fontIndex;
		dkFragment.mFontSize = fontSize;
		return dkFragment;
	}
	
	View mLayoutView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		mLayoutView = inflater.inflate(R.layout.activity_key_board, container, false);
		setLongCLickListner();
		bindClickListeners(mLayoutView);
		return mLayoutView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		  
		String host_address = mySharedPreferences.getString("HostAddress", "");
		mFontIndex = mySharedPreferences.getInt("FontIndex", 0);
		mFontSize = mySharedPreferences.getInt("FontSize", 0);
		setButtonFormatting(mLayoutView, mFontIndex, mFontSize);
		getActivity().setTitle(getString(R.string.title_activity_dylan_keyboard) + " - " + host_address);
	}
	
	
	
	
	private void setLongCLickListner() {
		mOnLongClickListener = new OnLongClickListener() {
			public boolean onLongClick(View v) {
				Button clickedButton = (Button)v;
				String letterClicked = clickedButton.getText().toString();
				char newChar = letterClicked.charAt(0);
				if(Character.isUpperCase(newChar)) {
					newChar = Character.toLowerCase(newChar);
					clickedButton.getBackground().clearColorFilter();
				}
				else {
					newChar = Character.toUpperCase(newChar);
					clickedButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
				}
				clickedButton.setText(String.valueOf(newChar));

				return true;
			}
		};

	}

	
	
}
