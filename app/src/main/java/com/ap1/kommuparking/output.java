package com.ap1.kommuparking;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class output extends AppCompatActivity {

    String slot;
    int y=290;
    int hr;
    int min;
    int fare=30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Bundle extras = getIntent().getExtras();
        slot=extras.getString("slot");
        hr=Integer.parseInt(extras.getString("hr"));
        min=Integer.parseInt(extras.getString("min"));

        fare=fare*(hr+min/60);



        TextView slot_al = (TextView)findViewById(R.id.textView11);
        TextView fair = (TextView)findViewById(R.id.textView12);
        slot_al.setText("YOUR ALLOTTED SLOT NO: "+slot);
        fair.setText("TICKET FARE:"+ fare +" Rupees");


    }
}
