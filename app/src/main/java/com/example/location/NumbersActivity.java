package com.example.location;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class NumbersActivity extends AppCompatActivity {
    EditText Nums ;
    TextView display;
    ArrayList<String> numberList1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        Intent intent = getIntent();
        numberList1 = intent.getStringArrayListExtra("CURRENT_NUMBER_LIST");
        //numberList1.add("100");
        System.out.println(numberList1);
    }
    public void addFromContacts(View view){
        Intent forCons = new Intent(this,contactsView.class);
        forCons.putExtra("CONTACTS_TO_NUMBER_LIST",numberList1);
        startActivityForResult(forCons,1234);
    }
    @Override
    public void onBackPressed(){
        Intent intent1 = new Intent(this,MainActivity.class);
        intent1.putExtra("MODIFIED_NUMBER_LIST",numberList1);
        setResult(RESULT_OK,intent1);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234 && resultCode == RESULT_OK){
            if (data!=null) {
                numberList1 = data.getStringArrayListExtra("MODIFIED_WITH_CONTACTS");
            }
        }
    }
//    public void gotoContacts(View view){
//        Intent intent2 = new Intent(this,contactsView.class);
//        startActivity(intent2);
//    }
    public void addNum(View view){
        Nums = findViewById(R.id.ev1);
        String newNum = String.valueOf(Nums.getText());
        if ((newNum.length()!=10) || numberList1.contains("+91"+newNum)){
            return;
        }
        numberList1.add("+91"+newNum);
        Nums.setText("");
        //System.out.println(numberList1.toString());
    }
    public void remNum(View view){
        Nums = findViewById(R.id.ev1);
        String newNum = String.valueOf(Nums.getText());
        numberList1.removeAll(Collections.singleton("+91"+newNum));
        Nums.setText("");
        System.out.println(numberList1.toString());
    }
    public void displayNumbers(View view){
        display = findViewById(R.id.ShowNumbers);
        display.setText("");
        display.append("--Available numbers--");
        for (String x:
             numberList1) {
            display.append("  \n"+x);
        }
        //System.out.println(numberList1);

    }
}