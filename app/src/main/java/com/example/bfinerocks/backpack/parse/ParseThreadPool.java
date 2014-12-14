package com.example.bfinerocks.backpack.parse;

import java.util.concurrent.Executor;

/**
 * Created by BFineRocks on 12/9/14.
 */
public class ParseThreadPool implements Executor {
    @Override
    public void execute(Runnable runnable) {
        new Thread(runnable).start();
    }


}
