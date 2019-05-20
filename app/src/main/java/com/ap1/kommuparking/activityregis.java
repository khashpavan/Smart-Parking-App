package com.ap1.kommuparking;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class activityregis extends AppCompatActivity {



    Connection con;
    private final String url= "jdbc:mysql://85.10.205.173:3306/smartparking?verifyServerCertificate=false&useSSL=true";
    private final String user="smartkommu";
    private final String pass="pavanparking";
    public String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityregis);
        this.con=DbConnect();
    }
    public void Register(View view){

        EditText name = (EditText)findViewById(R.id.editText);
        EditText phone = (EditText)findViewById(R.id.editText2);
        EditText vehical_no = (EditText)findViewById(R.id.editText3);
        uname=String.valueOf(name.getText());

        String na=uname;
        String ph=String.valueOf(phone.getText());
        String vh=String.valueOf(vehical_no.getText());

        AddUser(na,ph,vh);


        Intent intent=new Intent(activityregis.this,duration.class);
        intent.putExtra("name",uname);
        startActivity(intent);
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

    public void AddUser(String name,String phone,String vehicle){
        try {

            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO `registration`(`Name`, `PhoneNo`, `NoPlate`) VALUES ('"+name+"','"+phone+"','"+vehicle+"')");

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
