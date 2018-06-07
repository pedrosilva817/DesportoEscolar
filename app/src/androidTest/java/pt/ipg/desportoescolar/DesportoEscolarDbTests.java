package pt.ipg.desportoescolar;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
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
            public void setUp() {
                getContext().deleteDatabase(DbDesportoEscolarOpenHelper.DATABASE_NAME);
            }

    @Test
        public void openDesportEscolarDbTest() {
            // Context of the app under test.Context appContext = getContext();
            DbDesportoEscolarOpenHelper DbDesportoEscolarOpenHelper = new DbDesportoEscolarOpenHelper(appContext);

            SQLiteDatabase db = DbDesportoEscolarOpenHelper.getReadableDatabase();
        assertTrue("Could not open/create desporto escolar database", db.isOpen());
        db.close();
    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}