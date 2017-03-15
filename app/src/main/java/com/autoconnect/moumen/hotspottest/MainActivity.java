package com.autoconnect.moumen.hotspottest;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activateHotspot();

        connectToHotspot();
    }

    private void activateHotspot() {
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(false);

        Method[] wmMethods = wifiManager.getClass().getDeclaredMethods();   //Get all declared methods in WifiManager class
        boolean methodFound = false;
        for (Method method : wmMethods) {
            if (method.getName().equals("setWifiApEnabled")) {
                methodFound = true;
                WifiConfiguration netConfig = new WifiConfiguration();
                netConfig.SSID = "\"" + "MOUMENE" + "\"";
                netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);

                try {
                    boolean apstatus = (Boolean) method.invoke(wifiManager, netConfig, true);
                    for (Method isWifiApEnabledmethod : wmMethods) {
                        if (isWifiApEnabledmethod.getName().equals("isWifiApEnabled")) {
                            while (!(Boolean) isWifiApEnabledmethod.invoke(wifiManager)) {
                            };
                            for (Method method1 : wmMethods) {
                                if (method1.getName().equals("getWifiApState")) {
                                    int apstate;
                                    apstate = (Integer) method1.invoke(wifiManager);
                                }
                            }
                        }
                    }
                    if (apstatus) {
                        System.out.println("SUCCESSdddd");
                        Toast.makeText(this, "SUCCESSdddd", Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println("FAILED");
                        Toast.makeText(this, "FAILED", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                    Toast.makeText(this, "FAILED "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    private void connectToHotspot() {
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"\"" + "MOUMENE" + "\"\"";
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        WifiManager wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"\"" + "MOUMENE" + "\"\"")) {
                try {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    System.out.print("i.networkId " + i.networkId + "\n");
                    wifiManager.reconnect();
                    break;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
