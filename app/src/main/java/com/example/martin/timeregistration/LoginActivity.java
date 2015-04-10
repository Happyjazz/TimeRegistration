package com.example.martin.timeregistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.martin.timeregistration.Data.DataAccessObject;
import com.example.martin.timeregistration.Data.inMemory.DataAccessObjectInMemory;
import com.example.martin.timeregistration.Model.User;


public class LoginActivity extends ActionBarActivity {

    private EditText etUserName;
    private EditText etPassword;
    private CheckBox cbRemember;

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    private DataAccessObject dao;

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CHECKBOX = "pref_remember";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUserName = (EditText) findViewById(R.id.editTextUserName);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        cbRemember = (CheckBox) findViewById(R.id.checkBoxRememberLogin);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        dao = DataAccessObjectInMemory.getInstance();

        //Check if a shared preference with saved login-credentials exist
        //and login u if they do
        if (sharedPreferences.contains(KEY_USERNAME)) {
            String storedUsername = sharedPreferences.getString(KEY_USERNAME, null);
            String storedPassword = sharedPreferences.getString(KEY_PASSWORD, null);

            User currentUser = dao.getUser(storedUsername, storedPassword);

            if (currentUser != null) {
                Toast.makeText(this, "Welcome " + currentUser.getFirstname() + " " + currentUser.getLastname(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, TaskRegistrationActivity.class);
                intent.putExtra(KEY_USERNAME, storedUsername);
                intent.putExtra(KEY_PASSWORD, storedPassword);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                editor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                editor.remove(LoginActivity.KEY_USERNAME);
                editor.remove(LoginActivity.KEY_PASSWORD);
                editor.putBoolean(LoginActivity.KEY_CHECKBOX, false);
                editor.apply();
            }
        }
    }

    public void LoginClicked(View view) {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if (etUserName.length() == 0) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        } else if (etPassword.length() == 0) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
        } else {
            User currentUser = dao.getUser(userName, password);

            if (currentUser != null) {
                if (cbRemember.isChecked()) {
                    editor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                    editor.putString(KEY_USERNAME, userName);
                    editor.putString(KEY_PASSWORD, password);
                    editor.putBoolean(KEY_CHECKBOX, true);
                    editor.apply();
                }

                Toast.makeText(this, "Welcome " + currentUser.getFirstname() + " " + currentUser.getLastname(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, TaskRegistrationActivity.class);
                intent.putExtra(KEY_USERNAME, userName);
                intent.putExtra(KEY_PASSWORD, password);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
