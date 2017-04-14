package me.zuichu.sa.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.zuichu.smarandroid.R;

/**
 * Created by office on 2017/4/7.
 */
public class ToastUtil {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void showToastLong(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, String text, View view) {
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToast(Context context, String text, boolean view) {
        Toast toast = new Toast(context);
        toast.setView( View.inflate(context, R.layout.layout_toast, null));
        TextView textView = (TextView) toast.getView().findViewById(R.id.tv_text);
        textView.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
