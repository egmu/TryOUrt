package com.example.pboyusuf;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnLogOut;
    FirebaseAuth mAuth;
    DatabaseHelper myDb;

    private EditText edtAngka1,tvProcess, tvProcess2;
    private EditText edtAngka2;
    private TextView textHasil;
    private TextView textTebak, editName;
    private TextView edt_jawaban;
    private DatabaseReference mNoteRef;
    //    Button btAdd;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private Button btnCek;
    private Button btAdd;
    int duration;
    AdView adBanner;
    Button btnAddData;
    Button btnViewAll;
    AdRequest adRequest;
    String toast;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtAngka1 = findViewById(R.id.Edtangka1);
        edtAngka2 = findViewById(R.id.Edtangka2);
        textHasil = findViewById(R.id.show_result);
        edt_jawaban = findViewById(R.id.Edt_jawaban);
        btnCek = findViewById(R.id.button_result);
        textTebak = findViewById(R.id.show_tebak);
        btAdd = findViewById(R.id.btnLanjut);
        mAuth = FirebaseAuth.getInstance();
        myDb = new DatabaseHelper(this);
        editName = (TextView) findViewById(R.id.show_tebak);
        btnAddData = (Button)findViewById(R.id.btnLanjut);
        btnViewAll = (Button)findViewById(R.id.btnSelesai);
        AddData();

        viewAll();



        btnCek.setOnClickListener(this);
        Button tombol = (Button) findViewById(R.id.menampilkanToast);


        tombol.setOnClickListener(this);
        //Create random nomer
        tvProcess = findViewById(R.id.Edtangka1);
        tvProcess2 = findViewById(R.id.Edtangka2);
        //Create random
        Random myRandom = new Random();
        int num = myRandom.nextInt(101);//0-100
        int numa = myRandom.nextInt(101);//0-100
        tvProcess.setText(""+num);
        tvProcess2.setText(""+numa);

        AdView bannerAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAdview.loadAd(adRequest);



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
    public void AddData() {

        btnAddData.setOnClickListener(

                new View.OnClickListener() {

                    @Override

                    public void onClick(View v) {

                        boolean isInserted = myDb.insertData(editName.getText().toString()

                               );

                        if(isInserted == true)

                            Toast.makeText(MainActivity.this,"Lanjut",Toast.LENGTH_LONG).show();

                        else

                            Toast.makeText(MainActivity.this,"Data Not Iserted",Toast.LENGTH_LONG).show();

                    }

                }

        );

    }



    //fungsi menampilkan data

    public void viewAll() {

        btnViewAll.setOnClickListener(

                new View.OnClickListener(){

                    @Override

                    public void onClick(View v) {

                        Cursor res = myDb.getAllData();

                        if(res.getCount() == 0) {

                            // show message

                            showMessage("Error","Noting Found");

                            return;

                        }



                        StringBuffer buffer = new StringBuffer();

                        while (res.moveToNext() ) {

                            buffer.append("Id :"+ res.getString(0)+"\n");

                            buffer.append("JAWAB :"+ res.getString(1)+"\n");



                        }


                        Random myRandom = new Random();
                        int num = myRandom.nextInt(101);//0-100
                        int numa = myRandom.nextInt(101);//0-100
                        tvProcess.setText(""+num);
                        tvProcess2.setText(""+numa);
                        // show all data

                        showMessage("Data",buffer.toString());

                    }

                }

        );

    }
    public void showMessage(String title, String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);

        builder.setTitle(title);

        builder.setMessage(Message);

        builder.show();

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_result) {
            String InputAngka1 = edtAngka1.getText().toString().trim();
            String InputAngka2 = edtAngka2.getText().toString().trim();

            String InputAngka3 = edt_jawaban.getText().toString().trim();
            boolean isEmpytyFields = false;
            boolean isInvalidDouble = false;

            if (TextUtils.isEmpty(InputAngka1)) {
                isEmpytyFields = true;
                edtAngka1.setError("Angka Tidak Boleh Kosong");
            }
            if (TextUtils.isEmpty(InputAngka2)) {
                isEmpytyFields = true;
                edtAngka2.setError("Angka Tidak Boleh Kosong");
            }
            if (TextUtils.isEmpty(InputAngka3)) {
                isEmpytyFields = true;
                edtAngka2.setError("Angka Tidak Boleh Kosong");
            }

            Double Opetand1 = toDouble(InputAngka1);
            Double Opetand2 = toDouble(InputAngka2);
            Double Opetand3 = toDouble(InputAngka3);

            if (Opetand1 == null) {
                isInvalidDouble = true;
                edtAngka1.setError("Angka Tidak Boleh Kosong");
            }
            if (Opetand2 == null) {
                isInvalidDouble = true;
                edtAngka2.setError("Angka Tidak Boleh Kosong");
            }
            if (Opetand3 == null) {
                edtAngka2.setError("Jawaban Tidak Boleh Kosong");
            }
            double hasil;
            TextView dia;
            if (!isEmpytyFields && !isInvalidDouble) {
                hasil = Opetand2 + Opetand1;
//                textHasil.setText(String.valueOf(hasil));
                if (Opetand3 == hasil){
                    textTebak.setText("Jawaban anda benar");
                } else {
                    textTebak.setText("Jawaban anda salah");
                }
            }

        }
        if (v.getId()==R.id.menampilkanToast){
            String InputAngka1 = edtAngka1.getText().toString().trim();
            String InputAngka2 = edtAngka2.getText().toString().trim();
            String InputAngka3 = edt_jawaban.getText().toString().trim();
            TextView dia;
            Double Opetand1 = toDouble(InputAngka1);
            Double Opetand2 = toDouble(InputAngka2);
            Double Opetand3 = toDouble(InputAngka3);
            double hasil;
            hasil = Opetand2 + Opetand1;
            textHasil.setText(String.valueOf(hasil));


        }
    }

    private Double toDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e){
            return  null;
        }
    }
    //Membuat menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu,menu);
        return true;
    }

    //Membuat pilihan menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.tentang)
        {
            startActivity(new Intent(MainActivity.this, HelpActivity.class));
        }
        if (item.getItemId()==R.id.pengaturan)
        {
            startActivity(new Intent(MainActivity.this, About.class));
        }
        if (item.getItemId()==R.id.logout)
        {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        return true;
    }


    public void Click1(View view) {

        Random myRandom = new Random();
        int num = myRandom.nextInt(101);//0-100
        int numa = myRandom.nextInt(101);//0-100
        tvProcess.setText(""+num);
        tvProcess2.setText(""+numa);
    }



}