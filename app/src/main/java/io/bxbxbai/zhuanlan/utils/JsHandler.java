package io.bxbxbai.zhuanlan.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import io.bxbxbai.zhuanlan.R;

/**
 * Created by xuebin on 15/7/8.
 */
public class JsHandler {

    Activity activity;
    String TAG = "JsHandler";
    WebView webView;


    public JsHandler(Activity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    /**
     * This function handles call from JS
     */
    public void jsFnCall(String jsString) {
        showDialog(jsString);
    }

    /**
     * This function handles call from Android-Java
     */
    @JavascriptInterface
    public void javaFnCall(String jsString) {
        final String webUrl = "javascript:diplayJavaMsg('" + jsString + "')";
        // Add this to avoid android.view.windowmanager$badtokenexception unable to add window
        if (!activity.isFinishing())
            // load url on UI main thread
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    webView.loadUrl(webUrl);
                }
            });
    }

    /**
     * function shows Android-Native Alert Dialog
     */
    public void showDialog(String msg) {

        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setTitle(activity.getString(R.string.app_name));
        dialog.setMessage(msg);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }
}
