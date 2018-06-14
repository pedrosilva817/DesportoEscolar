package pt.ipg.desportoescolar;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Pedro on 14/06/2018.
 */
public class DesportoEscolarContentProvider extends ContentProvider {
    private static final int ATLETAS = 100;
    private static final int ATLETAS_ID = 101;
    private static final int DESPORTOS = 200;
    private static final int DESPORTOS_ID = 201;

    DbDesportoEscolarOpenHelper desportoEscolarOpenHelper;


    private static UriMatcher getDesportoEscolarUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI("pt.ipg.desportoescolar", "atletas", ATLETAS);
        uriMatcher.addURI("pt.ipg.desportoescolar", "atletas/#", ATLETAS_ID);

        uriMatcher.addURI("pt.ipg.desportoescolar", "desportos", DESPORTOS);
        uriMatcher.addURI("pt.ipg.desportoescolar", "desportos/#", DESPORTOS_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        return false;
        desportoEscolarOpenHelper = new DbDesportoEscolarOpenHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;

        SQLiteDatabase db = desportoEscolarOpenHelper.getReadableDatabase();

        String id = uri.getLastPathSegment();

        UriMatcher matcher = getDesportoEscolarUriMatcher();

        switch (matcher.match(uri)) {
            case ATLETAS:
                return new DbTableAtletas(db).query(projection, selection, selectionArgs, null, null, sortOrder);

            case DESPORTOS:
                return new DbTableDesportos(db).query(projection, selection, selectionArgs, null, null, sortOrder);

            case ATLETAS_ID:
                return new DbTableAtletas(db).query(projection, DbTableAtletas._ID + "=?", new String[] { id }, null, null, null);

            case DESPORTOS_ID:
                return new DbTableDesportos(db).query(projection, DbTableDesportos._ID + "=?", new String[] { id }, null, null, null);

            default:
                throw new UnsupportedOperationException("Invalid URI: " + uri);
        }
    }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}