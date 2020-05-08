package com.doyal2020.AllmyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doyal2020.HandlerAllclass.StudentNRHandler;
import com.doyal2020.HandlerAllclass.SubjectAddHandler;
import com.doyal2020.presentapps.R;

import org.w3c.dom.Text;

import java.util.List;

public class StudentDetailsAdapter extends RecyclerView.Adapter<StudentDetailsAdapter.MyViewHolder> {

    Context context;
    private List<StudentNRHandler> studentNRHandlers;

    public StudentDetailsAdapter(Context context, List<StudentNRHandler> studentNRHandlers) {
        this.context = context;
        this.studentNRHandlers = studentNRHandlers;
    }

    @NonNull
    @Override
    public StudentDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.student_layout,parent,false);



        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull StudentDetailsAdapter.MyViewHolder holder, int position) {


        StudentNRHandler studentData=studentNRHandlers.get(position);

        holder.nameTextview.setText(studentData.getStudent());
        holder.rollTextView.setText(studentData.getRoll());
        holder.shifTextview.setText(studentData.getShift());
        holder.groupTextview.setText(studentData.getGroup());



    }

    @Override
    public int getItemCount() {
        return studentNRHandlers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextview;
        TextView rollTextView;
        TextView shifTextview;
        TextView groupTextview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextview=itemView.findViewById(R.id.studentName_id);
            rollTextView=itemView.findViewById(R.id.roll_id);
            shifTextview=itemView.findViewById(R.id.shift_Id);
            groupTextview=itemView.findViewById(R.id.group_Id);
        }
    }
}
