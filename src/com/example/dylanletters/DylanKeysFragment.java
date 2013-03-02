package com.example.dylanletters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


public class DylanKeysFragment extends Fragment {

	
	protected DylanKeysFragment() {
		super.onCreate(null);
	}
	
	public static DylanKeysFragment newInstance(int fontIndex, int fontSize) {
		
		DylanKeysFragment dkFragment = new DylanKeysFragment();
		dkFragment.mFontIndex = fontIndex;
		dkFragment.mFontSize = fontSize;
		return dkFragment;
				
	}

	int mFontIndex;
	int mFontSize;
	protected OnLongClickListener mOnLongClickListener;
	
	
	protected void bindClickListeners(View v) {
		
		if(v instanceof LinearLayout) {
			LinearLayout top = (LinearLayout) v;
			int topLayoutCount = top.getChildCount();
			for(int i=0; i<topLayoutCount; i++) {
				LinearLayout child = (LinearLayout) top.getChildAt(i);
				for (int j=0; j<child.getChildCount(); j++) {
					if(child.getChildAt(j) instanceof Button) {
						Button button = (Button) child.getChildAt(j);
						fixSymbolButtonTest(button);
						button.setOnClickListener(mOnClickListner);
						if(mOnLongClickListener != null && !isControlButton(button)) {
							button.setOnLongClickListener(mOnLongClickListener);
						}
						
						
					}
				}
				
			}
		}
	}
	
	protected void setButtonFormatting(View v, int fontIndex, int fontSize) {
		
		
		if(v instanceof LinearLayout) {
			LinearLayout top = (LinearLayout) v;
			int topLayoutCount = top.getChildCount();
			for(int i=0; i<topLayoutCount; i++) {
				LinearLayout child = (LinearLayout) top.getChildAt(i);
				for (int j=0; j<child.getChildCount(); j++) {
					if(child.getChildAt(j) instanceof Button  ) {
						Button button = (Button) child.getChildAt(j);
						if(button.getId() != R.id.buttonEnter && button.getId() != R.id.buttonSpace){
							button.setTypeface(Utilities.getTypefaceFromIndex(fontIndex));
							button.setTextSize(fontSize);
						}
						
					}
				}
				
			}
		}
	}
	
	protected boolean isControlButton(Button buttonToTest) {
		switch(buttonToTest.getId()){
		case R.id.buttonSpace: case	R.id.buttonBack: case R.id.buttonEnter:
			return true;
		default:
			return false;
		}
		
	}
		
	protected void fixSymbolButtonTest(Button button) {
		
		if(button.getId() == R.id.buttonQuestion) {
			button.setText("?");
		}
		
		if(button.getId() == R.id.buttonBackSlash) {
			button.setText("\\");
		}
		
		if(button.getId() == R.id.buttonQuote) {
			button.setText("\"");
		}
		
	}
	
		


	protected OnClickListener mOnClickListner = new OnClickListener() {
		
		
		@Override
		public void onClick(View v) {
			Button clickedButton = (Button)v;

			char sendChar = getCharToSend(clickedButton);
			DylanKeyboardActivity kb = (DylanKeyboardActivity) v.getContext();
			kb.sendCharacter(sendChar);
					
			
			if(Character.isLetter(sendChar) &&  Character.isUpperCase(sendChar)) {
				
				sendChar = Character.toLowerCase(sendChar);
				clickedButton.setText(String.valueOf(sendChar));
				clickedButton.getBackground().clearColorFilter();
			}
			
		}
		
		private char getCharToSend(Button b) {
			
			switch(b.getId()) {

			case R.id.buttonEnter:
				return '\n';

			case R.id.buttonBack:
				return '\b';

			case R.id.buttonSpace:
				return ' ';
			
			case R.id.buttonPlus:
				return '+';
			
			case R.id.buttonPercent:
				return '%';
				
			default:
				String letterClicked = b.getText().toString();
				char newChar = letterClicked.charAt(0);
				return newChar;
				

			}
		}
	};
	
}
