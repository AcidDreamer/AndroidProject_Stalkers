package teamproject.a.just.stalkersbestfriend;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE;

/**
 * Created by Acid on 2/7/2017.
 */

public class JustaContentProvider extends ContentProvider {
    DatabaseHelper myDBH;                                                                           //instantiate our DatabaseHelper

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);                  //initialize URI matcher
    private static final String AUTHORITY = "teamproject.a.just.stalkersbestfriend";                //set the authority
    static{                                                                                         //the URIS supported
        matcher.addURI(AUTHORITY,"stalkers",1);         //return all ,allow queries bellow
    };
    @Override
    public boolean onCreate() {                                                                     //onCreate instantiate our database helper
        myDBH = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch(matcher.match(uri)){
           case 1:
               return myDBH.getReadableDatabase().query(DatabaseHelper.TABLE_NAME,projection,selection,selectionArgs,null,null,null );  //if it matches the first Uri ,return the whole database
            default:
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (matcher.match(uri)){
            case 1:
                long id = myDBH.getWritableDatabase().insertWithOnConflict(DatabaseHelper.TABLE_NAME,null,values,CONFLICT_IGNORE);  //insert into the db with ON conflict set to ignore
                return Uri.parse(uri.toString()+"/"+id);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
