package com.era.nightdozor.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class Constants {

    public static ProgressDialog initProgressDialog(Context context, String text) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(text);
        return progressDialog;
    }

}
