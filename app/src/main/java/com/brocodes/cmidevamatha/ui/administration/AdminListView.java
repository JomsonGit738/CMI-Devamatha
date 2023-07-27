package com.brocodes.cmidevamatha.ui.administration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.brocodes.cmidevamatha.R;


public class AdminListView extends AppCompatActivity {
    int track_changer;
    int tracking_list;
    ListView listView_admin;
    String[] mTitle;// = {"Facebook", "Whatsapp", "Twitter", "Instagram", "Youtube"};
    String[] mDescription;// = {"Facebook Description", "Whatsapp Description", "Twitter Description", "Instagram Description", "Youtube Description"};
    int[] images;// = {R.drawable.dev1,R.drawable.dev1,R.drawable.dev1,R.drawable.dev1,R.drawable.dev1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_view);
        listView_admin = findViewById(R.id.admin_listView);
        Intent value = getIntent();
        if (this.getIntent().getExtras() != null) {
            tracking_list = value.getIntExtra("list_name", 1);
            track_changer =tracking_list;
        }

        if(tracking_list == 1 ) {
            mTitle = new String[] {"Fr. Dr. Davis Panakkal CMI", "Fr. Francis Kurissery CMI", "Fr. Rijo Payyappilly CMI", "Fr. Thomas Vazhakkala CMI", "Fr. Franco Chittilappilly CMI","Fr. Jose Thanickal CMI","Fr Fijo Chirayath CMI"};
            mDescription = new String[] {"Devamatha Provincial", "Vicar Provincial, Councillor for Education and Media of Communication", "Councillor for Evangelization & Pastoral Ministry", "Councillor for Social Apostolate", "Councillor for Finance and Agriculture","Provincial Auditor","Secretary to Provincial"};
            images = new int[] {R.drawable.dev1,R.drawable.dev2,R.drawable.dev3,R.drawable.dev4,R.drawable.dev5,R.drawable.dev6,R.drawable.dev7};
            MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
            listView_admin.setAdapter(adapter);
        } else if(tracking_list == 2){
            mTitle = new String[] {"Fr. Dr. Sheejan Thomas Pullokkaran CMI", "Rev. Fr. Joseph Akkara CMI", "Rev. Fr. Joby Kachappilly CMI", " Rev. Fr. Joy Vattoly (Sr) CMI", " Rev. Fr. Jaison Vadakkethala CMI","Rev. Fr Cijin Thaikadan CMI"};
            mDescription = new String[] {"Regional Superior", "Pastoral Ministry & Evangelization", "Finance and Agriculture", "Social Apostolate & Health Care Ministr", "Education & Mass Media","Auditor"};
            images = new int[] {R.drawable.dh1,R.drawable.dh2,R.drawable.dh3,R.drawable.dh4,R.drawable.dh5,R.drawable.dh6};
            MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
            listView_admin.setAdapter(adapter);
        } else if(tracking_list == 3){
            mTitle = new String[] {"Fr. Anto Thekkudan CMI", "Fr. Julius Kamuti CMI", "Fr. Christopher Makau CMI", "Fr. Lawrence Kinyua CMI", "Fr. Ajith Pullokkaran CMI","Fr. Stephen Kasisi CMI"};
            mDescription = new String[] {"Regional Superior", "Social Apostolate", "Education and Mass Media", "Mission and Pastoral Ministry", "Finance and Agriculture","Auditor"};
            images = new int[] {R.drawable.k1,R.drawable.k2,R.drawable.k3,R.drawable.k4,R.drawable.k5,R.drawable.k6};
            MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
            listView_admin.setAdapter(adapter);

        } else if(tracking_list == 4){
            mTitle = new String[] {"Fr. Louisraj Porathur CMI", "Fr. Rinson Pullokkaran CMI", "Fr Agustine Ananailayil CMI", "Fr. Jinto Erinjery CMI"};
            mDescription = new String[] {"Sub-regional Superior", "Councillor", "Councillor, Econome", "Auditor"};
            images = new int[] {R.drawable.m1,R.drawable.m2,R.drawable.m3,R.drawable.m4};
            MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
            listView_admin.setAdapter(adapter);
        }
        else if(tracking_list == 5){
            mTitle = new String[] {"2017-2020", "2014-2017", "2011-2014", "2008-2011","2005-2008","2002-2005","1999-2002","1996-1999","1993-1996","1990-1993","1987-1990","1984-1987","1981-1984","1978-1981","1975-1978","1972-1975","1968-1972","1965-1968","1962-1965","1959-1962","1956-1959","1953-1956"};
            mDescription = new String[] {"Provincial\t\t\t:\tFr. Walter Thelappilly\n" +
                    "\tCouncillors\t:\tFrs. Davis Panakkal (Vicar Provincial), Shaju Edamana, Fr. Joy Vattoly (Jr)," +
                    "Paulson Paliakkara\n" +
                    "\tAuditor\t:\t\tFr. Jolly Andrews Maliyakkal\n","Provincial\t\t\t:\tFr. Walter Thelappilly\n" +
                    "\tCouncillors\t:\tFrs. Thomas Chakkalamattath (Vicar Provincial), Anil George Konkoth, Joy Vattoly (Jr), " +
                    "Jose Kavungal\n" +
                    "\tAuditor\t:\t\tFr. Antony Perinchery\n","Provincial\t\t\t:\tFr. Paul Achandy\n" +
                    "\tCouncillors\t:\tFrs. Paulson Paliakkara (Vicar Provincial), Jolly Andrews Maliyakkal, Thomas Vazhakkala, " +
                    "Joshy Kattookaran\n" +
                    "\tAuditor\t:\t\tFr. Anil George Konkoth\n","Provincial\t\t\t:\tFr. George Pius Ukken\n" +
                    "\tCouncillors\t:\tFrs. Paulson Paliakkara (Vicar Provincial), John Neelankavil, Jacob Njerinjampilly, " +
                    "Paul Alengattukaran\n" +
                    "\tAuditor\t:\t\tFr. Joy Peenikkaparambil\n","Provincial\t\t\t:\tFr. Jerome Cherussery " +
                    "\tCouncillors\t:\tFrs. Paul Achandy (Vicar Provincial)," +
                    "Lucius Nereparambil, Thomas Vazhakkala, Jose Stephen Menachery\n" +
                    "\tAuditor\t\t\t\t:\tFr. Joshy Kattookaran\n","Provincial\t\t\t:\tFr. Jerome Cherussery\n" +
                    "\tCouncillors\t:\tFrs. Winson Parekkat (Vicar Provincial), Francis Kurissery, John Joseph Kollannoor, Antony Paramban\n" +
                    "\tAuditor\t\t\t\t:\tFr. Joshy Kattookaran\n","Provincial \t\t\t:\tFr. Sebastain Athappilly\n" +
                    "\tCouncillors\t:\tFrs. Paul Thazhath (Vicar Provincial)," +
                    "Anto Thekkudan, Walter Thelappilly," +
                    "Paul Alengattukaran\n" +
                    "\tAuditor\t\t\t\t:\tFr. Jacob Njerinjampilly\n","Provincial\t\t\t:\tFr. Norbert Edattukaran\n" +
                    "\tCouncillors\t:\tFrs. Joseph Aureus Chakkalamattath" +
                    "(Vicar Provincial), Jerome Cherussery, Francis Kurissery, George Pius Ukken\n" +
                    "\tAuditor\t\t\t\t:\tBro. Abraham Malieckal\n","Provincial\t\t\t:\tFr. Eustace Thottan\n" +
                    "\tCouncillors\t:\tFrs. Norbert Edattukaran (Vicar Provincial), Davis  Pattath, Paul Kodiyan and " +
                    "Jose Chittilappilly\n" +
                    "\tAuditor\t\t\t\t:\tFr. George Thaliyaparambil\n","Provincial\t\t\t:\tFr. Mathew Kattumath\n" +
                    "\tCouncillors\t:\tFrs. Joseph Vivian Ambooken" +
                    "(Vicar Provincial), Jerome Cherussery, Jacob Achandy and Thomas Chakramakkal\n" +
                    "\tAuditor\t\t\t\t:\tFr. John Thottappilly\n","Provincial \t\t\t:\tFr. Alex Ukken\n" +
                    "\tCouncillors\t:\tFrs. Caius Edattukaran (Vicar Provincial)," +
                    "Jerome Cherussery, Antony Thattil and Paul Blaize Kadicheeni\n" +
                    "\tAuditor\t\t\t\t:\tFr. Mathew Kattumath\n","Provincial\t\t\t:\tFr. Eusebius Kizhakkoodan\n" +
                    "\tCouncillors\t:\tFrs. Paul Kozhippat (Vicar Provincial)," +
                    "Paul Thottan, Joseph Elias and Jose Stephen\n" +
                    "\tAuditor\t\t\t\t:\tFr. Mathew Kattumath\n","Provincial\t \t:\tFr. Alex Ukken\n" +
                    "\tCouncillors\t:\tFrs. Eusebius (Vicar Provincial)," +
                    "Paul Blaize, George Honerius " +
                    "and John Tharayil\n" +
                    "\tAuditor\t\t\t\t:\tFr. Leontius\n","Provincial \t\t\t:\tFr. Alex Ukken\n" +
                    "\tCouncillors\t:\tFrs. Aureus (Vicar Provincial),\n" +
                    "Eusebius, Antony Kachappilly and\n" +
                    "Jose Akkarakkaran\n" +
                    "\tAuditor\t\t\t\t:\tFr. Justinian\n","Provincial\t\t\t:\tFr. Gabriel\n" +
                    "\tCouncillors\t:\tFrs. Alex Ukken  (Vicar Provincial)," +
                    "Eymard,  Cyric Elias & Vincent Alappat\n" +
                    "\tAuditor\t\t\t\t:\tFr. Abdias\n","Provincial\t\t\t:\tFr. Canisius\n" +
                    "\tCouncillors\t:\tFrs. Sampson (Vicar Provincial)\n" +
                    "Callistus,  Werner and Pastor\n" +
                    "(Later Fr. Alex instead of Fr. Werner)\n" +
                    "\tAuditor\t\t\t\t:\tFr. Paul Thottan\n","Provincial \t\t\t:\tFr. Sampson\n" +
                    "\tCouncillors\t:\tFrs. Gabriel, Martin, Eymard and Celsus\n" +
                    "\tEconomous\t:\tFr. Agapitus\n","Provincial\t\t\t:\tFr. Clemens\n" +
                    "\tCouncillors\t:\tFrs. Joshua, Sampson,\n" +
                    "Benizi and Adolphus\n" +
                    "\tEconomous\t:\tFr. Justinian\n","Provincial\t\t\t:\tFr. Malachias\n" +
                    "\tCouncillors\t:\tFrs. Seraphim and Joshua\n" +
                    "\tEconomous\t:\tFr. Oliver\n","Provincial \t\t\t:\tFr. Flavian\n" +
                    "\tCouncillors\t:\tFrs. Vincent and Seraphim \n" +
                    "\tEconomous\t:\tFr. Oliver\n","Provincial\t\t\t:\tFr. Clemens\n" +
                    "\tCouncillors\t:\tFrs. Gervase and Alypius\n" +
                    "\tEconomous\t:\tFr. Gerard\n","Provincial\t\t\t:\tFr. Clemens\n" +
                    "\tCouncillors\t:\tFrs. Gerard and Dismas\n" +
                    "\tEconomous\t:\tFr. Amos\n"};
            //images = new int[] {R.drawable.devaadmini,R.drawable.devaadmini,R.drawable.devaadmini,R.drawable.devaadmini};

            MyAdapter1 adapter1 = new MyAdapter1(this, mTitle, mDescription);
            listView_admin.setAdapter(adapter1);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];


        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row_administration_details, R.id.text_admin_list, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_administration_details, parent, false);
            ImageView images = row.findViewById(R.id.admin_list_image);
            TextView myTitle = row.findViewById(R.id.text_admin_list);
            TextView myDescription = row.findViewById(R.id.text_admin_list2);

            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);
            return row;
        }
    }
    class MyAdapter1 extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];


        MyAdapter1 (Context c, String[] title, String[] description) {
            super(c, R.layout.row_administration_details1, R.id.text_admin_list, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_administration_details1, parent, false);
            TextView myTitle = row.findViewById(R.id.text_admin_list);
            TextView myDescription = row.findViewById(R.id.text_admin_list2);

            // now set our resources on views
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);
            return row;
        }
    }
}
