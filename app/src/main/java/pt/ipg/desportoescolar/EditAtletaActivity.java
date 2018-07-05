package pt.ipg.desportoescolar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class EditAtletaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DESPORTOS_CURSOR_LOADER_ID = 0;

    private EditText editTextName;
    private EditText editTextAge;
    private Spinner spinnerDesporto;
    private Atletas atletas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_atleta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        int desportoescolarId = intent.getIntExtra(MainActivity.DESPORTOESCOLAR_ID, -1);

        if (desportoescolarId == -1) {
            finish();
            return;
        }

        Cursor cursorDesportoEscolar = getContentResolver().query(
                Uri.withAppendedPath(DesportoEscolarContentProvider.ATLETAS_URI, Integer.toString(desportoescolarId)),
                DbTableAtletas.ALL_COLUMNS,
                null,
                null,
                null
        );

        if (!cursorDesportoEscolar.moveToNext()) {
            Toast.makeText(this, "Atleta not found", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        spinnerDesporto = (Spinner) findViewById(R.id.spinnerDesporto);

        atletas = DbTableAtletas.getCurrentAtletaFromCursor(cursorDesportoEscolar);

        editTextName.setText(atletas.getName());
        editTextAge.setText(String.format("%.2f",atletas.getAge()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(DESPORTOS_CURSOR_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(DESPORTOS_CURSOR_LOADER_ID, null, this);
    }

    public void cancel(View view) {
        finish();
    }

    public void save(View view) {
        // todo: validations

        atletas.setName(editTextName.getText().toString());
        atletas.setAge((int) Double.parseDouble(editTextAge.getText().toString()));
        atletas.setIdDesporto((int) spinnerDesporto.getSelectedItemId());

        int recordsAffected = getContentResolver().update(
                Uri.withAppendedPath(DesportoEscolarContentProvider.ATLETAS_URI, Integer.toString(atletas.getId())),
                DbTableAtletas.getContentValues(atletas),
                null,
                null
        );

        if (recordsAffected > 0) {
            Toast.makeText(this, "Movie saved successfully", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Toast.makeText(this, "Could not save Movie", Toast.LENGTH_LONG).show();
    }

    /*
    void delete(){

        String where = DbTableFilmes._ID+" = "+filmes.getId();
        int i = filmes.delete(CinemaContentProvider.FILMES_URI,where,null);
        if(i>0){
            Toast.makeText(this,filmes.getTitle()+ " deleted from DB "+i,Toast.LENGTH_LONG).show();

        }
*/


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == DESPORTOS_CURSOR_LOADER_ID) {
            return new CursorLoader(this, DesportoEscolarContentProvider.DESPORTOS_URI, DbTableDesportos.ALL_COLUMNS, null, null, null);
        }

        return null;
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        SimpleCursorAdapter cursorAdapterCategories = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{DbTableDesportos.FIELD_NAME},
                new int[]{android.R.id.text1}
        );

        spinnerDesporto.setAdapter(cursorAdapterCategories);

        int idCategory = atletas.getIdDesporto();

        for (int i = 0; i < spinnerDesporto.getCount(); i++) {
            Cursor cursor = (Cursor) spinnerDesporto.getItemAtPosition(i);

            final int posId = cursor.getColumnIndex(DbTableDesportos._ID);

            if (idCategory == cursor.getInt(posId)) {
                spinnerDesporto.setSelection(i);
                break;
            }
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
