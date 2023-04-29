package com.example.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.SearchView;

import java.util.ArrayList;

public class contactsView extends AppCompatActivity {
    ArrayList<contact> contactsList = new ArrayList<>();
    ArrayList<String>  numberList2;
    adapter ad;
    SearchView s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_view);

        Intent i1 = getIntent();
        numberList2 = i1.getStringArrayListExtra("CONTACTS_TO_NUMBER_LIST");


        s = findViewById(R.id.search);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() >= 0) {
            while (cursor.moveToNext()) {
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String num = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1)).replaceAll(" ", "");
                contact con = new contact(num, displayName, false);
                if (!contactsList.contains(con)) {
                    contactsList.add(con);
                }
            }
            cursor.close();
        }
        contactsList.sort(contact.asc);
        ad = new adapter(contactsList);
        recyclerView.setAdapter(ad);

        s.clearFocus();
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                recyclerView.scrollToPosition(0);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        for (contact x:
             contactsList) {
            if (x.ask){
                if (x.num.length()==10){x.num="+91"+x.num;}
                if (!numberList2.contains(x.num)){numberList2.add(x.num);}
            }

        }
        Intent modNums = new Intent(this,NumbersActivity.class);
        modNums.putExtra("MODIFIED_WITH_CONTACTS",numberList2);
        setResult(RESULT_OK,modNums);
        finish();
    }


    private void filter(String newText) {
        ArrayList<contact> searchList = new ArrayList<>();
        for (contact item :
                contactsList) {
            if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                searchList.add(item);
            }
        }
        ad.search(searchList);
    }

}