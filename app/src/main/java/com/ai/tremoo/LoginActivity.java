package com.ai.tremoo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ai.tremoo.Models.Login_Response;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email, password;
    private CheckBox saveLoginCheckBox;
    private ProgressDialog progressDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextTextLoginUsername);
        password = findViewById(R.id.editTextTextLoginPassword);
        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        Button loginButton = findViewById(R.id.buttonLogin);
        TextView newUser = findViewById(R.id.textViewNewUser);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        loginButton.setOnClickListener(this);
        newUser.setOnClickListener(this);

        context = this;

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("authPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
//
//        if (isLoggedIn) {
//            // User is already logged in, navigate to the main screen
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish(); // Prevent going back to LoginActivity when pressing back button
//        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.buttonLogin) {
            userLogin();
        } else if (viewId == R.id.textViewNewUser) {
            // Handle new user registration
            // Add your registration logic here
        }
    }

    private void userLogin() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if (userEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.setError("Please enter a valid Email");
            email.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            password.setError("Please enter your Password");
            password.requestFocus();
            return;
        }

        progressDialog.setMessage("Login...");
        progressDialog.show();


        Call<Login_Response> call = RequestHandler.getInstance().getApi().login(userEmail, userPassword);

        call.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                // Log the request URL
                Log.d("Retrofit", "Request URL: " + call.request().url());

                // Log the response code
                Log.d("Retrofit", "Response : " + response);

                if (response.isSuccessful() && response.body() != null) {
                    Login_Response loginResponse = response.body();
                    String message = loginResponse.getMessage();
                    Log.d("Retrofit", "Login successful: " + message);

                    Login_Response.User user = loginResponse.getData().getUser(); // Get the User object from Data
                    if (user != null) {
                        // Login successful, store the token
                        String token = loginResponse.getData().getToken(); // Get the token from Data
                        SharedPrefManager.getInstance(context).saveToken(token);
                        Log.d("Retrofit", "Login successful, Token: " + token);
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        // Handle unsuccessful login
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                progressDialog.dismiss();
                // Log the request URL
                Log.d("Retrofit", "Request URL: " + call.request().url());

                // Handle failure
                Log.e("Retrofit", "Login request failed: " + t.getMessage());
                if (t instanceof IOException) {
                    Toast.makeText(LoginActivity.this, "Network error occurred. Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void saveToken(String token) {
//        // Store the token in SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("authPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("token", token);
//        editor.putBoolean("isLoggedIn", true); // Save login status
//        editor.apply();
//        Log.d("Retrofit", "Login successful, Token: " + token);
//
//    }

}
