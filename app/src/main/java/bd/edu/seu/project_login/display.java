package bd.edu.seu.project_login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bd.edu.seu.project_login.dbhelper.DbHelper;
import bd.edu.seu.project_login.ragi_vlaues.Values;

public class display extends AppCompatActivity {
    TextView display;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        //display = findViewById(R.id.display_textView);
        listView = findViewById(R.id.displayListViewId);


        DbHelper db = new DbHelper(display.this);
        List<Values> allDetails = new ArrayList<>();
        allDetails = db.getDetails();

        String str ="";
        int count = 1;
        ArrayList<String> listViewAllDetails = new ArrayList<>();
        for(Values values: allDetails){
            str += count+" : "+values.getFullname()+"     "+values.getEmail()+"    "+values.getAddress()+"\n";
            listViewAllDetails.add(str);
            str = "";
            count++;
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listViewAllDetails);
        listView.setAdapter(arrayAdapter);

       // display.setText(str);
    }
}
