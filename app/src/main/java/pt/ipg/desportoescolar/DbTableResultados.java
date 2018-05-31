package pt.ipg.desportoescolar;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Pedro on 31/05/2018.
 */

public class DbTableResultados implements BaseColumns{

    public static final String TABLE_NAME = "resultado";
    private static final String FIELD_RESULT = "score";

    private SQLiteDatabase db;

    public DbTableResultados(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_RESULT + " TEXT NOT NULL" +
                        ")"
        );
    }

    public static ContentValues getContentValues(Resultados resultado) {
        ContentValues values = new ContentValues();

        values.put(_ID, resultado.getId());
        values.put(FIELD_RESULT, resultado.getName());

        return values;
    }

}
