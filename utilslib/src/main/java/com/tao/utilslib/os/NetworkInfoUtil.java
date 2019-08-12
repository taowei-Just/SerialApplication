package com.tao.utilslib.os;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Administrator on 2017-11-01.
 */

public class NetworkInfoUtil {
    public static int senstNetType(String str) {
        switch (str) {
            case "WIFI":
                return 1;
        }
        return 0;
    }

    //获取本地IP
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("hh", ex.toString());
        }

        return "223.64.152.69";
    }


    public static String getNetworkMac(Context context) {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        String macAddress = wifiManager.getConnectionInfo().getMacAddress();


        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {

                    NetworkInterface networkInterface = networkInterfaces.nextElement();

                    if (networkInterface != null) {

                        byte[] hardwareAddress = networkInterface.getHardwareAddress();

                        StringBuffer buffer = new StringBuffer();
                        if (hardwareAddress != null)
                            for (int i = 0; i < hardwareAddress.length; i++) {
                                if (i != 0) {
                                    buffer.append('_');
                                }

                                String str = Integer.toHexString(hardwareAddress[i] & 0xFF);
                                buffer.append(str.length() == 1 ? 0 + str : str);
                            }
                        String strMacAddr = buffer.toString().toUpperCase();


                        if (!strMacAddr.isEmpty()) {

                            return strMacAddr;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, String> getTwoMac(Context context) {
        String macAddress1 = getMacAddress();
        HashMap<String, String> macMap = new HashMap<>();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String macAddress = wifiManager.getConnectionInfo().getMacAddress();
        // wifi
        macMap.put("wifi", macAddress);
        if (Build.MODEL.equals("G1803")) {
            macMap.put("ethernet", macAddress);
        } else {
            macMap.put("ethernet", macAddress1);
        }
        return macMap;
    }

    /**
     * 获取Ethernet的MAC地址
     *
     * @return
     */

    private static String getMacAddress() {
        try {
            return loadFileAsString("/sys/class/net/eth0/address").toUpperCase(Locale.ENGLISH).substring(0, 17);
        } catch (IOException e) {
            return null;
        }
    }

    private static String loadFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

}
