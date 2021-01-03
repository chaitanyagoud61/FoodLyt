package com.chaitanya.quicksoft.glutton;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {

    private ConnectivityManager connectivityManager;
    private Context context;
    private boolean isconncted = false;
    private NetworkResponseInterface networkResponseInterface = null;

    public NetworkCheck(ConnectivityManager connectivityManager,
                        NetworkResponseInterface networkResponseInterface, Context context) {
        this.connectivityManager = connectivityManager;
        this.context = context;
        this.networkResponseInterface = networkResponseInterface;
    }


    public void CheckNetworkState(ConnectivityManager connectivityManager, int calling_request_from ) {

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            isconncted = true;
            if(networkResponseInterface!=null)
            networkResponseInterface.IsConnected(isconncted,calling_request_from);

        } else {

            isconncted = false;
            if(networkResponseInterface!=null)
            networkResponseInterface.IsConnected(isconncted,calling_request_from);

        }

    }

}
