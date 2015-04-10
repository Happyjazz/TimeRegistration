package com.example.martin.timeregistration.Data;

import java.util.Calendar;
import java.util.List;
import com.example.martin.timeregistration.Model.User;
import com.example.martin.timeregistration.Model.TaskRegistration;

/**
 * Created by Martin on 09-03-2015.
 */
public interface DataAccessObject {
    User getUser(String username, String password);

    boolean changePassword(User user, String newPassword);

    List<TaskRegistration> getRegistrations(User user, Calendar date);

    void saveRegistration(User user, Calendar date,List<TaskRegistration> registrations);
}