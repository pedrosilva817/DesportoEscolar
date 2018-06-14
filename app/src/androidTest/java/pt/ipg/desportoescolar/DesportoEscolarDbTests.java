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
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class DesportoEscolarDbTests {
    @Before
    public void setUp() {
        getContext().deleteDatabase(DbDesportoEscolarOpenHelper.DATABASE_NAME);
    }

    @Test
    public void openDesportoEscolarDbTest() {
        // Context of the app under test.
        Context appContext = getContext();

        DbDesportoEscolarOpenHelper dbDesportoEscolarOpenHelper = new DbDesportoEscolarOpenHelper(appContext);

        SQLiteDatabase db = dbDesportoEscolarOpenHelper.getReadableDatabase();
        assertTrue("Could not open/create desporto escolar database", db.isOpen());
        db.close();
    }

    @Test
    public void desportosCRUDtest() {
        DbDesportoEscolarOpenHelper dbDesportoEscolarOpenHelper = new DbDesportoEscolarOpenHelper(getContext());

        SQLiteDatabase db = dbDesportoEscolarOpenHelper.getWritableDatabase();

        DbTableDesportos tableDesportos = new DbTableDesportos(db);

        Desporto desporto = new Desporto();
        desporto.setName("Futsal");

        // Insert/create (C)RUD
        long id = insertCategory(tableDesportos, desporto);

        // query/read C(R)UD
        desporto = ReadFirstDesporto(tableDesportos, "Futsal", id);

        // update CR(U)D
        desporto.setName("Futsal!");
        int rowsAffected = tableDesportos.update(
                DbTableDesportos.getContentValues(desporto),
                DbTableDesportos._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update category", 1, rowsAffected);

        // query/read C(R)UD
        desporto = ReadFirstDesporto(tableDesportos, "Futsal!", id);

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
    public void atletasCRUDtest() {
        DbDesportoEscolarOpenHelper dbDesportoEscolarOpenHelper = new DbDesportoEscolarOpenHelper(getContext());

        SQLiteDatabase db = dbDesportoEscolarOpenHelper.getWritableDatabase();

        DbTableDesportos tableDesportos = new DbTableDesportos(db);

        Desporto desporto = new Desporto();
        desporto.setName("Basketball");

        long idDeporto = insertDesporto(tableDesportos, desporto);

        DbTableAtletas = new DbTableAtletas(db);

        // Insert/create (C)RUD
        Atletas atletas = new Atletas();

        atletas.setName("Pedro");
        atletas.setAge(19);
        atletas.setCourse("Engenharia Informática");

        long id = tableAtletas.insert(
                DbTableAtletas.getContentValues(atletas)
        );
        assertNotEquals("Failed to insert atleta", -1, id);

        // query/read C(R)UD
        atletas = ReadFirstBook(tableAtletas, "Pedro Silva", 9.99, idCategory, id);

        // update CR(U)D
        atletas.setName("Pedro Silva");
        atletas.setAge(19);
        atletas.setCourse("Engenharia Informática");

        int rowsAffected = tableAtletas.update(
                DbTableAtletas.getContentValues(atletas),
                DbTableAtletas._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update atleta", 1, rowsAffected);

        // query/read C(R)UD
        atletas = ReadFirstBook(tableAtletas, "Pedro Silva", 19, id);

        // delete CRU(D)
        rowsAffected = tableAtletas.delete(
                DbTableAtletas._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to delete atleta", 1, rowsAffected);

        Cursor cursor = tableAtletas.query(DbTableAtletas.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Atletas found after delete ???", 0, cursor.getCount());
    }

    private Atletas ReadFirstBook(DbTableAtletas , String expectedName, double expectedAge, String expectedCourse, long expectedId) {
        Cursor cursor = tableAtletas.query(DbTableAtletas.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Failed to read atletas", 1, cursor.getCount());

        assertTrue("Failed to read the first atleta", cursor.moveToNext());

        Atletas atletas = DbTableAtletas.getCurrentBookFromCursor(cursor);

        assertEquals("Incorrect book title", expectedName, atletas.getName());
        assertEquals("Incorrect book price", expectedAge, atletas.getAge(), 0.001);
        assertEquals("Incorrect book category", expectedCourse, atletas.getCourse());
        assertEquals("Incorrect book id", expectedId, atletas.getId());

        return atletas;
    }

    private long insertCategory(DbTableDesportos , Desportos desportos) {
        long id = tableDesportos.insert(
                DbTableDesportos.getContentValues(desportos)
        );

        assertNotEquals("Failed to insert a desporto", -1, id);

        return id;
    }

    @NonNull
    private Desportos ReadFirstDesporto(DbTableDesportos , String expectedName, long expectedId) {
        Cursor cursor = tableDesportos.query(DbTableDesportos.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Failed to read desportos", 1, cursor.getCount());

        assertTrue("Failed to read the first desporto", cursor.moveToNext());

        Desportos desportos = DbTableDesportos.getCurrentCategoryFromCursor(cursor);
        assertEquals("Incorrect category name", expectedName, desportos.getName());
        assertEquals("Incorrect category id", expectedId, desportos.getId());

        return desportos;
    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}}