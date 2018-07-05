package pt.ipg.desportoescolar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DesportoEscolarDbTests {
    @Before
    public void setUp() {
        getContext().deleteDatabase(DbDesportoEscolarOpenHelper.DATABASE_NAME);
    }

    @Test
    public void openCinemaDbTest() {
        // Context of the app under test.
        Context appContext = getContext();

        DbDesportoEscolarOpenHelper dbDesportoEscolarOpenHelper = new DbDesportoEscolarOpenHelper(appContext);

        SQLiteDatabase db = dbDesportoEscolarOpenHelper.getReadableDatabase();
        assertTrue("Could not open/create DesportoEscolar database", db.isOpen());
        db.close();
    }

    @Test
    public void desportosCRUDtest() {
        DbDesportoEscolarOpenHelper dbDesportoEscolarOpenHelper = new DbDesportoEscolarOpenHelper(getContext());

        SQLiteDatabase db = dbDesportoEscolarOpenHelper.getWritableDatabase();

        DbTableDesportos tableDesportos = new DbTableDesportos(db);

        Desportos desporto = new Desportos();
        desporto.setName("Futsal");

        // Insert/create (C)RUD
        long id = insertDesportos(tableDesportos, desporto);

        // query/read C(R)UD
        desporto = ReadFirstDesporto(tableDesportos, "Futsal", id);

        // update CR(U)D
        desporto.setName("Futsal ");
        int rowsAffected = tableDesportos.update(
                DbTableDesportos.getContentValues(desporto),
                DbTableDesportos._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update desporto", 5, rowsAffected);

        // query/read C(R)UD
        desporto = ReadFirstDesporto(tableDesportos, "Futsal ", id);

        // delete CRU(D)
        rowsAffected = tableDesportos.delete(
                DbTableDesportos._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to delete desporto", 1, rowsAffected);

        Cursor cursor = tableDesportos.query(DbTableDesportos.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Desportos found after delete ???", 0, cursor.getCount());
    }

    @Test
    public void cinemasCRUDtest() {
        DbDesportoEscolarOpenHelper dbDesportoEscolarOpenHelper = new DbDesportoEscolarOpenHelper(getContext());

        SQLiteDatabase db = dbDesportoEscolarOpenHelper.getWritableDatabase();

        DbTableDesportos tableDesportos = new DbTableDesportos(db);

        Desportos desporto = new Desportos();
        desporto.setName("Andebol");

        long idDesporto = insertDesportos(tableDesportos, desporto);

        DbTableAtletas tableAtletas = new DbTableAtletas(db);

        // Insert/create (C)RUD
        Atletas atletas = new Atletas();

        atletas.setName("Pedro Silva");
        atletas.setAge(19);
        atletas.setIdDesporto((int) idDesporto);

        long id = tableAtletas.insert(
                DbTableAtletas.getContentValues(atletas)
        );
        assertNotEquals("Failed to insert Atleta", -1, id);

        // query/read C(R)UD
        atletas = ReadFirstAtleta(tableAtletas, "Pedro Silva", 19, idDesporto, id);

        // update CR(U)D
        atletas.setName("Pedro Silva");
        atletas.setAge(20);

        int rowsAffected = tableAtletas.update(
                DbTableAtletas.getContentValues(atletas),
                DbTableAtletas._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update Atleta", 1, rowsAffected);

        // query/read C(R)UD
        atletas = ReadFirstAtleta(tableAtletas, "Pedro Silva", 18, idDesporto, id);

        // delete CRU(D)
        rowsAffected = tableAtletas.delete(
                DbTableAtletas._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to delete Atleta", 1, rowsAffected);

        Cursor cursor = tableAtletas.query(DbTableAtletas.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Atletas found after delete ???", 0, cursor.getCount());
    }

    private Atletas ReadFirstAtleta(DbTableAtletas tableFilmes, String expectedName, int expectedAge, long expectedDesportoId, long expectedId) {
        Cursor cursor = tableFilmes.query(DbTableAtletas.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Failed to read Atleta", 4, cursor.getCount());

        assertTrue("Failed to read the first Atleta", cursor.moveToNext());

        Atletas atletas = DbTableAtletas.getCurrentAtletaFromCursor(cursor);

        assertEquals("Incorrect Atleta name", expectedName, atletas.getName());
        assertEquals("Incorrect Atleta age", expectedAge, atletas.getAge(), 0);
        assertEquals("Incorrect Atleta desporto", expectedDesportoId, atletas.getIdDesporto());
        assertEquals("Incorrect Atleta id", expectedId, atletas.getId());

        return atletas;
    }

    private long insertDesportos(DbTableDesportos tableDesportos, Desportos desporto) {
        long id = tableDesportos.insert(
                DbTableDesportos.getContentValues(desporto)
        );

        assertNotEquals("Failed to insert a desporto", -1, id);

        return id;
    }

    @NonNull
    private Desportos ReadFirstDesporto(DbTableDesportos tableDesportos, String expectedName, long expectedId) {
        Cursor cursor = tableDesportos.query(DbTableDesportos.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Failed to read Desportos", 1, cursor.getCount());

        assertTrue("Failed to read the first desporto", cursor.moveToNext());

        Desportos desporto = DbTableDesportos.getCurrentDesportoFromCursor(cursor);
        assertEquals("Incorrect desporto name", expectedName, desporto.getName());
        assertEquals("Incorrect desporto id", expectedId, desporto.getId());

        return desporto;
    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}
