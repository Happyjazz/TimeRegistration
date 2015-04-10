package com.example.martin.timeregistration.Model;

/**
 * Created by Martin on 09-03-2015.
 */
public class TaskRegistration {

    private final String taskname;
    private double hours;

    public TaskRegistration(final String taskname, final double hours) {
        this.taskname = taskname;
        this.hours = hours;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(final double hours) {
        this.hours = hours;
    }

    public String getTaskname() {
        return taskname;
    }

    @Override
    public String toString() {
        return taskname + ": " + hours;
    }
}
