package com.example.bfinerocks.backpack.parse;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by BFineRocks on 12/10/14.
 */
public class ParseNetworkThreadPoolExecutor{

    public void singleNetWorkCall(Runnable runnable){
      Thread thread = new Thread(runnable);
        thread.run();

    }
    public void chainedNetworkCall(final NetworkCallInterface networkCallInterface, List<Runnable> listOfRunnables){
        for(int i = 0; i < listOfRunnables.size(); i++) {
            Runnable runnableToRun = listOfRunnables.get(i);
            Thread thread = new Thread(runnableToRun);
            thread.run();
            if(networkCallInterface != null){

            }
        }
    }

    public interface NetworkCallInterface{
        public void onSuccess(ParseObject object);
    }
}
