package pt.ipg.desportoescolar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Pedro on 31/05/2018.
 */

public class DbTableDesportos implements BaseColumns {

    public static final String TABLE_NAME = "desportos";
    private static final String FIELD_NAME = "nome";
    private static final String FIELD_DATE = "data";

    private SQLiteDatabase db;

    public DbTableDesportos(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_NAME + " TEXT NOT NULL"+
                        ")"
        );
    }

    public static ContentValues getContentValues(Desportos desportos) {
        ContentValues values = new ContentValues();

        values.put(_ID, desportos.getId());
        values.put(FIELD_NAME, desportos.getName());
        /*
        *
        DATA
        *
        */

        return values;
    }

    public long insert(ContentValues values) {
        return db.insert(TABLE_NAME, null, values);
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }
    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}
