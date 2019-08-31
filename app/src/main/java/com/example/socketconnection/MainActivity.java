package com.example.socketconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {


    EditText txtName;
    EditText txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[] {Manifest.permission.INTERNET},1);
        txtName = (EditText)findViewById(R.id.editTextName);
        txtPassword = (EditText)findViewById(R.id.editTextPassword);



    }

    public void onLogin(View view) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = txtName.getText().toString();
                String password = txtPassword.getText().toString();
                try {
                    Socket serverConnect = new Socket("192.168.1.107", 9000); //Change The Ip Address to your Pc's Ip

                    DataOutputStream dos = new DataOutputStream(serverConnect.getOutputStream()); // To Send Data
                    DataInputStream dis = new DataInputStream(serverConnect.getInputStream()); // To Receive Data

                    dos.writeUTF(name);
                    dos.writeUTF(password);

                    String status = dis.readUTF();
                    if(status.equals("Welcome"))
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
                            }
                        });
                    else
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_LONG).show();
                            }
                        });

                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        thread.start();

    }
}
