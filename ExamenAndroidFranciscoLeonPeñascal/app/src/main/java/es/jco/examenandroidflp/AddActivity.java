package es.jco.examenandroidflp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    private EditText edtNombre, edtEmail;
    private Spinner listaTipos;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtEmail = (EditText)findViewById(R.id.edtEmail);

        listaTipos = (Spinner)findViewById(R.id.listaTipos);

        String[] tipos = {"Empleado", "Directivo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, tipos);
        listaTipos.setAdapter(adapter);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario(null,listaTipos.getSelectedItemPosition(),
                        edtNombre.getText().toString(),
                        edtEmail.getText().toString());

                Intent i = new Intent();
                i.putExtra("usuario", u);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
