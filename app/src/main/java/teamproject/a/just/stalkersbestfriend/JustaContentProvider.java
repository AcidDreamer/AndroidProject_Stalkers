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
    DatabaseHelper myDBH;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "teamproject.a.just.stalkersbestfriend";
    static{
        matcher.addURI(AUTHORITY,"stalkers",1);         //return all
        matcher.addURI(AUTHORITY,"Stalkers/#",2);       //return by ID
    };

    @Override
    public boolean onCreate() {
        myDBH = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
       switch(matcher.match(uri)){
           case 1:
               System.out.println(myDBH.getReadableDatabase().query(DatabaseHelper.TABLE_NAME,null,null,null,null,null,null ));
               return myDBH.getReadableDatabase().query(DatabaseHelper.TABLE_NAME,null,null,null,null,null,null );

           case 2:
               String[] selArgs = new String[1];
               selArgs[0] = uri.getLastPathSegment();
               Cursor result = myDBH.getReadableDatabase().query(DatabaseHelper.TABLE_NAME,null,"_ID=?",selArgs,null,null,null);
               return result;
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
                long id = myDBH.getWritableDatabase().insertWithOnConflict(DatabaseHelper.TABLE_NAME,null,values,CONFLICT_IGNORE);
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
    /*
    private static final int stalkers_list = 1;
    private static final int stalker_id = 2 ;
    DatabaseHelper myDBH;
    static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static final Uri CONTENT_URI = Uri.parse(URL);

    static UriMatcher buildUriMatcher(){
        final String authority = "content://teamproject.a.just.stalkersbestfriend.JustaContentProvider";
        matcher.addURI(authority,"stalkers",stalkers_list);                                         //returns the whole table
        matcher.addURI(authority,"stalkers/#",stalker_id);                                          //returns date
        matcher.addURI(authority,"stalkers/#/#",stalker_id);                                        //returns latitude and longtitude
        return matcher;
    }

    @Override
    public boolean onCreate() {
        myDBH = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = matcher.match(uri);
        switch (match){
            case stalkers_list
                return "team"
            case stalker_id
        }
        return null;
    }
    */
}
