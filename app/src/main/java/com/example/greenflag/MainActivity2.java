package com.example.greenflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Pattern;


public class MainActivity2 extends AppCompatActivity {

    private Button advance;
    private Button back;
    private TextView emailError;
    private TextView passwordMismatch;
    private EditText emailField;
    private EditText createPassword;
    private EditText repeatPassword;
    private ImageView buttonColor;
    private boolean emailValid = false;
    private boolean passwordValid = false;
    private boolean confirmPassword = false;
    private SharedPreferences mShared;


    public static boolean isValidEmailId(String email){
        return Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$").matcher(email).matches();
    }

    public static boolean isValidPassword(String password){
        int nUpper = 0;
        int nLower = 0;
        int nNumber = 0;
        boolean length = false;
        for (int letter = 0; letter < password.length(); letter++) {
            if (password.charAt(letter) >= 'A' && password.charAt(letter) <= 'Z') {
                nUpper++;
            }
            if (password.charAt(letter) >= 'a' && password.charAt(letter) <= 'z') {
                nLower++;
            }
            if (password.charAt(letter) >= '0' && password.charAt(letter) <= '9') {
                nNumber++;
            }
        }
        if (password.length() < 8) {
            length = false;
        }
        else {
            length = true;
        }
        if (nUpper >= 1 && nLower >= 1 && nNumber >= 1 && length) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        emailError = findViewById(R.id.emailError);
        passwordMismatch = findViewById(R.id.mismatchPasswordError);
        emailField = findViewById(R.id.emailField);
        createPassword = findViewById(R.id.passwordField);
        repeatPassword = findViewById(R.id.repeatPasswordField);
        advance = findViewById(R.id.nextButton);
        back = findViewById(R.id.backButton);
        buttonColor = findViewById(R.id.buttonColor);

        emailError.setVisibility(View.GONE);
        passwordMismatch.setVisibility(View.GONE);
        advance.setEnabled(false);
        buttonColor.setColorFilter(getColor(R.color.grey));


    }

    @Override
    protected void onResume() {
        super.onResume();

        mShared = getApplicationContext().getSharedPreferences("SHARED_PREF_GREENFLAG", MODE_PRIVATE);
        SharedPreferences.Editor editor = mShared.edit();

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = emailField.getText().toString().trim();
                if (isValidEmailId(email)) {
                    emailField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.tick2x,0);
                    emailField.setBackground(getDrawable(R.drawable.green_white_border));
                    String registeredEmail = mShared.getString(emailField.getText().toString().trim(), "default");
                    if (!registeredEmail.equals("default")) {
                        emailField.setBackground(getDrawable(R.drawable.red_white_border));
                        emailField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                        emailError.setText(R.string.repeat_email);
                        emailError.setVisibility(View.VISIBLE);
                        emailValid = false;
                    }
                    else {
                        emailField.setBackground(getDrawable(R.drawable.green_white_border));
                        emailField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.tick2x,0);
                        emailError.setVisibility(View.GONE);
                        emailValid = true;
                    }

                }
                else {
                    emailField.setBackground(getDrawable(R.drawable.red_white_border));
                    emailField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                    emailValid = false;
                    emailError.setText(R.string.invalid_email_msg);
                    emailError.setVisibility(View.VISIBLE);
                }
                if (emailValid && passwordValid && confirmPassword) {
                    advance.setEnabled(true);
                    buttonColor.setColorFilter(getColor(R.color.green));
                }
                else {
                    advance.setEnabled(false);
                    buttonColor.setColorFilter(getColor(R.color.grey));
                }
            }
        });

        createPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = createPassword.getText().toString().trim();
                String passwordConfirmation = repeatPassword.getText().toString().trim();
                if (isValidPassword(password)){
                    createPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.tick2x,0);
                    createPassword.setBackground(getDrawable(R.drawable.green_white_border));
                    passwordMismatch.setVisibility(View.GONE);
                    passwordValid = true;
                    if (passwordConfirmation.equals(password)) {
                        repeatPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.tick2x,0);
                        repeatPassword.setBackground(getDrawable(R.drawable.green_white_border));
                        passwordMismatch.setVisibility(View.GONE);
                        confirmPassword = true;
                    }
                    else {
                        repeatPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                        repeatPassword.setBackground(getDrawable(R.drawable.red_white_border));
                        passwordMismatch.setText(R.string.password_mismatch);
                        passwordMismatch.setVisibility(View.VISIBLE);
                        confirmPassword = false;
                    }
                }
                else {
                    createPassword.setBackground(getDrawable(R.drawable.red_white_border));
                    createPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                    passwordMismatch.setVisibility(View.VISIBLE);
                    repeatPassword.setText("");
                    passwordMismatch.setText(R.string.invalid_password);
                    repeatPassword.setBackground(getDrawable(R.color.white));
                    repeatPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                    passwordValid = false;
                }

                if (emailValid && passwordValid && confirmPassword) {
                    advance.setEnabled(true);
                    buttonColor.setColorFilter(getColor(R.color.green));
                }
                else {
                    advance.setEnabled(false);
                    buttonColor.setColorFilter(getColor(R.color.grey));
                }
            }
        });

        repeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = emailField.getText().toString().trim();
                String password = createPassword.getText().toString().trim();
                String passwordConfirmation = repeatPassword.getText().toString().trim();
                if (passwordConfirmation.equals(password)) {
                    repeatPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.tick2x,0);
                    repeatPassword.setBackground(getDrawable(R.drawable.green_white_border));
                    passwordMismatch.setVisibility(View.GONE);
                    confirmPassword = true;
                }
                else {
                    repeatPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                    repeatPassword.setBackground(getDrawable(R.drawable.red_white_border));
                    passwordMismatch.setText(R.string.password_mismatch);
                    passwordMismatch.setVisibility(View.VISIBLE);
                    confirmPassword = false;

                }
                if (emailValid && passwordValid && confirmPassword) {
                    advance.setEnabled(true);
                    buttonColor.setColorFilter(getColor(R.color.green));
                }
                else {
                    advance.setEnabled(false);
                    buttonColor.setColorFilter(getColor(R.color.grey));
                }
            }
        });

        advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(emailField.getText().toString().trim(), createPassword.getText().toString().trim());
                editor.apply();

                Intent next = new Intent(getBaseContext(), MainActivity3.class);
                startActivity(next);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent previous = new Intent(getBaseContext(), MainActivity.class);
                startActivity(previous);
            }
        });

    }


}