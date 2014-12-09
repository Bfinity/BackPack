package com.example.bfinerocks.backpack.interfaces;

import android.app.Fragment;
import android.os.Handler.Callback;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by BFineRocks on 12/8/14.
 */
public class UIHandler implements Callback {

    public static final int LISTUPDATED = 1;

    private final WeakReference<Fragment> currentFragment;

    public UIHandler(Fragment fragment){
        currentFragment = new WeakReference<Fragment>(fragment);
    }

/*    public UIHandler(Looper looper){
        super();
    }*/


    @Override
    public boolean handleMessage(Message message) {
        Fragment fragment = currentFragment.get();
        if(fragment != null){
            return true;
        }
        return false;
    }

}
