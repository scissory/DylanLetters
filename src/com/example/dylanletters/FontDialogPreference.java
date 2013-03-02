package com.example.dylanletters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;

public class FontDialogPreference extends DialogPreference {

	public FontDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		 setDialogLayoutResource(R.layout.activity_settings_font);
	}
	
	public FontDialogPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setDialogLayoutResource(R.layout.activity_settings_font);
	}
	
	private Spinner mSpinner;
	private SeekBar mSeekBar;
	private Button mButton;
	private int mFontIndex;
	private int mFontSize;
	private int mFontSizeOriginal;
		
	 
	@Override
	protected View onCreateDialogView() {
		View root = super.onCreateDialogView();
		mSpinner = (Spinner) root.findViewById(R.id.spinnerFontName);
		mSpinner.setOnItemSelectedListener(mOnItemSelectedListener);
		
		mSeekBar = (SeekBar) root.findViewById(R.id.seekBarFontSize);
		mButton = (Button) root.findViewById(R.id.buttonTester);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.font_name_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		mSpinner.setAdapter(adapter);
		
		setControlsFromPreferences();
		
		return root;
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		Editor ed =  mySharedPreferences.edit();
		
		ed.putInt("FontIndex", mSpinner.getSelectedItemPosition());
		ed.putInt("FontSize", mFontSize);
		ed.commit();
		
		
		
	}
	
	@Override
	protected void onBindDialogView(View view) {
		
		
	}
	
	private void setControlsFromPreferences() {
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

		float test = getContext().getResources().getDimension(R.dimen.buttonFontSize);
		mFontSizeOriginal = Math.round(test);
		
		mFontIndex = mySharedPreferences.getInt("FontIndex", 0);
		if(mFontIndex > 4)
			mFontIndex = 0;
		//mFontSize = mySharedPreferences.getInt("FontSize", 0);
		
		if(mFontSize == 0) 
			mFontSize = mFontSizeOriginal;
		
		mSpinner.setSelection(mFontIndex);
		mButton.setTypeface(Utilities.getTypefaceFromIndex(mFontIndex));
		mButton.setTextSize(mFontSizeOriginal);
		mSeekBar.setProgress(50);
		
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
						
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				
				float changePercent = (progress - 50) * .01f;
				mFontSize = Math.abs(Math.round(mFontSizeOriginal * (1 + changePercent)));
				
				
				mButton.setTextSize(mFontSize);
				
				
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	protected OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view, 
				int pos, long id) {
			// An item was selected. You can retrieve the selected item using
			// parent.getItemAtPosition(pos)

			mFontIndex = parent.getSelectedItemPosition();
			mButton.setTypeface(Utilities.getTypefaceFromIndex(mFontIndex));
			
		}


		public void onNothingSelected(AdapterView<?> parent) {
			// Another interface callback
			mFontIndex = 0;
		}
	};


}
