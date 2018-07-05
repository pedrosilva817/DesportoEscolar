package pt.ipg.desportoescolar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAtleta extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public EditText editTexName;
    public EditText editTextAge;
    public Spinner spinnerDesportos;
    public Atletas atletas;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_atleta_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        atletas = new Atletas();
        editTexName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        spinnerDesportos = (Spinner) findViewById(R.id.spinnerDesportos);


    }


    public void cancel(View view) {
        finish();
    }

    public void save(View view) {




        atletas.setName(editTexName.getText().toString());
        atletas.setAge((int) Double.parseDouble(editTextAge.getText().toString()));
        atletas.setIdDesporto((int) spinnerDesportos.getSelectedItemId());

        int recordsAffected = getContentResolver().update(
                Uri.withAppendedPath(DesportoEscolarContentProvider.ATLETAS_URI, Integer.toString(atletas.getId())),
                DbTableAtletas.getContentValues(atletas),
                null,
                null
        );

        if (recordsAffected > 0) {
            Toast.makeText(this, "Atleta saved successfully", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Toast.makeText(this, "Could not save Atleta", Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
