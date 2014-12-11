package com.example.bfinerocks.backpack.interfaces;

import android.os.Handler;
import android.os.Message;

/**
 * Created by BFineRocks on 12/8/14.
 */
public class UIHandler extends Handler {
    public UIHandler(Callback callback) {
        super(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
    }
}
