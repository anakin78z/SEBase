package com.sedigital.sebase.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.sedigital.sebase.application.SEApplication;
import com.sedigital.sebase.tasks.SETaskManager;

public class SEActivity extends FragmentActivity {

    private SEApplication getSEApplication(){
        return (SEApplication) getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
    }

    public SETaskManager getTaskManager(){
        return getSEApplication().getTaskManager();
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
