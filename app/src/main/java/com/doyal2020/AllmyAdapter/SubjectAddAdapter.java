package com.doyal2020.AllmyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doyal2020.HandlerAllclass.SubjectAddHandler;
import com.doyal2020.presentapps.R;

import java.util.List;

public class SubjectAddAdapter extends RecyclerView.Adapter<SubjectAddAdapter.MyViewHolder>{


    public static ClickListener clickListener;




    Context context;

    private List<SubjectAddHandler> subjectAddHandlerList;

    public SubjectAddAdapter(Context context, List<SubjectAddHandler> subjectAddHandlerList) {
        this.context = context;
        this.subjectAddHandlerList = subjectAddHandlerList;
    }



    @NonNull
    @Override
    public SubjectAddAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.subject_add_layout,parent,false);



        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAddAdapter.MyViewHolder holder, int position) {

            SubjectAddHandler addData=subjectAddHandlerList.get(position);

            holder.semsterName.setText(addData.getSemester());
            holder.departmentName.setText(addData.getDepartment());
            holder.subjectName.setText(addData.getSubject());



    }

    @Override
    public int getItemCount() {
        return subjectAddHandlerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        TextView semsterName;
        TextView departmentName;
        TextView subjectName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            semsterName=itemView.findViewById(R.id.semester_add_textview);
            departmentName=itemView.findViewById(R.id.deparment_add_textview);
            subjectName=itemView.findViewById(R.id.subject_add_textview);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {


            clickListener.onItemClick(getAdapterPosition(),v);

        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(),v);
            return false;
        }
    }

    public interface ClickListener{

        void onItemClick(int position, View view);



        void  onItemLongClick(int position,View view);


    }

    public void  setOnItemClickLisener(ClickListener clickLisener){

        SubjectAddAdapter.clickListener=clickLisener;

    }

}
