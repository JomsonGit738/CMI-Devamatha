package com.brocodes.cmidevamatha.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.brocodes.cmidevamatha.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    //CalObject calObject;
    CalendarView calendarView;
    TextView cal_text;//curDate;
    String mon,Day;
    DatabaseReference yearRef;
    private TextView bible_Reading,Birthday,CMI_obituary,
            Daily_Saints,Feast_day,Final_Vow,
            First_Vow,Ordination,Other_Remarks;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View cal = inflater.inflate(R.layout.fragment_calendar, container, false);

        bible_Reading = cal.findViewById(R.id.one);
        Birthday = cal.findViewById(R.id.two);
        CMI_obituary = cal.findViewById(R.id.three);
        Daily_Saints = cal.findViewById(R.id.four);
        Feast_day = cal.findViewById(R.id.five);
        Final_Vow = cal.findViewById(R.id.six);
        First_Vow = cal.findViewById(R.id.seven);
        Ordination = cal.findViewById(R.id.eight);
        Other_Remarks = cal.findViewById(R.id.nine);

        calendarView = cal.findViewById(R.id.calendarView);
        cal_text = cal.findViewById(R.id.cal_result);

        cal_text.setText(getTodayDate());
        DateFormation(getTodayDate());
        //curDate = cal.findViewById(R.id.cuRRentDate);

        //Calendar calendar = Calendar.getInstance();
        //String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        //curDate.setText(currentDate);
        //Log.d("myLOG", currentDate);

        //curDate.setText(getTodayDate());



        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            //String date = dayOfMonth+"/"+(month+1)+"/"+year;

            //emptying values for next response
            bible_Reading.setText("");
            Birthday.setText("");
            CMI_obituary.setText("");
            Daily_Saints.setText("");
            Feast_day.setText("");
            Final_Vow.setText("");
            First_Vow.setText("");
            Ordination.setText("");
            Other_Remarks.setText("");



            int Month = month+1;
            Day = String.valueOf(dayOfMonth);
            if(dayOfMonth < 10){
                Day = "0"+dayOfMonth;
            }
            if(1 == Month){
                mon = "January";
            }
            else if(2 == Month){
                mon = "February";
            }
            else if(3 == Month){
                mon = "March";
            }
            else if(4 == Month){
                mon = "April";
            }
            else if(5 == Month){
                mon = "May";
            }
            else if(6 == Month){
                mon = "June";
            }
            else if(7 == Month){
                mon = "July";
            }
            else if(8 == Month){
                mon = "August";
            }
            else if(9 == Month){
                mon = "September";
            }
            else if(10 == Month){
                mon = "October";
            }
            else if(11 == Month){
                mon = "November";
            }
            else {
                mon = "December";
            }

            String Day_With_month = mon+" "+Day;
            cal_text.setText(Day_With_month);
            DateFormation(Day_With_month);
        });

        return cal;

    }

    private String getTodayDate()
    {
        return new SimpleDateFormat( "MMMM"+" "+"dd", Locale.getDefault()).format(new Date());
    }


    private void DateFormation(String Day_With_month){
        //////////////////////////////////////////////////////////////////////
        yearRef = FirebaseDatabase.getInstance().getReference().child("year").child(Day_With_month);
        yearRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    CalObject calOb = snapshot1.getValue(CalObject.class);

                    assert calOb != null;
                    bible_Reading.setText("");
                    String one = calOb.getBiblereading();
                    bible_Reading.setText(one.replace("•","\n•"));

                    Birthday.setText("");
                    String two = calOb.getBirthday();
                    Birthday.setText(two.replace("•","\n•"));

                    CMI_obituary.setText("");
                    String three = calOb.getCmiobituary();
                    CMI_obituary.setText(three.replace("•","\n•"));

                    Daily_Saints.setText("");
                    String four = calOb.getDailysaints();
                    Daily_Saints.setText(four.replace("•","\n•"));

                    Feast_day.setText("");
                    String five = calOb.getFeastday();
                    Feast_day.setText(five.replace("•","\n•"));

                    Final_Vow.setText("");
                    String six = calOb.getFinalvow();
                    Final_Vow.setText(six.replace("•","\n•"));

                    First_Vow.setText("");
                    String seven = calOb.getFirstvow();
                    First_Vow.setText(seven.replace("•","\n•"));

                    Ordination.setText("");
                    String eight = calOb.getOrdination();
                    Ordination.setText(eight.replace("•","\n•"));

                    Other_Remarks.setText("");
                    String nine = calOb.getOthermarks();
                    Other_Remarks.setText(nine.replace("•","\n•"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


