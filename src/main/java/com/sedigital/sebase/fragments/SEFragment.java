package com.sedigital.sebase.fragments;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.sedigital.sebase.activities.SEActivity;
import com.sedigital.sebase.tasks.SETaskManager;

/**
 * Created by anakin78z on 12/2/13.
 */
public class SEFragment extends Fragment{

    @Override
    public void onDestroy() {
        cancelTasks();
        super.onDestroy();
    }

    public void setProgressBarIndeterminateVisibility(boolean visible){
        try{
            getActivity().setProgressBarIndeterminateVisibility(visible);
        } catch (Exception e) {
            //pass
        }
    }

    private SEActivity getSEActivity(){
        return (SEActivity) getActivity();
    }

    public SETaskManager getTaskManager(){
        return getSEActivity().getTaskManager();
    }

    public <Params,Progress,Result> void executeTask(AsyncTask<Params,Progress,Result> task, Params... params) {
        SETaskManager taskManager = getTaskManager();
        taskManager.executeTask(this, task, params);
    }

    public <Params,Progress,Result> void executeParallelTask(AsyncTask<Params,Progress,Result> task, Params... params) {
        SETaskManager taskManager = getTaskManager();
        taskManager.executeParallelTask(this, task, params);
    }

    public void cancelTasks() {
        SETaskManager taskManager = getTaskManager();
        taskManager.cancelTasks(this);
    }
}
