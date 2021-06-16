package com.example.contact_ahsanulkabir_c0796259_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contact_ahsanulkabir_c0796259_android.model.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter {

    Context context;
    int layoutResource;
    List<Contact> contactList;
    SQLiteDatabase sqLiteDatabase;
    ListView lv;
    public ContactAdapter(@NonNull Context context, int resource, List<Contact> contacts, SQLiteDatabase sqLiteDatabase) {
        super(context, resource,contacts);
        this.context = context;
        this.layoutResource = resource;
        this.contactList = contacts;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if (v == null) v = inflater.inflate(layoutResource,null);
        TextView tv_name = v.findViewById(R.id.tv_name);
        TextView tv_email = v.findViewById(R.id.tv_emailID);
        TextView tv_phoneNumber = v.findViewById(R.id.tv_phoneNumber);
        TextView tv_address = v.findViewById(R.id.tv_address);

        Contact contact = contactList.get(position);
        tv_name.setText(contact.getFirstName() + " "+ contact.getLastName());
        tv_email.setText(contact.getEmailID());
        tv_phoneNumber.setText(contact.getPhoneNumber());
        tv_address.setText(contact.getAddress());

        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact(contact);
            }

            private void updateContact(Contact contact) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.update_contact_layout,null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                EditText firstName = view.findViewById(R.id.et_firstName);
                EditText lastName = view.findViewById(R.id.et_lastName);
                EditText emailID = view.findViewById(R.id.et_emailID);
                EditText phoneNumber = view.findViewById(R.id.et_phoneNumber);
                EditText address = view.findViewById(R.id.et_address);

                firstName.setText(contact.getFirstName());
                lastName.setText(contact.getLastName());
                emailID.setText(contact.getEmailID());
                phoneNumber.setText(contact.getPhoneNumber());
                address.setText(contact.getAddress());

                view.findViewById(R.id.btn_update).setOnClickListener(v1 -> {
                    String fName = firstName.getText().toString().trim();
                    String lName = lastName.getText().toString().trim();
                    String email = emailID.getText().toString().trim();
                    String phoneNumb = phoneNumber.getText().toString().trim();
                    String addressContact = address.getText().toString().trim();

                    if (fName.isEmpty()){
                        firstName.setError("First name cannot be empty");
                        firstName.requestFocus();
                        return;
                    }
                    if (lName.isEmpty()){
                        lastName.setError("Last name cannot be empty");
                        lastName.requestFocus();
                        return;
                    }
                    if (email.isEmpty()){
                        emailID.setError("Email ID cannot be empty");
                        emailID.requestFocus();
                        return;
                    }
                    if (phoneNumb.isEmpty()){
                        phoneNumber.setError("Phone Number cannot be empty");
                        phoneNumber.requestFocus();
                        return;
                    }
                    if (addressContact.isEmpty()){
                        address.setError("Address cannot be empty");
                        address.requestFocus();
                        return;
                    }
                    String sql = "UPDATE contact SET first_Name = ?, last_Name = ?, email_ID = ?, phone_Number = ?, address = ? WHERE id = ? ";

                    sqLiteDatabase.execSQL(sql, new String[]{fName,lName,email,phoneNumb,addressContact,String.valueOf(contact.getId())});

                    loadContact();
                    alertDialog.dismiss();

                });

            }
        });
        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(contact);
            }

            private void deleteContact(Contact contact) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "DELETE FROM contact WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new Integer[]{contact.getId()});

                        loadContact();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "The contact (" + contact.getFirstName()+" "+contact.getLastName() + ") is not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
/*
        lv = v.findViewById(R.id.lv_contact);
        lv.setOnItemClickListener((parent1, view, position1, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = convertView;
            view = layoutInflater.inflate(R.layout.mail_sms_call_layout,null);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

 */



        return v;

    }

    private void loadContact() {
        String sql = "SELECT * FROM contact";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        contactList.clear();
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
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contactList.size();
    }
}
