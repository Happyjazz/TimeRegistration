package com.example.martin.timeregistration.Data.inMemory;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.martin.timeregistration.Model.TaskRegistration;
import com.example.martin.timeregistration.Model.User;
import com.example.martin.timeregistration.Data.DataAccessObject;
import com.example.martin.timeregistration.Model.UserDate;

/**
 * Created by Martin on 09-03-2015.
 */
public class DataAccessObjectInMemory implements DataAccessObject {
    private final String[] tasks = new String[] { "Task1", "Task2", "Task3","Task4", "Task5", "Task6", "Task7", "Task8", "Task9" };
    private List<User> users = new ArrayList<>();
    private final Map<UserDate, List<TaskRegistration>> registration = new HashMap<UserDate, List<TaskRegistration>>();

    private static final DataAccessObject INSTANCE = new DataAccessObjectInMemory();

    public static DataAccessObject getInstance() { return INSTANCE; }

    public DataAccessObjectInMemory() {
        final User user1 = new User(1, "martin", "secret", "Martin","Kiersgaard");
        users.add(user1);
        final User user2 = new User(2, "john", "secret", "John", "Johnson");
        users.add(user2);

        final Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        final UserDate userdate = new UserDate(user1, yesterday);
        final List<TaskRegistration> taskList = new ArrayList<TaskRegistration>();
        for (String task : tasks) {
            taskList.add(new TaskRegistration(task, 2.2));
        }
        registration.put(userdate, taskList);
        Log.d("MINE", registration.toString());
    }

    @Override
    public User getUser(final String username, final String password) {
        for (final User user : users) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<TaskRegistration> getRegistrations(final User user,final Calendar date) {
        final UserDate userdate = new UserDate(user, date);
        final List<TaskRegistration> taskRegistrations = registration.get(userdate);
        Log.d("MINE", "getRegistrations(" + user + ", " + date + ": " + taskRegistrations);

        if (taskRegistrations == null) {
            return createEmptyTaskRegistration();
        }

        Log.d("MINE", "getRegistrations(" + user + ", " + date + ": " + taskRegistrations);
        return taskRegistrations;
    }

    private List<TaskRegistration> createEmptyTaskRegistration() {
        List<TaskRegistration> registration = new ArrayList<TaskRegistration>();
        for (String taskname : tasks) {
            registration.add(new TaskRegistration(taskname, 0.0));
        }
        return registration;
    }

    @Override
    public void saveRegistration(User user, Calendar date,List<TaskRegistration> registrations) {
        final UserDate userdate = new UserDate(user, date);
        registration.put(userdate, registrations);
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        User currentUser = getUser(user.getUsername(), user.getPassword());
        users.remove(currentUser);
        boolean succes = currentUser.setPassword(newPassword);
        users.add(currentUser);
        return succes;
    }
}