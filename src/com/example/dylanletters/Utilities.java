package com.example.dylanletters;

import android.graphics.Typeface;

public class Utilities {
	
	public static Typeface getTypefaceFromIndex(int value) {
		
		switch(value) {
		case 1:
			return Typeface.SANS_SERIF;
		case 2:
			return Typeface.SERIF;
		case 3:
			return Typeface.MONOSPACE;
		case 0: case 99: default:
			return Typeface.DEFAULT;

		}
	}

}
