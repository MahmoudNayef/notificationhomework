package com.example.assignmentnotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.UnsupportedEncodingException;

public class Register extends AppCompatActivity {
    EditText name , num , pass , email1;
    TextView txtvlog;
    Button btnsignup;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        num = findViewById(R.id.num);
        pass = findViewById(R.id.pass);
        email1 = findViewById(R.id.email1);
        txtvlog = findViewById(R.id.txtvlog);
        btnsignup = findViewById(R.id.btnsignup);


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data="{"+
                        "\"name\""   + ":"+  "\""+name.getText().toString()+"\","+
                        "\"num\""  + ":"+  "\""+num.getText().toString()+"\","+
                        "\"email\""       + ":"+  "\""+email1.getText().toString()+"\","+
                        "\"password\""    + ":"+   "\""+pass.getText().toString()+"\""+
                        "}";
                Submit(data);
            }
        });

        txtvlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Register.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void Submit(String data) {
        String savedata = data;
        String URL="https://mcc-users-api.herokuapp.com/login";
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        Log.d("TAG", "requestQueue: " + requestQueue);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Get response on JsonObject
                    JSONObject objres = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),objres.toString(),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Server Error ",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public String getBodyContentType(){return "application/json; charset=utf-8";}
            @Override
            public byte[] getBody() throws AuthFailureError {
                try{
                    Log.d("TAG", "savedata: " + savedata);
                    return savedata==null?null:savedata.getBytes("utf-8");
                }catch(UnsupportedEncodingException uee){
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
    }
}