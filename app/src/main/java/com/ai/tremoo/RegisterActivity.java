package com.ai.tremoo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edname, edemail, edpassword, edconfirm, edphone, edEduc, edCity;
    private Button btn;
    private Spinner spinnerCountry;

    private Snackbar snackbar;
    private ProgressDialog progressDialog;

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
        edEduc = findViewById(R.id.editTextTextEducation);
        edCity = findViewById(R.id.editTextTextCity);
        spinnerCountry = findViewById(R.id.spinnerCountry);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = edname.getText().toString().trim();
        String email = edemail.getText().toString().trim();
        String password = edpassword.getText().toString().trim();
        String confirm = edconfirm.getText().toString().trim();
        String phoneNo = edphone.getText().toString().trim();
        String country = spinnerCountry.getSelectedItem().toString().trim();
        String education = edEduc.getText().toString().trim();
        String city = edCity.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edname.setError("Username is required");
            edname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            edemail.setError("Email is required");
            edemail.requestFocus();
            return;
        }

        if (!isValidPassword(password)) {
            edpassword.setError("Password must be at least 8 characters long and contain letters, digits, and special characters");
            edpassword.requestFocus();
            return;
        }

        if (!password.equals(confirm)) {
            edconfirm.setError("Passwords do not match");
            edconfirm.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phoneNo)) {
            edphone.setError("Phone Number is required");
            edphone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(country)) {
            showSnackBar("Country is required");
            return;
        } else {
            hideSnackBar(); // Clear the error if the field is not empty
        }

        if (TextUtils.isEmpty(education)) {
            edEduc.setError("Level of Education is required");
            edEduc.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            edCity.setError("City is required");
            edCity.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering User....");
        progressDialog.show();

        // Make a POST request to the API to register the user
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        // Print the response to the logcat
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            // Check if the response contains any specific error fields
                            if (jsonObject.has("status_code")) {
                                int statusCode = jsonObject.getInt("status_code");
                                if (statusCode == 409) {
                                    String message = jsonObject.getString("message");
                                    edemail.setError(message);
                                    edemail.requestFocus();
                                } else {
                                    String message = jsonObject.getString("message");
                                    showSnackBar(message);
                                }
                            } else {
                                // Assuming a successful response since there's no specific error indication
                                Toast.makeText(RegisterActivity.this, "User registration successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showSnackBar("Error parsing response from the server");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        showSnackBar("Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("phoneNo", phoneNo);
                params.put("country", country);
                params.put("education", education);
                params.put("city", city);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void hideSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private void showSnackBar(String message) {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private boolean isValidPassword(String password) {
        // Implement your password validation logic here
        // For example, you can use the isvalid method you provided in your original code
        // or you can use regular expressions to define password requirements.
        // The current implementation is a basic check for password length and containing at least one letter, one digit, and one special character.

        int f1 = 0, f2 = 0, f3 = 0;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                f1 = 1;
            } else if (Character.isDigit(c)) {
                f2 = 1;
            } else if (c >= 33 && c <= 46 || c == 64) {
                f3 = 1;
            }
        }

        return password.length() >= 8 && f1 == 1 && f2 == 1 && f3 == 1;
    }
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