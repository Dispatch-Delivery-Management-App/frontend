package com.fullstack.frontend.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class AlertDialogHelper {
    private AlertDialogHelper() {

    }

    public static void show(Context context, String title, String errorMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title
        ).setMessage(errorMessage).create();
        alertDialog.show();
    }
}
