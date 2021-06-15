package com.example.contact_ahsanulkabir_c0796259_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.contact_ahsanulkabir_c0796259_android.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    ListView contactListView;
    List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        contactListView = findViewById(R.id.lv_contact);
        contactList = new ArrayList<>();
        sqLiteDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME,MODE_PRIVATE,null);

        loadContact();
    }

    private void loadContact() {
        String sql = "SELECT * FROM contact";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                // getting and adding contacts to the list
                contactList.add(new Contact(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        ContactAdapter contactAdapter = new ContactAdapter(this,R.layout.list_contact,contactList,sqLiteDatabase);
        contactListView.setAdapter(contactAdapter);
    }
}