package com.example.martin.timeregistration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.example.martin.timeregistration.Data.DataAccessObject;
import com.example.martin.timeregistration.Data.inMemory.DataAccessObjectInMemory;

/**
 * Created by Martin on 23-03-2015.
 */
public class SettingsFragment extends PreferenceFragment {

    CheckBoxPreference checkBoxRemember;
    Preference changePassword;

    String newPassword;

    SharedPreferences sharedPreferences;

    DataAccessObject dao = DataAccessObjectInMemory.getInstance();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        checkBoxRemember = (CheckBoxPreference) findPreference("pref_remember");
        changePassword = (Preference) findPreference("pref_changePassword");

        setCheckboxRememberListener();
        setChangePasswordListener();
    }

    public void setChangePasswordListener(){
        changePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                //Build the dialog to change the password in
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Change password");

                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newPassword = input.getText().toString();
                        changePassword(newPassword);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    public void changePassword(String newPassword) {
        boolean success = dao.changePassword(TaskRegistrationActivity.getCurrentUser(), newPassword);
        if (success) Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }
    public void setCheckboxRememberListener()
    {
        checkBoxRemember.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {

                if (newValue.toString() == "false") {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Delete credentials")
                            .setMessage("Are you sure you want to delete your login credentials?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                                    editor.remove(LoginActivity.KEY_USERNAME);
                                    editor.remove(LoginActivity.KEY_PASSWORD);
                                    editor.putBoolean(LoginActivity.KEY_CHECKBOX, false);
                                    editor.apply();
                                    checkBoxRemember.setChecked(false);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    checkBoxRemember.setEnabled(true);
                                    checkBoxRemember.setChecked(true);

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else if (newValue.toString() == "true") {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    editor.putString(LoginActivity.KEY_USERNAME, TaskRegistrationActivity.getCurrentUser().getUsername());
                    editor.putString(LoginActivity.KEY_PASSWORD, TaskRegistrationActivity.getCurrentUser().getPassword());
                    editor.putBoolean(LoginActivity.KEY_CHECKBOX, true);
                    editor.apply();
                }
                return true;
            }
        });
    }
}
