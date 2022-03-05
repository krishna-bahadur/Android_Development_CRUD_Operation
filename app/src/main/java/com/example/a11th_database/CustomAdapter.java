package com.example.a11th_database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a11th_database.dbutil.DBHelper;
import com.example.a11th_database.model.student_model;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.MyViewHolder>{
    private final Context c;
    private final ArrayList<student_model> details;

    public CustomAdapter(ViewActivity viewActivity, ArrayList<student_model> arrayList) {
    c =  viewActivity;
    details = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(c).inflate(R.layout.single_item,null);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText("Id: "+details.get(position).getId());
        holder.tvid.setText("Student_Id: "+details.get(position).getStid());
        holder.tvname.setText("Name: "+details.get(position).getName());
        holder.tvfac.setText("Faculty: "+details.get(position).getFac());
        holder.tvsem.setText("Semester: "+details.get(position).getSem());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(c);
                View v = LayoutInflater.from(c).inflate(R.layout.edit_delete,null);
                alert.setView(v);

                EditText etid = v.findViewById(R.id.sid);
                EditText etname = v.findViewById(R.id.sname);
                EditText etsem = v.findViewById(R.id.ssem);
                Button edit = v.findViewById(R.id.edit);
                Button del = v.findViewById(R.id.delete);

                AlertDialog all = alert.create();
                all.show();

                etid.setText(String.valueOf(details.get(holder.getAdapterPosition()).getStid()));
                etname.setText(String.valueOf(details.get(holder.getAdapterPosition()).getName()));
                etsem.setText(String.valueOf(details.get(holder.getAdapterPosition()).getSem()));
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       student_model studentModel = new student_model();
                       studentModel.setStid(details.get(holder.getAdapterPosition()).getStid());
                       studentModel.setName(etname.getText().toString());
                       studentModel.setSem(Integer.parseInt(etsem.getText().toString()));
                       DBHelper db = new DBHelper(c);
                       db.updateRecoed(studentModel);
                        //details.set(0,studentModel);
                        notifyDataSetChanged();
                        all.dismiss();

                    }
                });
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DBHelper db = new DBHelper(c);
//                        studentid extract garne through getAdapterPosition()
                        db.deleteRecord(details.get(holder.getAdapterPosition()).getStid());
                        Toast.makeText(c,"Record deleted successfully ",Toast.LENGTH_SHORT).show();
                        details.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        all.dismiss();
                    }
                });
                return true;
            }
        });
    }
    @Override
    public int getItemCount(){
        return details.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{
       TextView tv,tvid,tvname,tvfac,tvsem;

       public MyViewHolder(@NonNull View itemView){
           super(itemView);
           tv = itemView.findViewById(R.id.mainid);
           tvid = itemView.findViewById(R.id.tv);
           tvname = itemView.findViewById(R.id.tv1);
           tvfac = itemView.findViewById(R.id.tv2);
           tvsem = itemView.findViewById(R.id.tv3);
       }
    }
}