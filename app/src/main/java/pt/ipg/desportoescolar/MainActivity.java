package pt.ipg.desportoescolar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonmasculino;
    private Button buttonfeminino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonmasculino = (Button) findViewById(R.id.buttonmasculino);
        buttonmasculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirmasculino();
            }
        });

        buttonfeminino = (Button) findViewById(R.id.buttonfeminino);
        buttonfeminino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirfeminino();
            }
        });
    }

    public void abrirmasculino(){
        Intent intent = new Intent(this, masculino.class);
        startActivity(intent);
    }

    public void abrirfeminino(){
        Intent intent = new Intent(this, masculino.class);
        startActivity(intent);
    }

}
