//
//  Based on LolayTaskManager,
//  Copyright 2011, 2012, 2013 Lolay, Inc.
//
//  Modified 2013 by Shaking Earth Digital
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
package com.sedigital.sebase.tasks;

import android.os.AsyncTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SETaskManager {

    private Map<Object,Set<AsyncTask<?,?,?>>> taskMap = null;
    private Lock tasksLock = new ReentrantLock();


    /**
     * Executes {@link android.os.AsyncTask}
     *
     * @param context An object to tie the task to. Tasks can be cancelled using {@link #cancelTasks(java.lang.Object) cancelTasks}
     * @param task An AsyncTask
     * @param params parameters for execution
     *
     */
    public <Params,Progress,Result> void executeTask(Object context, AsyncTask <Params,Progress,Result> task, Params... params){
        tasksLock.lock();
        try {
            if (taskMap == null) {
                taskMap = new HashMap<Object,Set<AsyncTask<?,?,?>>>();
            }

            Set<AsyncTask<?,?,?>> tasks = taskMap.get(context);
            if (tasks == null) {
                tasks = new HashSet<AsyncTask<?,?,?>>();
                taskMap.put(context, tasks);
            }
            tasks.add(task);
            task.execute(params);
        } finally {
            tasksLock.unlock();
        }
    }

    /**
     * Executes {@link android.os.AsyncTask} on AsyncTask.THREAD_POOL_EXECUTOR} to run tasks in parallel.
     * Normally AsyncTask performs tasks in serial.
     *
     * @param context An object to tie the task to. Tasks can be cancelled using {@link #cancelTasks(java.lang.Object) cancelTasks}
     * @param task An AsyncTask
     * @param params parameters for execution
     *
     */
    public <Params,Progress,Result> void executeParallelTask(Object context, AsyncTask <Params,Progress,Result> task, Params... params){
        tasksLock.lock();
        try {
            if (taskMap == null) {
                taskMap = new HashMap<Object,Set<AsyncTask<?,?,?>>>();
            }

            Set<AsyncTask<?,?,?>> tasks = taskMap.get(context);
            if (tasks == null) {
                tasks = new HashSet<AsyncTask<?,?,?>>();
                taskMap.put(context, tasks);
            }
            tasks.add(task);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } finally {
            tasksLock.unlock();
        }
    }

    public void cancelTasks(Object context) {
        tasksLock.lock();
        try {
            if (taskMap != null) {
                Set<AsyncTask<?,?,?>> tasks = taskMap.get(context);
                if (tasks != null) {
                    for (AsyncTask<?,?,?> task : tasks) {
                        task.cancel(true);
                    }
                }
                taskMap.remove(context);
            }
        } finally {
            tasksLock.unlock();
        }
    }

    public void cancelAllTasks() {
        tasksLock.lock();
        try {
            if (taskMap != null) {
                for (Object context : new HashSet<Object>(taskMap.keySet())) {
                    cancelTasks(context);
                }
            }
        } finally {
            tasksLock.unlock();
        }
    }

}
