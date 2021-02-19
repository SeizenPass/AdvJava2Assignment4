package com.example.javaapp.models;

import androidx.annotation.NonNull;

public class ToDo {
    private String taskName;
    private String taskDescription;
    private boolean taskCrossed;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isTaskCrossed() {
        return taskCrossed;
    }

    public void setTaskCrossed(boolean taskCrossed) {
        this.taskCrossed = taskCrossed;
    }

    public String toParsable() {
        return taskName + "," + taskDescription + "," + taskCrossed;
    }

    @NonNull
    @Override
    public String toString() {
        return taskName + ", " + taskDescription + " - Finished: " + taskCrossed;
    }
}
