package com.example.loginregistrationapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.loginregistrationapp.R;
import com.example.loginregistrationapp.dataSource.RegistrationDataSource;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView lnkRegister;
    private Context context;
    private EditText edtEmail;
    private EditText edtPassword;
    private String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        initViews();
        setClickListeners();
    }

    private void setClickListeners() {
        Log.e(TAG, "setClickListeners: ");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString();
                if (isEditTextValid(email, password)) {
                    RegistrationDataSource registrationDataSource = new RegistrationDataSource(context);
                    Boolean isValid = registrationDataSource.IsValidUser(email, password);
                    Log.e(TAG, "Kailas: " + isValid);
                    if (isValid) {
                        Intent intent = new Intent(context, ThirdActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setMessage("User is not available of this credentials.");
                        dialog.setTitle("Login Error");
                        dialog.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        clearControls();
                                    }
                                });
                        dialog.show();
                    }
                }
            }
        });

        lnkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clearControls() {
        Log.e(TAG, "clearControls: ");
        edtEmail.setText("");
        edtPassword.setText("");
    }

    private void initViews() {
        Log.e(TAG, "initViews: ");
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        lnkRegister = findViewById(R.id.lnkRegister);
    }

    private boolean isEditTextValid(String email, String password) {
        Log.e(TAG, "isEditTextValid: ");
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty() || !email.matches(emailPattern)) {
            edtEmail.setError("Please enter valid email");
            edtEmail.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            edtPassword.setError("Please enter password");
            edtPassword.requestFocus();
            return false;
        }
        return true;
    }
}
