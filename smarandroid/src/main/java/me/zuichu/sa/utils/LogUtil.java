package me.zuichu.sa.utils;

import android.util.Log;

/**
 * Created by office on 2017/4/7.
 */
public class LogUtil {
    public static boolean logOpen = true;
    public static String tag = "LogUtil";

    public static void logI(String tag, String log) {
        if (logOpen) {
            Log.i(tag, log);
        }
    }

    public static void logI(String log) {
        if (logOpen) {
            Log.i(tag, log);
        }
    }

    public static void logD(String tag, String log) {
        if (logOpen) {
            Log.d(tag, log);
        }
    }

    public static void logD(String log) {
        if (logOpen) {
            Log.d(tag, log);
        }
    }

    public static void logV(String tag, String log) {
        if (logOpen) {
            Log.v(tag, log);
        }
    }

    public static void logV(String log) {
        if (logOpen) {
            Log.v(tag, log);
        }
    }

    public static void logW(String tag, String log) {
        if (logOpen) {
            Log.w(tag, log);
        }
    }

    public static void logW(String log) {
        if (logOpen) {
            Log.w(tag, log);
        }

    }

    public static void logE(String tag, String log) {
        if (logOpen) {
            Log.e(tag, log);
        }

    }

    public static void logE(String log) {
        if (logOpen) {
            Log.e(tag, log);
        }

    }

    public static void logWTF(String tag, String log) {
        if (logOpen) {
            Log.wtf(tag, log);
        }

    }

    public static void logWTF(String log) {
        if (logOpen) {
            Log.wtf(tag, log);
        }
    }

    public static void logJsonI(String tag, String log) {
        if (logOpen) {
            Log.i(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonI(String log) {
        if (logOpen) {
            Log.i(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonE(String tag, String log) {
        if (logOpen) {
            Log.e(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonE(String log) {
        if (logOpen) {
            Log.e(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonD(String tag, String log) {
        if (logOpen) {
            Log.d(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonD(String log) {
        if (logOpen) {
            Log.d(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonW(String tag, String log) {
        if (logOpen) {
            Log.w(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonW(String log) {
        if (logOpen) {
            Log.w(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonV(String tag, String log) {
        if (logOpen) {
            Log.v(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonV(String log) {
        if (logOpen) {
            Log.v(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonWTF(String tag, String log) {
        if (logOpen) {
            Log.wtf(tag, FileUtil.formatJson(log));
        }
    }

    public static void logJsonWTF(String log) {
        if (logOpen) {
            Log.wtf(tag, FileUtil.formatJson(log));
        }
    }

    public static void logXmlI(String tag, String log) {
        if (logOpen) {
            Log.i(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlI(String log) {
        if (logOpen) {
            Log.i(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlE(String tag, String log) {
        if (logOpen) {
            Log.e(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlE(String log) {
        if (logOpen) {
            Log.e(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlD(String tag, String log) {
        if (logOpen) {
            Log.d(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlD(String log) {
        if (logOpen) {
            Log.d(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlV(String tag, String log) {
        if (logOpen) {
            Log.v(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlV(String log) {
        if (logOpen) {
            Log.v(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlW(String tag, String log) {
        if (logOpen) {
            Log.w(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlW(String log) {
        if (logOpen) {
            Log.w(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlWTF(String tag, String log) {
        if (logOpen) {
            Log.wtf(tag, FileUtil.formatXML(log));
        }
    }

    public static void logXmlWTF(String log) {
        if (logOpen) {
            Log.wtf(tag, FileUtil.formatXML(log));
        }
    }

    public static void logSave(String log, String path) {
        FileUtil.writeText(log, path);
    }
}
