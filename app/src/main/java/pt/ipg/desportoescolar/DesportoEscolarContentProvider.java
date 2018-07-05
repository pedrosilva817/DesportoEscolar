package pt.ipg.desportoescolar;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.BreakIterator;


public class DesportoEscolarContentProvider extends ContentProvider {

    private static final String AUTHORITY = "pt.ipg.desportoescolar";

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri ATLETAS_URI = Uri.withAppendedPath(BASE_URI, DbTableAtletas.TABLE_NAME);
    public static final Uri DESPORTOS_URI = Uri.withAppendedPath(BASE_URI, DbTableDesportos.TABLE_NAME);


    private static final int ATLETAS = 100;
    private static final int ATLETAS_ID = 101;
    private static final int DESPORTOS = 200;
    private static final int DESPORTOS_ID = 201;

    private static final String MULTIPLE_ITEMS = "vnd.android.cursor.dir";
    private static final String SINGLE_ITEM = "vnd.android.cursor.item";


    DbDesportoEscolarOpenHelper desportoEscolarOpenHelper;


    private static UriMatcher getDesportoEscolarUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, "atletas", ATLETAS);
        uriMatcher.addURI(AUTHORITY, "atletas/#", ATLETAS_ID);

        uriMatcher.addURI(AUTHORITY, "desportos", DESPORTOS);
        uriMatcher.addURI(AUTHORITY, "desportos/#", DESPORTOS_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        desportoEscolarOpenHelper = new DbDesportoEscolarOpenHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

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


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        UriMatcher matcher = getDesportoEscolarUriMatcher();

        switch (matcher.match(uri)) {
            case ATLETAS:
                return MULTIPLE_ITEMS + "/" + AUTHORITY + "/" + DbTableAtletas.TABLE_NAME;

            case DESPORTOS:
                return MULTIPLE_ITEMS + "/" + AUTHORITY + "/" + DbTableDesportos.TABLE_NAME;

            case ATLETAS_ID:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" + DbTableAtletas.TABLE_NAME;

            case DESPORTOS_ID:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" + DbTableDesportos.TABLE_NAME;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    private void notifyChanges(@NonNull Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = desportoEscolarOpenHelper.getWritableDatabase();

        UriMatcher matcher = getDesportoEscolarUriMatcher();

        String id = uri.getLastPathSegment();

        int rows = 0;

        switch (matcher.match(uri)) {
            case ATLETAS_ID:
                rows = new DbTableAtletas(db).delete(DbTableAtletas._ID + "=?", new String[]{id});
                break;

            case DESPORTOS_ID:
                rows = new DbTableDesportos(db).delete(DbTableDesportos._ID + "=?", new String[]{id});
                break;

            default:
                throw new UnsupportedOperationException("Invalid URI: " + uri);
        }

        if (rows > 0) notifyChanges(uri);

        return rows;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = desportoEscolarOpenHelper.getWritableDatabase();

        UriMatcher matcher = getDesportoEscolarUriMatcher();

        String id = uri.getLastPathSegment();

        int rows = 0;

        switch (matcher.match(uri)) {
            case ATLETAS_ID:
                rows = new DbTableAtletas(db).update(values, DbTableAtletas._ID + "=?", new String[]{id});
                break;
            case DESPORTOS_ID:
                rows = new DbTableDesportos(db).update(values, DbTableDesportos._ID + "=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Invalid URI: " + uri);
        }

        if (rows > 0) notifyChanges(uri);

        return rows;
    }
}