package com.ruo.player.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ruo.player.R;

/**
 * Created by Administrator on 2017/3/27.
 */

public class DialogUtils {

    private static Dialog dialog;

    //展示一个不确定进度Dialog
    public static void showIndeterminateDialog(Context context, String note) {
        View dialogView = View.inflate(context, R.layout.dialog_indeterminate, null);
        TextView noteView = (TextView) dialogView.findViewById(R.id.dialog_note);
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(dialogView);
        noteView.setText(note);
        dialog.setCancelable(false);
        dialog.show();
        Window dialogWindow = dialog.getWindow(); //Dialog的承载体,设置Dialog的显示效果
        WindowManager.LayoutParams attributes = dialogWindow.getAttributes();
        attributes.width = DensityUtils.dip2px(context, 240);
        attributes.height = DensityUtils.dip2px(context, 120);
        dialogWindow.setAttributes(attributes);
    }

    public static void shutdownIndeterminateDialog() {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 展示分享框
     * @param context
     */
    public static void showShareDialog(Context context) {
        View contentView = View.inflate(context, R.layout.dialog_share, null);
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(contentView, 0, 0, 0, 0);
        dialog.setCancelable(true);
        dialog.show();
    }
}
