package com.doyal2020.AllmyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doyal2020.HandlerAllclass.PresentShowHandler;
import com.doyal2020.HandlerAllclass.StudentNRHandler;
import com.doyal2020.presentapps.R;

import java.util.List;

public class PresentShowAdapter extends RecyclerView.Adapter<PresentShowAdapter.MyViewHolder> {

    private Context context;
    private List<PresentShowHandler> presentShowHandlerList;

    public PresentShowAdapter(Context context, List<PresentShowHandler> presentShowHandlerList) {
        this.context = context;
        this.presentShowHandlerList = presentShowHandlerList;
    }

    @NonNull
    @Override
    public PresentShowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.presentshow_layout,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PresentShowAdapter.MyViewHolder holder, int position) {

        PresentShowHandler studentData=presentShowHandlerList.get(position);
        holder.rollTextview.setText(studentData.getRoll());
        holder.presentTextview.setText(studentData.getPresent());
        holder.timeTextview.setText(studentData.getTime());

    }

    @Override
    public int getItemCount() {
        return presentShowHandlerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView rollTextview;
        TextView presentTextview;
        TextView timeTextview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            rollTextview=itemView.findViewById(R.id.rollNumbe_show_id);
            presentTextview=itemView.findViewById(R.id.presntShow_Id);
            timeTextview=itemView.findViewById(R.id.timeShow_id);

        }
    }
}
