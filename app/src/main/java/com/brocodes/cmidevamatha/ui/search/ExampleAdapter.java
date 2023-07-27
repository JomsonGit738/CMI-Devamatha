package com.brocodes.cmidevamatha.ui.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.brocodes.cmidevamatha.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends
        RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {
    private List<ExampleItem> exampleList;
    private List<ExampleItem> exampleListFull;

    Context myContext;
    String search_name, imgURL;
    DatabaseReference ref_one;
    ProgressDialog TempDialog;
    CountDownTimer countDownTimer;

    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView2;
        LinearLayout linear_search;
        ExampleViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView2 = itemView.findViewById(R.id.textView2);
            linear_search = itemView.findViewById(R.id.linear_search_item);

        }
    }
    ExampleAdapter(List<ExampleItem> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = exampleList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView2.setText(currentItem.getText2());
        holder.linear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.textView2.getContext(), "hello "+currentItem.getText2(), Toast.LENGTH_SHORT).show();
                /*Intent search = new Intent(holder.textView2.getContext(),Search_Result.class);
                search.putExtra("search_name",currentItem.getText2());
                holder.textView2.getContext().startActivity(search);*/
                myContext = holder.textView2.getContext();
                search_name = currentItem.getText2();
                FetchImage(myContext);
            }
        });
    }
    public void FetchImage(Context context){
        TempDialog = new ProgressDialog(context);
        TempDialog.setMessage("Please Wait...");
        TempDialog.setCancelable(false);
        ref_one = FirebaseDatabase.getInstance().getReference("houses").child(search_name);
        ref_one.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()){

                    FOject jis1 = snapshot1.getValue(FOject.class);
                    imgURL = jis1.getHouseimage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        TempDialog.show();
        countDownTimer = new CountDownTimer(3500,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TempDialog.setMessage("Please Wait...!");
            }

            @Override
            public void onFinish() {
                TempDialog.dismiss();
                Intent search = new Intent(context,Search_Result.class);
                search.putExtra("search_name",search_name);
                search.putExtra("search_url",imgURL);
                context.startActivity(search);
            }
        }.start();

    }




    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    private Filter examplefilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ExampleItem> filterlist=new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filterlist.addAll(exampleListFull);
            }
            else{
                String pattrn=constraint.toString().toLowerCase().trim();
                for(ExampleItem item :exampleListFull){
                    if(item.getText2().toLowerCase().contains(pattrn)){
                        filterlist.add(item);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filterlist;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
