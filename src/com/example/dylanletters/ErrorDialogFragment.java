package com.example.dylanletters;

import android.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ErrorDialogFragment extends DialogFragment {
	public static ErrorDialogFragment newInstance(String message) {	
		ErrorDialogFragment newInstance = new ErrorDialogFragment();
		Bundle args = new Bundle();
		args.putString("errorMessage", message);
		newInstance.setArguments(args);
		return newInstance;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final String message = getArguments().getString("errorMessage");
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle("DylanLetters Error");
		alertDialog.setMessage("Application error:  " + message );
		alertDialog.setIcon(android.R.drawable.btn_star);
		alertDialog.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		return alertDialog.create();
	}

}
