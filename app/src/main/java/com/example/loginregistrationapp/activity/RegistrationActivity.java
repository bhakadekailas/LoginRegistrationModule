package com.example.loginregistrationapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginregistrationapp.R;
import com.example.loginregistrationapp.dataObject.RegistrationDataModel;
import com.example.loginregistrationapp.dataSource.RegistrationDataSource;

public class RegistrationActivity extends AppCompatActivity {
    private Context context;
    private EditText edtFullName;
    private EditText edtEmail;
    private EditText edtMobile;
    private EditText edtPassword;
    private Button btnRegistration;
    private String TAG = RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = RegistrationActivity.this;
        initViews();
        setClickListeners();
    }

    private void setClickListeners() {
        Log.e(TAG, "setClickListeners: ");
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                String mobile = edtMobile.getText().toString();
                String password = edtPassword.getText().toString();
                if (isRegistrationFormValid(fullName, email, mobile, password)) {
                    RegistrationDataModel registrationDataModel = new RegistrationDataModel();
                    registrationDataModel.setFullname(fullName);
                    registrationDataModel.setEmail(email);
                    registrationDataModel.setMobile(mobile);
                    registrationDataModel.setPassword(password);

                    RegistrationDataSource registrationDataSource = new RegistrationDataSource(context);
                    registrationDataSource.saveToDatabase(registrationDataModel);
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean isRegistrationFormValid(String fullName, String email, String mobile, String password) {
        Log.e(TAG, "isRegistrationFormValid: ");
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (fullName.isEmpty() || fullName.length() < 2) {
            edtFullName.setError("Please enter full name");
            edtFullName.requestFocus();
            return false;
        } else if (email.isEmpty() || !email.matches(emailPattern)) {
            edtEmail.setError("Please enter valid email");
            edtEmail.requestFocus();
            return false;
        } else if (mobile.isEmpty() || mobile.length() < 10) {
            edtMobile.setError("Please enter valid mobile");
            edtMobile.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            edtPassword.setError("Please enter password");
            edtPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void initViews() {
        Log.e(TAG, "initViews: ");
        btnRegistration = findViewById(R.id.btnRegistration);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtPassword = findViewById(R.id.edtPassword);
    }
}
