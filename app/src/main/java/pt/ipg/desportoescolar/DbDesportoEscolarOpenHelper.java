package pt.ipg.desportoescolar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pedro on 31/05/2018.
 */

public class DbDesportoEscolarOpenHelper {

    private static final String DATABASE_NAME = "desportoescolar.db";
    private static final int DATABASE_VERSION = 1;


    public DbDesportoEscolarOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
