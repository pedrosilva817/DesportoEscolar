package pt.ipg.desportoescolar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbDesportoEscolarOpenHelper extends SQLiteOpenHelper {
    private static final boolean PRODUCTION = false;

    public static final String DATABASE_NAME = "desportoescolar.db";
    private static final int DATABASE_VERSION = 2;


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
        DbTableDesportos dbTableDesportos = new DbTableDesportos(db);

        Desportos desportos = new Desportos();
        desportos.setName("Futsal");
        int idDesportoFutsal = (int) dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));

        desportos = new Desportos();
        desportos.setName("Andebol");
        int idDesportoAndebol = (int) dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));

        desportos = new Desportos();
        desportos.setName("Atletismo");
        dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));
        int idDesportoAtletismo = (int) dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));

        desportos = new Desportos();
        desportos.setName("Basketball");
        int idDesportoBasketball = (int) dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));

        desportos = new Desportos();
        desportos.setName("Futebol");
        dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));
        int idDesportoFutebol = (int) dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));
        desportos = new Desportos();
        desportos.setName("Voleibol");
        int idDesportoVoleibol = (int) dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));

        desportos = new Desportos();
        desportos.setName("Natação");
        dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));
        int idDesportoNatacao = (int) dbTableDesportos.insert(DbTableDesportos.getContentValues(desportos));


        DbTableAtletas dbTableAtletas = new DbTableAtletas(db);

        Atletas atleta = new Atletas();
        atleta.setName("Pedro Silva");
        atleta.setIdDesporto(idDesportoFutsal);
        atleta.setAge(19);
        dbTableAtletas.insert(DbTableAtletas.getContentValues(atleta));

        atleta = new Atletas();
        atleta.setName("Daniela Felix");
        atleta.setIdDesporto(idDesportoAtletismo);
        atleta.setAge(18);
        dbTableAtletas.insert(DbTableAtletas.getContentValues(atleta));

        atleta = new Atletas();
        atleta.setName("Cátia Oliveira");
        atleta.setIdDesporto(idDesportoAndebol);
        atleta.setAge(20);
        dbTableAtletas.insert(DbTableAtletas.getContentValues(atleta));

        atleta = new Atletas();
        atleta.setName("Patricia Oliveira");
        atleta.setIdDesporto(idDesportoBasketball);
        atleta.setAge(21);
        dbTableAtletas.insert(DbTableAtletas.getContentValues(atleta));

        atleta = new Atletas();
        atleta.setName("Ana Silva");
        atleta.setIdDesporto(idDesportoFutebol);
        atleta.setAge(19);
        dbTableAtletas.insert(DbTableAtletas.getContentValues(atleta));

        atleta = new Atletas();
        atleta.setName("Cabo Felix");
        atleta.setIdDesporto(idDesportoVoleibol);
        atleta.setAge(26);
        dbTableAtletas.insert(DbTableAtletas.getContentValues(atleta));

        atleta = new Atletas();
        atleta.setName("Rui Oliveira");
        atleta.setIdDesporto(idDesportoNatacao);
        atleta.setAge(23);
        dbTableAtletas.insert(DbTableAtletas.getContentValues(atleta));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
