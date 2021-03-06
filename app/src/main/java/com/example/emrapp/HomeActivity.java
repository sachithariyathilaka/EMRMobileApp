package com.example.emrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    String id, HOST;
    TextView name,Id,address, gender, age, bloodGroup, username;
    Button changePswd, medicalHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        id = getIntent().getStringExtra("id");
        name = findViewById(R.id.name);
        Id = findViewById(R.id.id);
        address = findViewById(R.id.regNo);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.Age);
        bloodGroup = findViewById(R.id.bloodGroup);
        username = findViewById(R.id.username);
        changePswd = findViewById(R.id.changePswd);
        medicalHistory = findViewById(R.id.medicalHistory);
        HOST = "http://18.216.245.34/EMRapp/Patients/fetchAccountInfo.php";
        fetchAccountInfo();
        changePswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChangePswd();
            }
        });
        medicalHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMedicalHistory();
            }
        });
    }



    private void goToMedicalHistory() {
        Intent mainIntent=new Intent(HomeActivity.this, History.class);
        mainIntent.putExtra("id",id);
        startActivity(mainIntent);
    }

    private void goToChangePswd() {
        Intent mainIntent=new Intent(HomeActivity.this, changePasswordActivity.class);
        mainIntent.putExtra("id",id);
        startActivity(mainIntent);
    }

    private void fetchAccountInfo() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, HOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String Output=jsonObject.getString("output");
                            String FullName = jsonObject.getString("Name");
                            String Age = jsonObject.getString("Age");
                            String Address = jsonObject.getString("Address");
                            String Blood_Group = jsonObject.getString("Blood_Group");
                            String Gender = jsonObject.getString("Gender");
                            String Username = jsonObject.getString("Username");

                            if(Output.equals("1"))
                            {
                                name.setText("Name: "+FullName);
                                age.setText("Age: "+Age);
                                address.setText("Address: "+Address);
                                bloodGroup.setText("Blood Group: "+Blood_Group);
                                gender.setText("Gender: "+Gender);
                                username.setText("Username: "+Username);
                                Id.setText("Id: "+id);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, "Error Occured due to "+ e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(HomeActivity.this, "Error Occured "+ error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
