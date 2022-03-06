package com.example.greenflag;

import static com.example.greenflag.MainActivity2.emailKey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    private Button login;
    private Button back;
    private EditText emailField;
    private EditText passwordField;
    private SharedPreferences mShared;
    private String email;
    private String password;
    private ImageView buttonColor;
    private TextView passwordError;
    private TextView emailError;
    private boolean emailValid = false;
    private boolean passwordValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    @Override
    protected void onStart() {
        super.onStart();

        login = findViewById(R.id.nextButton);
        back = findViewById(R.id.backButton);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        buttonColor = findViewById(R.id.buttonColor);
        passwordError = findViewById(R.id.passwordError);
        emailError = findViewById(R.id.emailError);

        login.setEnabled(false);
        buttonColor.setColorFilter(getColor(R.color.grey));
        passwordError.setVisibility(View.GONE);
        emailError.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mShared = getApplicationContext().getSharedPreferences(MainActivity2.sharedPrefsFile, MODE_PRIVATE);
        String storedEmail = mShared.getString(emailKey, null);

        // here you can retrieve the email stored and put it into the email field
        if (storedEmail != null) {
            emailField.setText(storedEmail);
        }

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email = emailField.getText().toString().trim();

                // When we try to get data from shared preferences the default value should treated as null and check for no nullable value
                if (mShared.getString(email, null) == null) {
                    emailError.setVisibility(View.VISIBLE);
                    emailField.setBackground(getDrawable(R.drawable.red_white_border));
                    emailField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                    emailValid = false;
                }
                else {
                    emailError.setVisibility(View.GONE);
                    emailField.setBackground(getDrawable(R.drawable.green_white_border));
                    emailField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.tick2x,0);
                    emailValid = true;
                }
                if (passwordValid && emailValid) {
                    login.setEnabled(true);
                    buttonColor.setColorFilter(getColor(R.color.green));
                }
                else {
                    login.setEnabled(false);
                    buttonColor.setColorFilter(getColor(R.color.grey));
                }
            }
        });

        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = passwordField.getText().toString().trim();
                email = emailField.getText().toString().trim();

                if (MainActivity2.isValidPassword(password) && mShared.getString(email, null) == null) {
                    passwordField.setBackground(getDrawable(R.drawable.green_white_border));
                    passwordField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.tick2x,0);
                    passwordValid = true;
                    passwordError.setVisibility(View.GONE);
                }
                else {
                    passwordField.setBackground(getDrawable(R.drawable.red_white_border));
                    passwordField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                    passwordValid = false;
                    passwordError.setVisibility(View.VISIBLE);

                }

                if (passwordValid && emailValid) {
                    login.setEnabled(true);
                    buttonColor.setColorFilter(getColor(R.color.green));
                }
                else {
                    login.setEnabled(false);
                    buttonColor.setColorFilter(getColor(R.color.grey));
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),MainActivity4.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                Intent previous = new Intent(getBaseContext(), MainActivity.class);
//                startActivity(previous);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}