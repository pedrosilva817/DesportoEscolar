package pt.ipg.desportoescolar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbDesportoEscolarOpenHelper extends SQLiteOpenHelper {

    private static final boolean PRODUCTION = false;

    public static final String DATABASE_NAME = "desportoescolar.db";
    private static final int DATABASE_VERSION = 1;


    public DbDesportoEscolarOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
   public void onCreate(SQLiteDatabase db) {
               DbTableDesportos dbTableDesportos = new DbTableDesportos(db);
               dbTableDesportos.create();

               DbTableAtletas dbTableAtletas = new DbTableAtletas(db);
               dbTableAtletas.create();

        if (!PRODUCTION) {
            seed(db);
        }
    }
    private void seed(SQLiteDatabase db) {
        DbTableCategories dbTableCategories = new DbTableCategories(db);

        Category category = new Category();
        category.setName("Drama");
        int idCategoryDrama = (int) dbTableCategories.insert(DbTableCategories.getContentValues(category));

        category = new Category();
        category.setName("Romance");
        int idCategoryRomance = (int) dbTableCategories.insert(DbTableCategories.getContentValues(category));

        category = new Category();
        category.setName("Comedy");
        dbTableCategories.insert(DbTableCategories.getContentValues(category));

        DbTableBooks dbTableBooks = new DbTableBooks(db);

        Book book = new Book();
        book.setTitle("A midsummer night's dream");
        book.setIdCategory(idCategoryRomance);
        book.setPrice(9.99);
        dbTableBooks.insert(DbTableBooks.getContentValues(book));

        book = new Book();
        book.setTitle("Hamlet");
        book.setIdCategory(idCategoryDrama);
        book.setPrice(11.99);
        dbTableBooks.insert(DbTableBooks.getContentValues(book));

        book = new Book();
        book.setTitle("Macbeth");
        book.setIdCategory(idCategoryDrama);
        book.setPrice(5.99);
        dbTableBooks.insert(DbTableBooks.getContentValues(book));
    }
    
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}


