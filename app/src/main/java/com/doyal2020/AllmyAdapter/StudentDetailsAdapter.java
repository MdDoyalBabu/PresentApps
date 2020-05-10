package com.doyal2020.AllmyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doyal2020.HandlerAllclass.PresentShowHandler;
import com.doyal2020.HandlerAllclass.StudentNRHandler;
import com.doyal2020.HandlerAllclass.SubjectAddHandler;
import com.doyal2020.presentapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class StudentDetailsAdapter extends RecyclerView.Adapter<StudentDetailsAdapter.MyViewHolder> {

    private  static ClickListener clickListener;
    private  Context context;
    private List<StudentNRHandler> studentNRHandlers;

    private DatabaseReference mDatabase;
    private FirebaseUser mCurrent_user;



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
    public void onBindViewHolder(@NonNull StudentDetailsAdapter.MyViewHolder holder, final int position) {


        StudentNRHandler studentData=studentNRHandlers.get(position);

        holder.nameTextview.setText(studentData.getStudent());
        holder.rollTextView.setText(studentData.getRoll());
        holder.shifTextview.setText(studentData.getShift());
        holder.groupTextview.setText(studentData.getGroup());

        holder.presentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(position,"present") ;

            }
        });
        holder.absentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData(position,"absent") ;
            }
        });




    }

    ////...................Start present method....................///


    private void saveData(int position, String attendence) {


        String name,roll,group,shift;

        StudentNRHandler studentNR=studentNRHandlers.get(position);


        name=studentNR.getStudent();
        roll=studentNR.getRoll();
        group=studentNR.getGroup();
        shift=studentNR.getShift();

        String subjectName=studentNR.getSubjectName();
        String value=name.concat(roll).concat(group).concat(shift).concat(subjectName);


        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid=mCurrent_user.getUid();


        Calendar calendar=Calendar.getInstance();
        final String  currentTime= DateFormat.getDateTimeInstance().format(calendar.getTime());


        PresentShowHandler presentShowHandler=new PresentShowHandler(currentTime,attendence,roll);


        mDatabase = FirebaseDatabase.getInstance().getReference("PresentApps").child("UserData")
             .child(current_uid).child("StudentPresent").child(value);

        String key=mDatabase.push().getKey();

        mDatabase.child(key).setValue(presentShowHandler);

        Toast.makeText(context, roll+" Is : "+attendence, Toast.LENGTH_SHORT).show();

    }

    ////...................end present method....................///




    @Override
    public int getItemCount() {
        return studentNRHandlers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        TextView nameTextview;
        TextView rollTextView;
        TextView shifTextview;
        TextView groupTextview;

        Button presentButton;
        Button absentButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextview=itemView.findViewById(R.id.studentName_id);
            rollTextView=itemView.findViewById(R.id.roll_id);
            shifTextview=itemView.findViewById(R.id.shift_Id);
            groupTextview=itemView.findViewById(R.id.group_Id);
            presentButton=itemView.findViewById(R.id.present_button_Id);
            absentButton=itemView.findViewById(R.id.absent_button_Id);


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

    public void  setOnItemClickLisener(StudentDetailsAdapter.ClickListener clickLisener){

        StudentDetailsAdapter.clickListener=clickLisener;

    }

}
