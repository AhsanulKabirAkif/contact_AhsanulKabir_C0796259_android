package com.example.contact_ahsanulkabir_c0796259_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "My contact Database";
    SQLiteDatabase sqLiteDatabase;
    EditText et_fName, et_lName, et_email, et_phoneNumber, et_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_fName = findViewById(R.id.et_firstName);
        et_lName = findViewById(R.id.et_lastName);
        et_email = findViewById(R.id.et_emailID);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        et_address = findViewById(R.id.et_address);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);



        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
       // String sql = "DROP TABLE IF EXISTS contact";
       // sqLiteDatabase.execSQL(sql);
        createTable();


    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS contact (" +
                "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                "first_Name VARCHAR(20) NOT NULL, " +
                "last_Name VARCHAR(20) NOT NULL, " +
                "email_ID VARCHAR (20) NOT NULL, " +
                "phone_Number VARCHAR (20) NOT NULL, " +
                "address VARCHAR(50) NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                addEmployee();
                break;
            case R.id.btn_list:
                startActivity(new Intent(this,ContactListActivity.class));
                break;
        }
    }

    private void addEmployee() {
        String firstName = et_fName.getText().toString().trim();
        String lastName = et_lName.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String phoneNumber = et_phoneNumber.getText().toString().trim();
        String address = et_address.getText().toString().trim();

        if (firstName.isEmpty()){
            et_fName.setError("First name cannot be empty");
            et_fName.requestFocus();
            return;
        }
        if (lastName.isEmpty()){
            et_lName.setError("Last name cannot be empty");
            et_lName.requestFocus();
            return;
        }
        if (email.isEmpty()){
            et_email.setError("Email ID cannot be empty");
            et_email.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()){
            et_phoneNumber.setError("Phone Number cannot be empty");
            et_phoneNumber.requestFocus();
            return;
        }
        if (address.isEmpty()){
            et_address.setError("Address cannot be empty");
            et_address.requestFocus();
            return;
        }

        String sql = "INSERT INTO contact (first_Name, last_Name, email_ID, phone_Number, address)" +
                "VALUES (?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(sql, new String[]{firstName,lastName,email,phoneNumber,address});
        clearFields();
    }

    private void clearFields() {
        et_fName.setText("");
        et_lName.setText("");
        et_email.setText("");
        et_phoneNumber.setText("");
        et_address.setText("");

        et_fName.clearFocus();
        et_lName.clearFocus();
        et_email.clearFocus();
        et_phoneNumber.clearFocus();
        et_address.clearFocus();

    }
}























