package pt.ipg.desportoescolar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int DESPORTOESCOLAR_CURSOR_LOADER_ID = 0;
    public static final String DESPORTOESCOLAR_ID = "DESPORTOESCOLAR_ID";

    private DesportoEscolarCursorAdapter atletasCursorAdapter;
    private RecyclerView recyclerViewAtletas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewAtletas = (RecyclerView) findViewById(R.id.recyclerViewAtletas);

        recyclerViewAtletas.setLayoutManager(new LinearLayoutManager(this));
        atletasCursorAdapter = new DesportoEscolarCursorAdapter(this);
        recyclerViewAtletas.setAdapter(atletasCursorAdapter);

        atletasCursorAdapter.setViewHolderClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAtleta();
            }
        });

        getSupportLoaderManager().initLoader(DESPORTOESCOLAR_CURSOR_LOADER_ID, null, this);
    }

    private void editAtleta() {
        int id = atletasCursorAdapter.getlastAtletaClicked();

        Intent intent = new Intent(this, EditAtletaActivity.class);

        intent.putExtra(DESPORTOESCOLAR_ID, id);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(DESPORTOESCOLAR_CURSOR_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == DESPORTOESCOLAR_CURSOR_LOADER_ID) {
            return new CursorLoader(this, DesportoEscolarContentProvider.DESPORTOS_URI, DbTableAtletas.ALL_COLUMNS,
                    null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        atletasCursorAdapter.refreshData(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        atletasCursorAdapter.refreshData(null);
    }

}