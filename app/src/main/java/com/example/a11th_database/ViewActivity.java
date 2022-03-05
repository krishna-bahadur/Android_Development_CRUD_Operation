package com.example.a11th_database;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a11th_database.dbutil.DBHelper;
import com.example.a11th_database.model.student_model;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
private RecyclerView view;
private ArrayList<student_model>  arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        view = findViewById(R.id.rv);

        DBHelper db = new DBHelper(this);
        arrayList = db.retrieveData();
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(new CustomAdapter(this,arrayList));
    }
}
