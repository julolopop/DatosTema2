package com.example.DatosTema2;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DatosTema2.pojo.Memoria;
import com.example.DatosTema2.pojo.Resultado;

public class AgendaActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText tieNombre;
    private TextInputEditText tieNTelefono;
    private TextInputEditText tieMail;
    private TextView txvContactos;
    private Button btnAnadir;
    private Button btnListar;

    Resultado resultado;
    private Memoria memoria;

    private String contacto;

    private static final String RUTA = "agenda.txt";

    private static final String CODIFICACION = "UTF-8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        inicializar();

        memoria = new Memoria(this);

    }

    void inicializar() {
        tieNombre = (TextInputEditText) findViewById(R.id.tieNombre);
        tieNTelefono = (TextInputEditText) findViewById(R.id.tieNTelefono);
        tieMail = (TextInputEditText) findViewById(R.id.tieMail);
        txvContactos = (TextView) findViewById(R.id.txvContactos);
        btnListar = (Button) findViewById(R.id.btnListar);
        btnAnadir = (Button) findViewById(R.id.btnAnadir);
        btnAnadir.setOnClickListener(this);
        btnListar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
    if(view == btnListar)
    {
        Listar();
    }
    if(view == btnAnadir)
    {
        Guardar();
    }
    }



    private void Guardar()
    {

        if (tieNombre.getText().length() == 0) {
            tieNombre.setError("Hay que rellenar el campo");
        }
        if (tieNTelefono.getText().length() == 0) {
            tieNTelefono.setError("Hay que rellenar el campo");
        }
        if (tieMail.getText().length() == 0) {
            tieMail.setError("Hay que rellenar el campo");
        }

        if (!(tieNombre.getText().length() == 0 || tieNTelefono.getText().length() == 0 || tieMail.getText().length() == 0)) {
            contacto = tieNombre.getText().toString() + "; " + tieNTelefono.getText().toString() + "; " + tieMail.getText().toString() + "\n";
            memoria.escribirInterna(RUTA, contacto, true, CODIFICACION);
            Toast.makeText(this, "Contacto Guardado", Toast.LENGTH_SHORT).show();
        }
    }
    private void Listar() {
        resultado = memoria.leerInterna(RUTA, CODIFICACION);
        if (resultado.isCodigo()) {
            txvContactos.setText(resultado.getContenido());
            Toast.makeText(this, "Fin de listado de contactos", Toast.LENGTH_SHORT).show();
        }
        else
        {
            txvContactos.setText("");
            Toast.makeText(this, "Error Al listar los contactos", Toast.LENGTH_SHORT).show();
        }
    }


}
