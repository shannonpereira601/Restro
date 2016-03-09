package com.example.admin.restro;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by admin on 07/03/2016.
 */
public class Dialog extends DialogFragment{

    LayoutInflater inflater;
    View view;
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.contactus, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);


        return builder.create();
        }


}
