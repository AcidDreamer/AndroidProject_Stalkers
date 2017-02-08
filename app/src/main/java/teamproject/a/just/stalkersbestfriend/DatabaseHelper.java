package teamproject.a.just.stalkersbestfriend;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

/**
 * Created by Acid on 2/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "stalker.db";
    public static final String TABLE_NAME = "stalkers_table";
    public static final String col0 = "_id";
    public static final String col1 = "_userid";
    public static final String col2 = "_longitude";
    public static final String col3 = "_latitude";
    public static final String col4 = "_dt";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE " + TABLE_NAME +    "("+col0+" INTEGER PRIMARY KEY ," +
                                                                " "+col1+" TEXT ,"   +
                                                                " "+col2+" FLOAT ,"  +
                                                                " "+col3+" FLOAT ,"  +
                                                                " "+col4+" DATE "   + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void insertStalker(JSONArray stalkerList){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cV = new ContentValues();
        for(int i = 0 ; i<stalkerList.length(); i++){
            try {
                cV.put(col0,stalkerList.getJSONObject(i).getString("id"));
                cV.put(col1,stalkerList.getJSONObject(i).getString("userid"));
                cV.put(col2,stalkerList.getJSONObject(i).getString("longitude"));
                cV.put(col3,stalkerList.getJSONObject(i).getString("latitude"));
                cV.put(col4,stalkerList.getJSONObject(i).getString("dt"));
                db.insertWithOnConflict(TABLE_NAME, BaseColumns._ID,cV,CONFLICT_REPLACE);
            }catch(JSONException e){
                e.printStackTrace();
            }catch(SQLiteConstraintException e){
                Log.d("SQL:","Tupple was already there");
            }
        }
        db.close();
    }
}
