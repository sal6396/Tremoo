package com.ai.tremoo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ai.tremoo.Models.Register_Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText edname, edemail, edpassword, edconfirm, edphone, edCity;
    private Button btn;
    CheckBox checkBox1, checkBox2;
    Spinner spinnerCountry, spinnerEducation;
    List<String> countries = CountryList.getCountryNames();
    List<String> education = Arrays.asList("Level of Education", "High School", "College", "Graduate", "Post-Graduate");
    private ProgressDialog progressDialog;
    private Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edname = findViewById(R.id.editTextTextRegUsername);
        edpassword = findViewById(R.id.editTextTextRegPassword);
        edconfirm = findViewById(R.id.editTextTextRegConfirmPassword);
        edemail = findViewById(R.id.editTextEmail);
        btn = findViewById(R.id.buttonReg);
        edphone = findViewById(R.id.editTextTextPhoneNo);
        edCity = findViewById(R.id.editTextTextCity);
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerEducation = findViewById(R.id.spinnerEducation);



        View backButton = findViewById(R.id.back);
        backButton.setVisibility(View.VISIBLE);

        TextView titleTextView = findViewById(R.id.title);
        String dynamicTitle = "Register for New User";
        titleTextView.setText(dynamicTitle);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Handle back button click here
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        context = this;

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, education);
        spinnerEducation.setAdapter(adapter1);

        spinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedEducation = (String) parentView.getItemAtPosition(position);
                // Store the selected education or perform any action based on the selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCountry = (String) parentView.getItemAtPosition(position);
                // Store the selected country or perform any action based on the selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle CheckBox 1 state change
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle CheckBox 2 state change
            }
        });
    }

    private void registerUser() {
        String name = edname.getText().toString().trim();
        String email = edemail.getText().toString().trim();
        String password = edpassword.getText().toString().trim();
        String confirm = edconfirm.getText().toString().trim();
        String phoneNo = edphone.getText().toString().trim();
        String city = edCity.getText().toString().trim();
        String selectedCountry = spinnerCountry.getSelectedItem().toString();
        String selectedEducation = spinnerEducation.getSelectedItem().toString();
        int checkBox1Value = checkBox1.isChecked() ? 1 : 0;
        int checkBox2Value = checkBox2.isChecked() ? 1 : 0;

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirm) || TextUtils.isEmpty(phoneNo) || TextUtils.isEmpty(selectedCountry) || TextUtils.isEmpty(selectedEducation) ||
                TextUtils.isEmpty(city)) {

            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        Call<Register_Response> call = RequestHandler.getInstance().getApi().register(
                name, email, password, confirm, phoneNo, city, selectedCountry, selectedEducation,
                String.valueOf(checkBox1Value), String.valueOf(checkBox2Value));

        call.enqueue(new Callback<Register_Response>() {
            @Override
            public void onResponse(Call<Register_Response> call, Response<Register_Response> response) {
                // Log the request URL
                Log.d("Retrofit", "Request URL: " + call.request().url());

                // Log the response code
                Log.d("Retrofit", "Response : " + response);

                if (response.isSuccessful() && response.body() != null) {
                    Register_Response registerResponse = response.body();
                    String message = registerResponse.getMessage();
                    Log.d("Retrofit", "Login successful: " + message);

                    Register_Response.User user = registerResponse.getData().getUser(); // Get the User object from Data
                    if (user != null) {
                        // Login successful, store the token
                        String token = registerResponse.getData().getToken(); // Get the token from Data
                        SharedPrefManager.getInstance(context).saveToken(token);
                        Log.d("Retrofit", "Register successful, Token: " + token);
                        Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        // Handle unsuccessful login
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(RegisterActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Register_Response> call, Throwable t) {
                progressDialog.dismiss();
                // Log the request URL
                Log.d("Retrofit", "Request URL: " + call.request().url());

                // Handle failure
                Log.e("Retrofit", "Login request failed: " + t.getMessage());
                if (t instanceof IOException) {
                    Toast.makeText(RegisterActivity.this, "Network error occurred. Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
//        private void saveToken(String token) {
//            // Store the token in SharedPreferences
//            SharedPreferences sharedPreferences = getSharedPreferences("authPrefs", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("token", token);
//            editor.putBoolean("isRegister", true); // Save login status
//            editor.apply();
//            Log.d("Retrofit", "Register successful, Token: " + token);
//
//        }
}


















//    public static boolean isvalid(String passwordhere) {
//        int f1 = 0, f2 = 0, f3 = 0;
//        int p, r, s;
//        if (passwordhere.length() < 8) {
//            return false;
//        } else {
//            for (p = 0; p < passwordhere.length(); p++) {
//                if (Character.isLetter(passwordhere.charAt(p))) {
//                    f1 = 1;
//                }
//            }
//            for (r = 0; r < passwordhere.length(); r++) {
//                if (Character.isDigit(passwordhere.charAt(r))) {
//                    f2 = 1;
//                }
//            }
//            for (s = 0; s < passwordhere.length(); s++) {
//                char c = passwordhere.charAt(s);
//                if (c >= 33 && c <= 46 || c == 64) {
//                    f3 = 1;
//                }
//            }
//        }
//        if (f1 == 1 && f2 == 1 && f3 == 1)
//            return true;
//        return false;
//    }
//}










//  btn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                String username = edusername.getText().toString();
//                String email = edemail.getText().toString();
//                String password = edpassword.getText().toString();
//                String confirm  = edconfirm.getText().toString();
//                Database db =new Database(getApplicationContext(), "healthcare", null, 1);
//
//
//                if (username.length() == 0 || email.length() == 0 || password.length() == 0|| confirm.length() == 0 )
//                {
//                    Toast.makeText(RegisterActivity.this, "Please Fill All The Details.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else if (password.compareTo(confirm)==0)
//                    if (isvalid(edpassword.getText().toString()))
//                    {
//                        db.register(username,email,password);
//                        Toast.makeText(RegisterActivity.this, "Record Inserted", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                    }
//                    else
//                    {
//                        Toast.makeText(RegisterActivity.this, "Password must contain at least 8 characters, having latter,digit and special symbol", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                    Toast.makeText(RegisterActivity.this, "Password and Confirm Password didn't match", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//