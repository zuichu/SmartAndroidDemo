package me.zuichu.sa.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by office on 2017/4/7.
 */
public class AppUtil {

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * Get The App VersionCode
     *
     * @param context
     * @return the App VersionCode
     */

    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return verCode;
    }

    /**
     * Get The App VersionName
     *
     * @param context
     * @return the App VersionName
     */

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return verName;

    }

    /**
     * Get The AppName
     *
     * @param context
     * @return the AppName
     */

    public static String getAppName(Context context) {
        String verName = context
                .getResources()
                .getText(
                        context.getResources().getIdentifier("app_name",
                                "string", context.getPackageName())).toString();
        return verName;
    }

    /**
     * Get The AppIcon
     *
     * @param context
     * @return the AppIcon
     */

    public static Drawable getAppIcon(Context context) {
        Drawable icon = null;
        try {
            icon = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).applicationInfo
                    .loadIcon(context.getPackageManager());
        } catch (PackageManager.NameNotFoundException e) {

        }
        return icon;
    }

    /**
     * Get The App FirstInstallTime(requires API level 9)
     *
     * @param context
     * @return the App FirstInstallTime
     */

    public static long getFirstInstallTime(Context context) {
        long fit = 0;
        try {
            fit = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return fit;

    }

    /**
     * Get The App LastUpdateTime(requires API level 9)
     *
     * @param context
     * @return the App LastUpdateTime
     */

    public static long getLastUpdateTime(Context context) {
        long fit = 0;
        try {
            fit = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return fit;

    }

    /**
     * Get the App's All use Permission
     *
     * @param packageName
     * @return the use Permissions by ArrayList<MyPermission> permissions
     */

    public static ArrayList<MyPermission> getUsesPermission(Context context, String packageName) {
        ArrayList<MyPermission> myPerms = new ArrayList<MyPermission>();
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, PackageManager.GET_PERMISSIONS);
            String[] usesPermissionsArray = packageInfo.requestedPermissions;

            for (int i = 0; i < usesPermissionsArray.length; i++) {
                MyPermission permi = new MyPermission();

                // 得到每个权限的名字,如:android.permission.INTERNET
                String usesPermissionName = usesPermissionsArray[i];
                permi.setPermissionName(usesPermissionName);
                System.out.println("usesPermissionName=" + usesPermissionName);
                // 通过usesPermissionName获取该权限的详细信息
                PermissionInfo permissionInfo = packageManager
                        .getPermissionInfo(usesPermissionName, 0);
                // 获得该权限属于哪个权限组,如:网络通信
                PermissionGroupInfo permissionGroupInfo = packageManager
                        .getPermissionGroupInfo(permissionInfo.group, 0);
                permi.setPermissionGroup(permissionGroupInfo.loadLabel(
                        packageManager).toString());
                System.out.println("permissionGroup="
                        + permissionGroupInfo.loadLabel(packageManager)
                        .toString());
                // 获取该权限的标签信息,比如:完全的网络访问权限
                String permissionLabel = permissionInfo.loadLabel(
                        packageManager).toString();
                permi.setPermissionLabel(permissionLabel);
                System.out.println("permissionLabel=" + permissionLabel);
                // 获取该权限的详细描述信息,比如:允许该应用创建网络套接字和使用自定义网络协议
                // 浏览器和其他某些应用提供了向互联网发送数据的途径,因此应用无需该权限即可向互联网发送数据.
                String permissionDescription = permissionInfo.loadDescription(
                        packageManager).toString();
                permi.setPermissionDescription(permissionDescription);
                System.out.println("permissionDescription="
                        + permissionDescription);
                System.out
                        .println("===========================================");
                myPerms.add(permi);

            }
            return myPerms;
        } catch (Exception e) {
        }
        return myPerms;
    }

    /**
     * Get the Device's ALL Third Apps
     *
     * @return the Device's ALL Third Apps
     */

    public static String getAllThirdApp(Context context) {
        String result = "";
        List<PackageInfo> packages = context.getPackageManager()
                .getInstalledPackages(0);
        for (PackageInfo i : packages) {
            if ((i.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                result += i.applicationInfo.loadLabel(context.getPackageManager())
                        .toString() + ",";
            }
        }
        return result.substring(0, result.length() - 1);
    }

    public static String getSystemVersionCode() {

        return android.os.Build.VERSION.SDK + "";
    }

    /**
     * Get the SystemVersionName
     *
     * @return the SystemVersionName
     */

    public static String getSystemVersionName() {

        return android.os.Build.VERSION.RELEASE + "";
    }

    /**
     * Get the DeviceName
     *
     * @return the DeviceName
     */

    public static String getDeviceName() {

        return android.os.Build.MODEL + "";
    }

    /**
     * Get the Device's width
     *
     * @return the device's width
     */

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * Get the Device's height
     *
     * @return the device's height
     */

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * Get the Device's Orientation
     *
     * @return the device's Orientation(0 means portrait,1 means landscape)
     */

    public static int getDeviceOrientation(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getOrientation();
    }

    /**
     * Get the Device's Density(0.75/1.0/1.5/2.0)
     *
     * @return the Device's Density(0.75/1.0/1.5/2.0)
     */

    public static float getDeviceDensity(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        metric = context.getResources().getDisplayMetrics();
        float density = metric.density;

        return density;
    }

    /**
     * Get the Device's DensityDpi per inch
     *
     * @return the Device's DensityDpi per inch
     */

    public static float getDeviceDensityDpi(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        metric = context.getResources().getDisplayMetrics();

        float densityDpi = metric.densityDpi;
        return densityDpi;
    }

    /**
     * Get the Device's Language
     *
     * @return the Device's Language
     */

    public static String getDeviceLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    /**
     * Get the Device's TotalMemory
     *
     * @return the Device's TotalMemory(M)
     */

    public static String getTotalMemory() {
        long mTotal;
        // /proc/meminfo Kernel messages read out to explain
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // beginIndex
        int begin = content.indexOf(':');
        // endIndex
        int end = content.indexOf('k');
        content = content.substring(begin + 1, end).trim();
        mTotal = Integer.parseInt(content);

        return mTotal / 1024 + "";

    }

    /**
     * Get the Device's Available Memory
     *
     * @return the Device's Available Memory
     */

    public static String getAvailableMemory(Context context) {
        long MEM_UNUSED;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        MEM_UNUSED = (mi.availMem / (1024 * 1024));
        return MEM_UNUSED + "";
    }

    /**
     * If the Device has Root,this Method will apply Root Authorize
     */

    public static void ApplyRootAuthorize() {
        try {
            if (Runtime.getRuntime().exec("su").getOutputStream() == null) {

            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If this Device has Root
     *
     * @return If this Device has Root
     */
    public static boolean isRoot() {
        boolean bool = false;
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {

        }
        return bool;
    }

    /**
     * Get the Device's CPU Model
     *
     * @return the Device's CPU Model
     */

    public static String getCpuModel() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpuInfo[0];
    }

    /**
     * Get the Device's CPU Serial Number
     *
     * @return the Device's CPU Serial Number
     */

    @Deprecated
    public static String getCpuSer() {
        String cpuSerial = "";
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null && str.startsWith("Serial")) {
                    cpuSerial = str.substring(str.indexOf(":") + 1, str.length()).trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {

        }
        return cpuSerial;
    }

    /**
     * Get the Device's CPU Cores Num
     *
     * @return the Device's CPU Cores Num
     */

    public static int getCpuCoresNum() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            Log.d("info", "CPU Count: " + files.length);
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            // Print exception
            Log.d("info", "CPU Count: Failed.");
            e.printStackTrace();
            // Default to return 1 core
            return 1;
        }
    }

    /**
     * Get the Device's CPU'S MaxFrequence
     *
     * @return the Device's CPU'S MaxFrequence
     */

    public static String getCpuMaxFrequence() {
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            return (Long.parseLong(line) / 1000) + "MHZ";

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "0";
    }

    /**
     * Get the Device's CPU'S MinFrequence
     *
     * @return the Device's CPU'S MinFrequence
     */
    public static String getCpuMinFrequence() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return (Long.parseLong(result.trim()) / 1000) + "MHZ";
    }

    /**
     * Get the Device's SDCardTotalStorage
     *
     * @return the Device's SDCardTotalStorage
     */
    public static String getSDCardTotalStorage() {
        long[] sdCardInfo = new long[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[0] = bSize * bCount;

        }
        return sdCardInfo[0] / (1024 * 1024) + "MB";
    }

    /**
     * Get the Device's SDCardAvailableStorage
     *
     * @return the Device's SDCardAvailableStorage
     */
    public static String getSDCardAvailableStorage() {
        long[] sdCardInfo = new long[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[1] = bSize * availBlocks;
        }
        return sdCardInfo[1] / (1024 * 1024) + "MB";
    }

    /**
     * Get the Device's MacAddress,need android.permission.ACCESS_WIFI_STATE
     *
     * @return the Device's MacAddress
     */

    public static String getMacAddress(Context context) {
        String[] other = {"null", "null"};
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getMacAddress() != null) {
            other[0] = wifiInfo.getMacAddress();
        } else {
            other[0] = "Fail";
        }
        return other[0];
    }

    /**
     * Get the Device's Boot Time
     *
     * @return the Device's Boot Time
     */

    public static String getBootTime() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        if (ut == 0) {
            ut = 1;
        }
        int m = (int) ((ut / 60) % 60);
        int h = (int) ((ut / 3600));
        return h + " Hours " + m + " Minutes";
    }

    /**
     * Get the pid's CpuUsage
     *
     * @param pid
     * @return the pid's CpuUsage
     */

    @Deprecated
    public static int getCpuUsage(int pid) {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" ");

            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {
            }

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" ");

            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            float cpu = (float) (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));
            return Math.round(cpu * 100);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Get the device's current freq
     *
     * @return the device's current freq
     */

    public static String getCurrCpuFreq() {
        int result = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = Integer.parseInt(text.trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return result / 1000 + "MHZ";
    }

    /**
     * Get the device's BatteryLevel
     *
     * @param callback the device's BatteryLevel
     */

    public static void getBatteryLevel(Context context, final GlobalCallback callback) {
        final String[] batteryLevel = {"0", "0"};
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int rawlevel = intent.getIntExtra("level", -1);// getCurrentBattery
                int scale = intent.getIntExtra("scale", -1);
                // getAllBattery
                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                batteryLevel[0] = level + "%";
                Log.i("info", batteryLevel[0]);
                callback.data_result(batteryLevel[0]);

            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(batteryLevelReceiver, batteryLevelFilter);

    }

    /**
     * Get the device's imei
     *
     * @return the device's imei
     */

    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return tm.getDeviceId();
    }

    public static String getNetWorkType(Context context) {
        String type = "";
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();// wifi
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting(); // Mobile
        if (wifi) {
            return "wifi";
        } else if (internet) {
            return "Mobile";
        }
        return "wrong";
    }

    /**
     * Determine Device'GPS is open，need
     * permission：android:name="android.permission.ACCESS_FINE_LOCATION"；
     *
     * @return if open return true，else return false
     */
    public static boolean isOpenGPS(Context context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            return true;
        }
        // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        // context.startActivity(intent);
        return false;
        // startActivityForResult(intent, 0);
    }

    /**
     * Determine WIFI is open
     *
     * @return true means wifi is open and connect to internet，false means wifi
     * is not work
     */
    public static boolean checkWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            int wifiState = wifiManager.getWifiState();
            if (wifiState == 3) {
                return true;
            }
            // wifiManager.WIFI_STATE_DISABLED (1)
            //
            // wifiManager.WIFI_STATE_ENABLED (3)
            //
            // wifiManager.WIFI_STATE_DISABLING (0)
            //
            // wifiManager.WIFI_STATE_ENABLING (2)
        }
        return false;
    }

    /**
     * 获取WifiInfo对象，进而获取wifi相关信息
     *
     * @param wifiOpen WIFI是否正常打开联网
     * @return WifiInfo对象，通过调用WifiInfo对象里的相关方法获取相应信息</br> wifiInfo.getBSSID();//
     * 获取BSSID地址</br> wifiInfo.getSSID();// 获取SSID地址, 需要连接网络的ID</br>
     * wifiInfo.getIpAddress();// 获取IP地址,4字节Int,XXX.XXX.XXX.XXX
     * 每个XXX为一个字节</br> wifiInfo.getMacAddress();//获取MAC地址 </br>
     * wifiInfo.getNetworkId();//获取网络ID</br>
     * wifiInfo.getLinkSpeed();//获取连接速度，可以让用户获知这一信息</br>
     * wifiInfo.getRssi();//获取RSSI，RSSI就是接受信号强度指示</br>
     */

    public static WifiInfo getWifiInfo(Context context, boolean wifiOpen) {
        if (wifiOpen) {
            WifiManager wifi_service = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifi_service.getConnectionInfo();
            StringBuilder sb = new StringBuilder();
            sb.append(wifiInfo.getBSSID());// 获取BSSID地址
            sb.append(wifiInfo.getSSID());// 获取SSID地址, 需要连接网络的ID
            sb.append(wifiInfo.getIpAddress());// 获取IP地址,4字节Int, XXX.XXX.XXX.XXX
            // 每个XXX为一个字节
            sb.append(wifiInfo.getMacAddress());// 获取MAC地址
            sb.append(wifiInfo.getNetworkId());// 获取网络ID
            sb.append(wifiInfo.getLinkSpeed());// 获取连接速度，可以让用户获知这一信息
            sb.append(wifiInfo.getRssi());// 获取RSSI，RSSI就是接受信号强度指示
            // Log.i("info", sb+"");
            return wifiInfo;
        }

        return null;
    }

    /**
     * getWifiIpAddress
     *
     * @return the wifi's ipAddress
     */

    public static int getWifiIpAddress(Context context) {
        WifiManager wifi_service = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi_service.getConnectionInfo();
        return wifiInfo.getIpAddress();
    }

    /**
     * getWifiMacAddress
     *
     * @return the wifi's MacAddress
     */

    public static String getWifiMacAddress(Context context) {
        WifiManager wifi_service = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi_service.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    /**
     * getWifiLinkSpeed
     *
     * @return the wifi's LinkSpeed
     */

    public static int getWifiLinkSpeed(Context context) {
        WifiManager wifi_service = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi_service.getConnectionInfo();
        return wifiInfo.getLinkSpeed();
    }

    /**
     * getWifiRssi (Signal strength:0 to -100)
     *
     * @return the wifi's Rssi(Signal strength:0 to -100)
     */

    public static int getWifiRssi(Context context) {
        WifiManager wifi_service = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi_service.getConnectionInfo();
        return wifiInfo.getRssi();
    }

    /**
     * open wifi，need
     * permission：android:name="android.permission.CHANGE_WIFI_STATE"
     */
    public static void openWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }

    /**
     * close wifi，need
     * permission：android:name="android.permission.CHANGE_WIFI_STATE"
     */
    public static void closeWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);

    }

    /**
     * enter WIFI setting
     */
    public static void openWifiSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        // startActivityForResult(intent, 0);
        context.startActivity(intent);
    }

    /**
     * Determin if the device is Flight mode
     *
     * @param context
     * @return true or false
     */
    public static boolean getAirplaneMode(Context context) {
        int isAirplaneMode = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
        return (isAirplaneMode == 1) ? true : false;
    }

    /**
     * set the device to be flight mode or cancel flight mode
     *
     * @param context
     * @param enabling (true means set to be flight mode，false means cancel flight
     *                 mode)
     */
    public static void setAirplaneModeOn(Context context, boolean enabling) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enabling ? 1 : 0);
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", enabling);
        context.sendBroadcast(intent);
    }

    /**
     * 检测手机上的存在的所有传感器
     *
     * @return 返回手机上的所有传感器相关信息
     */
    public static String getSensor(Context context) {
        StringBuilder sb = new StringBuilder();
        // 从系统服务中获得传感器管理器
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // 从传感器管理器中获得全部的传感器列表
        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);
        // 显示有多少个传感器
        sb.append("经检测该手机有" + allSensors.size() + "个传感器，他们分别是：\n");
        for (Sensor s : allSensors) {

            String tempString = "\n" + " 设备名称：" + s.getName() + "\n" + " 设备版本：" + s.getVersion() + "\n" + " 供应商："
                    + s.getVendor() + "\n";

            switch (s.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    sb.append("传感器类型ID：" + s.getType() + " 加速度传感器accelerometer" + tempString);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    sb.append("传感器类型ID：" + s.getType() + " 陀螺仪传感器gyroscope" + tempString);
                    break;
                case Sensor.TYPE_LIGHT:
                    sb.append("传感器类型ID：" + s.getType() + " 环境光线传感器light" + tempString);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    sb.append("传感器类型ID：" + s.getType() + " 电磁场传感器magnetic field" + tempString);
                    break;
                case Sensor.TYPE_ORIENTATION:
                    sb.append("传感器类型ID：" + s.getType() + " 方向传感器orientation" + tempString);
                    break;
                case Sensor.TYPE_PRESSURE:
                    sb.append("传感器类型ID：" + s.getType() + " 压力传感器pressure" + tempString);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    sb.append("传感器类型ID：" + s.getType() + " 距离传感器proximity" + tempString);
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    sb.append("传感器类型ID：" + s.getType() + " 温度传感器temperature" + tempString);
                    break;
                default:
                    sb.append("传感器类型ID：" + s.getType() + " 未知传感器" + tempString);
                    break;
            }
        }
        return sb.toString();
    }

    static class MyPermission {
        private String permissionName;
        private String permissionGroup;
        private String permissionLabel;
        private String permissionDescription;

        public String getPermissionName() {
            return permissionName;
        }

        public void setPermissionName(String permissionName) {
            this.permissionName = permissionName;
        }

        public String getPermissionGroup() {
            return permissionGroup;
        }

        public void setPermissionGroup(String permissionGroup) {
            this.permissionGroup = permissionGroup;
        }

        public String getPermissionLabel() {
            return permissionLabel;
        }

        public void setPermissionLabel(String permissionLabel) {
            this.permissionLabel = permissionLabel;
        }

        public String getPermissionDescription() {
            return permissionDescription;
        }

        public void setPermissionDescription(String permissionDescription) {
            this.permissionDescription = permissionDescription;
        }
    }

    public interface GlobalCallback {

        /**
         * Get the return data
         *
         * @param str the return data
         */
        void data_result(String str);

    }
}
