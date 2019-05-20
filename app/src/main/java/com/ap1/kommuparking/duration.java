package com.ap1.kommuparking;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;

public class duration extends AppCompatActivity{

    Connection con;
    ResultSet rs;
    String name;
    int slot=1;
    boolean flag=false;
    int hor;
    int minut;
    private final String url= "jdbc:mysql://85.10.205.173:3306/smartparking?verifyServerCertificate=false&useSSL=true";
    private final String user="smartkommu";
    private final String pass="pavanparking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duration);

        Spinner sp = (Spinner) findViewById(R.id.spinneR);


        ArrayAdapter<String> ad=new ArrayAdapter<String>(duration.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.models));
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(ad);
        Bundle extras = getIntent().getExtras();
        name=extras.getString("name");

        this.con=DbConnect();
    }

    public Connection DbConnect(){

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(url,user,pass);
            return con;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;



    }
    public void output(View view){


        EditText hr = (EditText)findViewById(R.id.hr);
        EditText min = (EditText)findViewById(R.id.min);
      Spinner sp = (Spinner)findViewById(R.id.spinneR);

        rs=check_available();
        flag=false;


        for(int i=1;i<9;i++){
            try {
                rs.absolute(i);
                if(rs.getString(3)==null){
                    slot=i;
                    flag=true;
                    Toast.makeText(getApplicationContext(), "AT flag "+i , Toast.LENGTH_LONG).show();
                    break;
                }
                flag=false;
            }

            catch (Exception e){
                e.printStackTrace();
            }


        }
        LocalTime time = LocalTime.now();


        int hour = time.getHour();
        int minu = time.getMinute();
        hor=Integer.parseInt(String.valueOf(hr.getText()));
        minut =Integer.parseInt(String.valueOf(min.getText()));
        hour=hour+hor;
        minu=minu+minut ;


        int rem = minu/60;
        hour+=rem;
        hour=hour%24;
        minu=minu%60;
        String going_time = hour+" : "+minu;
        if(flag) {

            try {

                Statement forid = con.createStatement();
                ResultSet r = forid.executeQuery("SELECT id FROM registration WHERE Name='" + name + "'");
                r.absolute(1);
                String uid = r.getString(1);

                Statement stmt = con.createStatement();
                stmt.executeUpdate("UPDATE `slots` SET `time_arr` ='"+going_time+"',`user_id`='"+uid+"' WHERE id='"+slot+"'");
//                Toast.makeText(getApplicationContext(), "Hey man u got Slot " + slot, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Hey man No conn" + slot, Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Hey man No Slot left for u" + slot, Toast.LENGTH_LONG).show();

        }


        Intent intent=new Intent(duration.this,output.class);
        if(flag==false){
            intent.putExtra("slot","None");
        }
        else {
            intent.putExtra("slot",""+slot);
        }
        intent.putExtra("hr",""+hor);
        intent.putExtra("min",""+minut);
        startActivity(intent);


    }

    public ResultSet check_available(){
        try {

            Statement stmt = con.createStatement();
            rs=stmt.executeQuery("SELECT * FROM slots");
            return rs;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "At check available" , Toast.LENGTH_LONG).show();
        return null;



    }

}
