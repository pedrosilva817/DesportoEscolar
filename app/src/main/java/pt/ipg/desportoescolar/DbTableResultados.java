package pt.ipg.desportoescolar;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.database.Cursor;

/**
 * Created by Pedro on 31/06/2018.
 */

public class DbTableResultados implements BaseColumns {
    public static final String TABLE_NAME = "resultados";
    private static final String FIELD_TYPE = "tipo";

    private SQLiteDatabase db;

    public DbTableResultados(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_TYPE + " TEXT NOT NULL" +
                        ")"
        );
    }

    public static ContentValues getContentValues(Resultados resultados) {
        ContentValues values = new ContentValues();

        values.put(_ID, resultados.getId());
        values.put(FIELD_TYPE, resultados.getName());

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
