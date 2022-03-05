package com.example.a11th_database.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a11th_database.model.student_model;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "studentdb";
    private SQLiteDatabase db;
    public DBHelper(Context c){
        super(c,DATABASE_NAME,null,1);
        db= getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS students(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Stid INTEGER,Name VARCHAR(25),Faculty VARCHAR(30),Semester INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        trigger on 2nd time when db chanages
//        db ma naya changes garda newer version bancha purano db drop
//        huncha arko changed db pheri oncreate huncha
//        1. db.drop()
//       2.  oncreate

//        suruko db version 1 db ma changes gare naya attributed thape version 2 huncha naya version
//                bancha so older versions has to be dropeed

        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }

    public void insertDataToDb(student_model studentModel) {
        //query na chalune bhaye contentvlaue chahincha instead no

        ContentValues cv = new ContentValues();
        //key value pair key is db name
        cv.put("Stid",studentModel.getStid());
        cv.put("Name",studentModel.getName());
        cv.put("Faculty",studentModel.getFac());
        cv.put("Semester",studentModel.getSem());

        db.insert("students",null,cv);
    }

    public ArrayList<student_model> retrieveData() {
        ArrayList<student_model> data = new ArrayList<>();
        String query = "select * from students";
//        selectargs means where bhako query
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
//                i represents columnindex
                student_model st = new student_model();
                st.setId(cursor.getInt(0));
                st.setStid(cursor.getInt(1));
                st.setName(cursor.getString(2));
                st.setFac(cursor.getString(3));
                st.setSem(cursor.getInt(4));
                data.add(st);

            } while(cursor.moveToNext());
        }
        return data;
    }

    public void deleteRecord(int stid) {
        db.execSQL("DELETE FROM students where stid="+stid);
    }
    public void updateRecoed(student_model studentModel){
        ContentValues cv = new ContentValues();
        cv.put("Semester",studentModel.getSem());
        cv.put("Name",studentModel.getName());
        db.update("students",cv,"stid= "+studentModel.getStid(),null);
    }
}