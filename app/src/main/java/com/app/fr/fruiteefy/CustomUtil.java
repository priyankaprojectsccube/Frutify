package com.app.fr.fruiteefy;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomUtil {

  static SweetAlertDialog dialog;
    public static void ShowDialog(Context context,Boolean cancelstatus)
        {

            dialog=new SweetAlertDialog(context);
            dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            dialog.setTitleText(context.getResources().getString(R.string.loading));
            dialog.show();
            dialog.setCancelable(cancelstatus);



    }

    public static void DismissDialog(Context context)
    {
        if (dialog.isShowing())
            dialog.dismiss();
    }


}
