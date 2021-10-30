package com.example.pboyusuf;

import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnLogOut;
    FirebaseAuth mAuth;
    private EditText edtAngka1,tvProcess, tvProcess2;
    private EditText edtAngka2;
    private TextView textHasil;
    private TextView textTebak;
    private TextView edt_jawaban;
    private DatabaseReference mNoteRef;
//    Button btAdd;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private Button btnCek;
    private Button btAdd;
    int duration;
    AdView adBanner;

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


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
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
//        textHasil = findViewById(R.id.show_result);
//        EditText text1 = (EditText) findViewById(R.id.Edtangka1);
//        EditText text2 = (EditText) findViewById(R.id.Edtangka2);
//        EditText text3 = (EditText) findViewById(R.id.Edt_jawaban);
//        EditText text4 = (EditText) findViewById(R.id.Edt_jawaban);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        //Referensi database yang dituju
//        DatabaseReference myRef = database.getReference("Hasil").child(text4.getText().toString());
//
//
//        myRef.child("angka1").setValue(text1.getText().toString());
//        myRef.child("angka2").setValue(text2.getText().toString());
//        myRef.child("Nilai").setValue(text3.getText().toString());
        mNoteRef = FirebaseDatabase.getInstance().getReference("Hasil");
//        String key = mNoteRef.push().getKey();
        EditText text1 = (EditText) findViewById(R.id.Edtangka1);
        EditText text2 = (EditText) findViewById(R.id.Edtangka2);
        EditText text3 = (EditText) findViewById(R.id.Edt_jawaban);
        TextView text4 = (TextView) findViewById(R.id.show_result);
        TextView text5 = (TextView) findViewById(R.id.show_tebak);
        FirebaseDatabase myRef = FirebaseDatabase.getInstance();
        DatabaseReference mNoteRef = myRef.getReference("Hasil").child(text3.getText().toString());
//        mNoteRef.child("angka1").setValue(text1.getText().toString());
//        mNoteRef.child("angka2").setValue(text2.getText().toString());
        mNoteRef.child("Tebak").setValue(text3.getText().toString());
        mNoteRef.child("Jawab").setValue(text5.getText().toString());
        mNoteRef.child("Nilai").setValue(text4.getText().toString());

        Random myRandom = new Random();
        int num = myRandom.nextInt(101);//0-100
        int numa = myRandom.nextInt(101);//0-100
        tvProcess.setText(""+num);
        tvProcess2.setText(""+numa);
    }

    public void Selesai(View view) {
        Intent i = new Intent(MainActivity.this,HasilLista.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
//    public void create(String title, String content) {
//
//    }
}