package com.example.peter_pc.volleydata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.controllers.Config;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Textfields for collecting user data
    EditText user,passwrd;

    //Your submit button
    Button login;

    //String variable that shall store collected data from user
    String username_input,password_input;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=(EditText)findViewById(R.id.editText);
        passwrd=(EditText)findViewById(R.id.editText2);
        login=(Button)findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserdata();
            }
        });


    }

    public void getUserdata(){
        username_input=user.getText().toString().trim();
        password_input=passwrd.getText().toString().trim();

        if (username_input.isEmpty()){
            user.setError("Requirred");
        }else if(password_input.isEmpty()){
            passwrd.setError("Required");
        }else {
            user.setText("");
            passwrd.setText("");
        }

        //calls the method that sends user data to the online database
        postData();


    }

    private void postData() {
        //Creating a string request

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.equalsIgnoreCase(Config.LOGIN_SUCCESS)){

                            Toast.makeText(MainActivity.this, "Success"+response, Toast.LENGTH_SHORT).show();
//                            //Creating a shared preference
//                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_USERNAME, username_input);
                params.put(Config.KEY_PASSWORD, password_input);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
