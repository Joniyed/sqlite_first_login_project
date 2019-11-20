package bd.edu.seu.project_login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bd.edu.seu.project_login.dbhelper.DbHelper;
import bd.edu.seu.project_login.ragi_vlaues.Values;

public class Registration extends AppCompatActivity {


    Button regButton,deleteButton;
    AlertDialog.Builder alertDialog;
    EditText fullname,username,password,email,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        regButton = findViewById(R.id.regButtonId);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });
        fullname = findViewById(R.id.fullnameId);
        username = findViewById(R.id.reg_userNameId);
        password = findViewById(R.id.reg_pass_id);
        email = findViewById(R.id.reg_emailid);
        address = findViewById(R.id.reg_addressId);

        deleteButton = findViewById(R.id.deleteButtonId);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButton();
            }
        });

    }

    public void showAlert(){
        alertDialog = new AlertDialog.Builder(Registration.this);
        alertDialog.setTitle(R.string.save_confirm);
        alertDialog.setIcon(R.drawable.alert);
        alertDialog.setMessage(R.string.correct_username_password);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DbHelper db = new DbHelper(Registration.this);
                Values values = new Values();
                values.setFullname(fullname.getText().toString());
                values.setUsername(username.getText().toString());
                values.setPassword(password.getText().toString());
                values.setEmail(email.getText().toString());
                values.setAddress(address.getText().toString());

                long res = db.addValues(values);

                Toast.makeText(Registration.this, "Result: "+res, Toast.LENGTH_SHORT).show();
                if(res>0){
                    onBackPressed();
                }
                else{
                    Toast.makeText(Registration.this, "Not successfully saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    public void deleteButton(){
        alertDialog = new AlertDialog.Builder(Registration.this);
        alertDialog.setTitle(R.string.deleteTitle);
        alertDialog.setIcon(R.drawable.alert);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbHelper db = new DbHelper(Registration.this);
                if(!username.getText().toString().isEmpty()) {
                    db.delete(username.getText().toString());
                    Toast.makeText(Registration.this, username.getText()+"  is Deleted", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                else{
                    Toast.makeText(Registration.this, "Enter a username", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }
}
