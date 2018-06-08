package pt.ipg.desportoescolar;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.database.Cursor;

/**
 * Created by Pedro on 31/06/2018.
 */

public class DbTableAtletas implements BaseColumns {
    public static final String TABLE_NAME = "jogadores";
    private static final String FIELD_NAME = "tipo";
    private static final String FIELD_AGE = "idade";
    private static final String FIELD_COURSE = "curso";

    public static final String [] ALL_COLUMNS = new String[] { _ID, FIELD_NAME, FIELD_AGE, FIELD_COURSE };

    private SQLiteDatabase db;

    public DbTableAtletas(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_NAME + " TEXT NOT NULL, " +
                        FIELD_AGE + " REAL " +
                        FIELD_COURSE + " TEXT NOT NULL" +
                        ")"
        );
    }

    public static ContentValues getContentValues(Atleta atleta) {
        ContentValues values = new ContentValues();

        values.put(_ID, atleta.getId());
        values.put(FIELD_NAME, atleta.getName());
        values.put(FIELD_AGE, atleta.getAge());
        values.put(FIELD_COURSE, atleta.getCourse());

        return values;
    }

    public long insert(ContentValues values){
        return db.insert(TABLE_NAME, null, values);
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs){
        return db.update(TABLE_NAME, values, whereClause,whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}