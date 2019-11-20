package bd.edu.seu.project_login.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import bd.edu.seu.project_login.Regi_Details.Details;
import bd.edu.seu.project_login.ragi_vlaues.Values;

public class DbHelper extends SQLiteOpenHelper {

    private static final String db_name="student.db";
    private static final String table_name = "student_details";

    private static final int version = 1;
    private Context context;
    public DbHelper(@Nullable Context context) {
        super(context, db_name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+table_name+"("+Details.ID+" INTEGER,"+
                Details.fullname+" TEXT PRIMARY KEY,"+
                Details.username+" TEXT,"+
                Details.password+" TEXT,"+
                Details.email+" TEXT,"+
                Details.address+" TEXT);";
        try{
            Toast.makeText(context,"Oncreate is called",Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(sql);
        }catch (Exception e){
            Toast.makeText(context,"Exception:"+e,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP = Details.DROP_TABLE+table_name;
        sqLiteDatabase.execSQL(DROP);
        onCreate(sqLiteDatabase);
        Toast.makeText(context, "Dropped", Toast.LENGTH_SHORT).show();
    }

    public long addValues(Values values){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Details.fullname,values.getFullname());
        contentValues.put(Details.username,values.getUsername());
        contentValues.put(Details.password,values.getPassword());
        contentValues.put(Details.email,values.getEmail());
        contentValues.put(Details.address,values.getAddress());

        long res = db.insert(table_name,null,contentValues);
        db.close();
        return res;
    }

    public List<Values> getDetails(){
        List<Values> getAllDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM "+table_name;
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                Values values = new Values();
                values.setFullname(cursor.getString(1));
                values.setUsername(cursor.getString(2));
                values.setPassword(cursor.getString(3));
                values.setEmail(cursor.getString(4));
                values.setAddress(cursor.getString(5));
                getAllDetails.add(values);
            }while (cursor.moveToNext());
        }

        return getAllDetails;
    }

    public void delete(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(table_name,Details.username+"=?",new String[]{name});
        }catch (Exception e){
            Toast.makeText(context, "Error : "+e, Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
