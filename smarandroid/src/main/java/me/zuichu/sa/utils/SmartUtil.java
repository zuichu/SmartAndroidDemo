package me.zuichu.sa.utils;

import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by office on 2017/4/7.
 */
public class SmartUtil {

    public static int smartResourceId(Context context, String idName,
                                      String idType) {

        return context.getResources().getIdentifier(idName, idType,
                context.getPackageName());
    }

    public static void getMotionEvent(View v, int action, float x, float y) {
        MotionEvent event = MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis() + 5, action, x, y, 0);
        v.dispatchTouchEvent(event);
    }
}
