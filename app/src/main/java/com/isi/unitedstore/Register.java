package com.isi.unitedstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class Register extends AppCompatActivity {
    TextView login;
    Button registerButton;
    private DataBaseHelper db;
    boolean isNameValid, isEmailValid, isPasswordValid;
    EditText name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerBtn);



        db = new DataBaseHelper(Register.this);
        try {
            db.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.openDatabase();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String nameStr = name.getText().toString();

                // boolean validEmail =isEmailValid(emailStr);
                Log.e("Details :", "" + emailStr + " " + passwordStr + " " + nameStr);
               // postNewUser(Register.this, nameStr, emailStr, passwordStr);
                db.getDetails();
                SetValidation(emailStr);
               // db.registerUser(nameStr,passwordStr,emailStr);


            }
        });


        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });


    }

    public void SetValidation(String emailCheck) {
        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            name.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else  {
            isNameValid = true;
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            password.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            password.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }
       boolean emailExists = db.emailExists(emailCheck);

        if(emailExists){
            email.setError("Email already exists");
        }

        if (isNameValid && isEmailValid  && isPasswordValid && !emailExists) {
            String emailStr = email.getText().toString();
            String passwordStr = password.getText().toString();
            String nameStr = name.getText().toString();
            db.registerUser(nameStr,passwordStr,emailStr);
            Toast.makeText(getApplicationContext(), "Registration Successfully ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        }

    }


    //api call for registration

    public static void postNewUser(Context context, final String name, final String email, final String password) {
        String postUrl = "http://10.0.2.2:5000/store/register";

        RequestQueue queue = Volley.newRequestQueue(context);

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("password", password);
        params.put("email", email);
        params.put("name", name);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, (CharSequence) response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);
    }


}