package pt.ipg.desportoescolar;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.database.Cursor;

/**
 * Created by Pedro on 31/06/2018.
 */

public class DbTableAtletas implements BaseColumns {
    public static final String TABLE_NAME = "atletas";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_AGE = "age";
    private static final String FIELD_COURSE = "course";

    public static final String[] ALL_COLUMNS = new String[]{_ID, FIELD_NAME, FIELD_AGE, FIELD_COURSE};

    private SQLiteDatabase db;

    public DbTableAtletas(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_NAME + " TEXT NOT NULL, " +
                        FIELD_AGE + " REAL, " +
                        FIELD_COURSE + " TEXT NOT NULL" +
                        ")"
        );
    }

    public static ContentValues getContentValues(Atletas atletas) {
        ContentValues values = new ContentValues();

        values.put(_ID, atletas.getId());
        values.put(FIELD_NAME, atletas.getName());
        values.put(FIELD_AGE, atletas.getAge());
        values.put(FIELD_COURSE, atletas.getCourse());

        return values;
    }

    public static Atletas getCurrentAtletaFromCursor(Cursor cursor) {
        final int posName = cursor.getColumnIndex(FIELD_NAME);
        final int posAge = cursor.getColumnIndex(FIELD_AGE);
        final int posCourse = cursor.getColumnIndex(FIELD_COURSE);

        Atletas atleta = new Atletas();

        atleta.setName(cursor.getString(posName));
        atleta.setAge(cursor.getInt(posAge));
        atleta.setCourse(cursor.getString(posCourse));

        return atleta;
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

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}
