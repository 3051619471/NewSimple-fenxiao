package cn.qfishphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkManager extends BroadcastReceiver {
    static final public String TAG = "*ASTGO*";

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo lNetworkInfo = (NetworkInfo) intent
                .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        Log.i(TAG, "NetworkManager:  Network info [" + lNetworkInfo + "]");
        Boolean lNoConnectivity = intent.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
//		if (!PhoneService.isready()) {
//			Log.i(TAG, "NetworkManager: Phone service not ready");
//			return;
//		}

        if (lNoConnectivity
                | ((lNetworkInfo.getState() == NetworkInfo.State.DISCONNECTED) /*
                                                                                 * &&
																				 * !
																				 * lIsFailOver
																				 */)) {
        } else if (lNetworkInfo.getState() == NetworkInfo.State.CONNECTED
                && lNetworkInfo.getType() == 1// WIFI
                ) {
            Log.i(TAG,
                    String.format(
                            "NetworkManager: Network Type: %s, pref_use_wifi_key = true, enable register !",
                            lNetworkInfo.getTypeName()));

        } else if (lNetworkInfo.getState() == NetworkInfo.State.CONNECTED
                && lNetworkInfo.getType() == 0// MOBILE
                ) {
            Log.i(TAG,
                    String.format(
                            "NetworkManager: Network Type: %s, pref_use_wcdma_key = true,  enable register !",
                            lNetworkInfo.getTypeName()));
        } else {
            Log.i(TAG,
                    String.format(
                            "NetworkManager: Network Type: %s, pref_use_wcdma_key = false && pref_use_wifi_key = false,   disable register !",
                            lNetworkInfo.getTypeName()));
        }

    }

}