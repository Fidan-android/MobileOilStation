package com.example.mobileoilstation.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.example.mobileoilstation.R;

public class ShowDialog {
    public static void showDialog(Context context){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(R.layout.dialog_view);
        dialog.create().show();
    }
}
