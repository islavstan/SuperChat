package com.internship.supercoders.superchat.forgot_password;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.internship.supercoders.superchat.R;


public class ForgotPasswordDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        dialogBuilder.setView(dialogView);
        EditText email = (EditText) dialogView.findViewById(R.id.emailET);
        dialogBuilder.setTitle(getResources().getString(R.string.title));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.send), (dialogInterface, i) -> {

        }).setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());


        final AlertDialog dialog = dialogBuilder.create();

        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }
}
