package com.example.a11th_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a11th_database.dbutil.DBHelper;
import com.example.a11th_database.model.student_model;

import java.time.temporal.ValueRange;

public class MainActivity extends AppCompatActivity {
    private Button add, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        view = findViewById(R.id.view);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddDialog();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ViewActivity.class));
            }
        });

    }
    private void createAddDialog(){


        AlertDialog.Builder alertLayout = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.activity_add,null);
        alertLayout.setView(view);

        EditText etname = view.findViewById(R.id.name);
        EditText etid = view.findViewById(R.id.id);
        EditText etfac = view.findViewById(R.id.faculty);
        EditText etsem = view.findViewById(R.id.sem);
        Button save = view.findViewById(R.id.save);

        AlertDialog alert = alertLayout.create();
        alert.show();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student_model studentModel = new student_model();
                studentModel.setName(etname.getText().toString());
                studentModel.setStid(Integer.parseInt(etid.getText().toString()));
                studentModel.setFac(etfac.getText().toString());
                studentModel.setSem(Integer.parseInt(etsem.getText().toString()));
                insertData(studentModel);
                alert.dismiss();
            }
        });
    }
    private void insertData(student_model studentModel){
        if(dataValid(studentModel)){
            //insertion
            DBHelper db = new DBHelper(MainActivity.this);
            db.insertDataToDb(studentModel);
            Toast.makeText(MainActivity.this, "Successful",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Invalid Inputs",Toast.LENGTH_LONG).show();
        }
    }
    private boolean dataValid(student_model studentModel){
        boolean valid = true;
        if(TextUtils.isEmpty(studentModel.getName())){
            valid= false;
        }
        if(TextUtils.isEmpty(studentModel.getFac())){
            valid=false;
        }
        return valid;
    }
}