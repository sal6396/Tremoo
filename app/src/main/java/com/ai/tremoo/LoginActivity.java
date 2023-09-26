package com.ai.tremoo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
//    implements View.OnClickListener

    private EditText edEmail, edPassword;
    private Button btn;
    private TextView tv;
    private ProgressDialog progressDialog;
    private CheckBox saveLoginCheckBox;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private boolean saveLogin;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv = findViewById(R.id.textViewNewUser);
        btn = findViewById(R.id.buttonLogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        edEmail = findViewById(R.id.editTextTextLoginUsername);
        edPassword = findViewById(R.id.editTextTextLoginPassword);
        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
//        btn.setOnClickListener(this);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                               startActivity(intent);
            }
        });

//        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
//        loginPrefsEditor = loginPreferences.edit();
//
//        saveLogin = loginPreferences.getBoolean("saveLogin", false);
//        if (saveLogin) {
//            edEmail.setText(loginPreferences.getString("email", ""));
//            edPassword.setText(loginPreferences.getString("password", ""));
//            saveLoginCheckBox.setChecked(true);
//        }
//
//        if (saveLogin) {
//            String savedEmail = loginPreferences.getString("email", "");
//            String savedPassword = loginPreferences.getString("password", "");
//
//            if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
//                // Credentials are saved, automatically log in
//                edEmail.setText(savedEmail);
//                edPassword.setText(savedPassword);
//                userLogin(); // Call your login method here
//            }
//        }

    }

//    @Override
//    public void onClick(View view) {
//        if (view == btn) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(edEmail.getWindowToken(), 0);
//
//            email = edEmail.getText().toString().trim();
//            password = edPassword.getText().toString().trim();
//
//            if (saveLoginCheckBox.isChecked()) {
//                saveLoginCredentials(email, password);
//            } else {
//                clearLoginCredentials();
//            }
//
//            userLogin();
//        }
//    }

//    private void saveLoginCredentials(String email, String password) {
//        loginPrefsEditor.putBoolean("saveLogin", true);
//        loginPrefsEditor.putString("email", email);
//        loginPrefsEditor.putString("password", password);
//        loginPrefsEditor.apply();
//    }
//
//    private void clearLoginCredentials() {
//        loginPrefsEditor.clear();
//        loginPrefsEditor.apply();
//    }
//
//    private void userLogin() {
//        final String email = edEmail.getText().toString().trim();
//        final String password = edPassword.getText().toString().trim();
//
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        Log.d("Response", response);
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            Log.d("Server Response", jsonObject.toString());
//
//                            if (jsonObject.getBoolean("success")) {
//                                JSONObject userObj = jsonObject.getJSONObject("data");
//
//                                // Extract user data
//                                int id = userObj.getInt("id");
//                                String userEmail = userObj.getString("email");
//
//                                // Store user data in Shared Preferences
//                                SharedPrefManager.getInstance(LoginActivity.this)
//                                        .userLogin(id, userEmail);
//
//                                // Start the MainActivity
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(LoginActivity.this, "Error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(LoginActivity.this, "Error occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("email", email);
//                params.put("password", password);
//                return params;
//            }
//        };
//
//        // Add the request to the RequestQueue
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
//    }
}
