package bd.edu.seu.project_login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bd.edu.seu.project_login.dbhelper.DbHelper;
import bd.edu.seu.project_login.ragi_vlaues.Values;

public class MainActivity extends AppCompatActivity {

    Button createButton ,login;
    private AlertDialog.Builder alertdialog;
    DbHelper myDbHelper;
    EditText logUsername,logPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
        setContentView(R.layout.activity_main);

        myDbHelper = new DbHelper(this);

        createButton = findViewById(R.id.CreateButtonId);
        login = findViewById(R.id.logInButton);
        logUsername = findViewById(R.id.logInUsernameId);
        logPass = findViewById(R.id.logInPassid);

        DbHelper db = new DbHelper(this);

        db.delete("");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog = new AlertDialog.Builder(MainActivity.this);
                alertdialog.setTitle(R.string.create_new);
                alertdialog.setMessage(R.string.create_new_acc);
                alertdialog.setIcon(R.drawable.alert);
                alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this,Registration.class);
                        startActivity(intent);
                    }
                });
                alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alert = alertdialog.create();
                alert.show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog = new AlertDialog.Builder(MainActivity.this);
                alertdialog.setTitle(R.string.login);
                alertdialog.setMessage(R.string.correct_username_password);
                alertdialog.setIcon(R.drawable.alert);
                alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbHelper db = new DbHelper(MainActivity.this);
                        List<Values> allDetails = new ArrayList<>();
                        allDetails = db.getDetails();

                        String username = null;
                        String password = null;
                        String usernameEdittext = null;
                        String passwordEdittext = null;
                        int count = 0;
                        for(Values values : allDetails){
                            username = values.getUsername();
                            password = values.getPassword();
                            usernameEdittext = logUsername.getText().toString();
                            passwordEdittext = logPass.getText().toString();

                            if(username.equals(usernameEdittext) && password.equals(passwordEdittext)) {
                                username = password = usernameEdittext = passwordEdittext = null;
                                Intent intent = new Intent(MainActivity.this,display.class);
                                startActivity(intent);
                                clearAll();
                                Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                count++;
                            }
                            else username = password = usernameEdittext = passwordEdittext = null;
                        }
                        if(count==0){
                            Toast.makeText(MainActivity.this,username+" "+password, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alert = alertdialog.create();
                alertdialog.show();
            }
        });
    }
    public void clearAll(){
        logUsername.setText("");
        logPass.setText("");
    }
}
