package com.example.bfinerocks.backpack.parse;

import android.os.AsyncTask;

/**
 * Created by BFineRocks on 12/9/14.
 */
public class ParseAsyncTask extends AsyncTask<Runnable, Void, Void> {

    @Override
    protected Void doInBackground(Runnable... runnables) {
        runnables[0].run();
        return null;
    }
}
