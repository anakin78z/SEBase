package com.sedigital.sebase.application;

import android.app.Application;

import com.sedigital.sebase.tasks.SETaskManager;

/**
 * Created by anakin78z on 12/2/13.
 */
public class SEApplication extends Application {

    private SETaskManager taskManager;

    public SETaskManager getTaskManager(){
        if (taskManager == null){
            taskManager = new SETaskManager();
        }
        return taskManager;
    }
}
