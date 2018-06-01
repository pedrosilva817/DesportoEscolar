package pt.ipg.desportoescolar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonmasculino;

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
    }

    public void abrirmasculino(){
        Intent intent = new Intent(this, masculino.class);
        startActivity(intent);
    }

}
