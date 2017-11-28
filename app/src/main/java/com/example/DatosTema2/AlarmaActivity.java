package com.example.DatosTema2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.DatosTema2.pojo.Memoria;

public class AlarmaActivity extends AppCompatActivity implements View.OnClickListener {


    EditText edtTime1;
    EditText edtMess1;
    CheckBox chkAlarma1;
    EditText edtTime2;
    EditText edtMess2;
    CheckBox chkAlarma2;
    EditText edtTime3;
    EditText edtMess3;
    CheckBox chkAlarma3;
    EditText edtTime4;
    EditText edtMess4;
    CheckBox chkAlarma4;
    EditText edtTime5;
    EditText edtMess5;
    CheckBox chkAlarma5;
    Button btnIniciar;

    String alarma1;
    String alarma2;
    String alarma3;
    String alarma4;
    String alarma5;

    Memoria memoria;

    Bundle bundle;

    private static final String RUTA = "alarmas.txt";

    private static final String CODIFICACION = "UTF-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        inicializar();
    }

    void inicializar() {
        bundle = new Bundle();
        edtTime1 = (EditText) findViewById(R.id.edtTime1);
        edtMess1 = (EditText) findViewById(R.id.edtMess1);
        chkAlarma1 = (CheckBox) findViewById(R.id.chkAlarma1);
        edtTime2 = (EditText) findViewById(R.id.edtTime2);
        edtMess2 = (EditText) findViewById(R.id.edtMess2);
        chkAlarma2 = (CheckBox) findViewById(R.id.chkAlarma2);
        edtTime3 = (EditText) findViewById(R.id.edtTime3);
        edtMess3 = (EditText) findViewById(R.id.edtMess3);
        chkAlarma3 = (CheckBox) findViewById(R.id.chkAlarma3);
        edtTime4 = (EditText) findViewById(R.id.edtTime4);
        edtMess4 = (EditText) findViewById(R.id.edtMess4);
        chkAlarma4 = (CheckBox) findViewById(R.id.chkAlarma4);
        edtTime5 = (EditText) findViewById(R.id.edtTime5);
        edtMess5 = (EditText) findViewById(R.id.edtMess5);
        chkAlarma5 = (CheckBox) findViewById(R.id.chkAlarma5);
        btnIniciar = (Button) findViewById(R.id.btnInicio);
        btnIniciar.setOnClickListener(this);
        memoria = new Memoria(getApplicationContext());
        memoria.escribirExterna(RUTA, "", false, CODIFICACION);
    }

    @Override
    public void onClick(View view) {
        if (view == btnIniciar) {
            Guardar();
            Iniciar();
        }
    }

    private void Iniciar() {
        Intent intnt = new Intent(AlarmaActivity.this, ContadorActivity.class);
        intnt.putExtra("alarmas", bundle);
        startActivity(intnt);
    }

    private void Guardar() {
        if (edtTime1.getText().length() == 0 && chkAlarma1.isChecked()) {
            Toast.makeText(this, "Rellene los campos incompletos de la alarma 1", Toast.LENGTH_SHORT).show();
        }
        if (edtTime2.getText().length() == 0 && chkAlarma2.isChecked()) {
            Toast.makeText(this, "Rellene los campos incompletos de la alarma 2", Toast.LENGTH_SHORT).show();
        }
        if (edtTime3.getText().length() == 0 && chkAlarma3.isChecked()) {
            Toast.makeText(this, "Rellene los campos incompletos de la alarma 3", Toast.LENGTH_SHORT).show();
        }
        if (edtTime4.getText().length() == 0 && chkAlarma4.isChecked()) {
            Toast.makeText(this, "Rellene los campos incompletos de la alarma 4", Toast.LENGTH_SHORT).show();
        }
        if (edtTime5.getText().length() == 0 && chkAlarma5.isChecked()) {
            Toast.makeText(this, "Rellene los campos incompletos de la alarma 5", Toast.LENGTH_SHORT).show();
        }

        if (!(edtTime1.getText().length() == 0 && edtTime2.getText().length() == 0 && edtTime3.getText().length() == 0 && edtTime4.getText().length() == 0 && edtTime5.getText().length() == 0)) {
            if (edtTime1.getText().length() > 0 && edtTime1.getText().length() < 3 && chkAlarma1.isChecked()) {
                alarma1 = edtTime1.getText().toString() + ", " + edtMess1.getText().toString() + "\n";
                memoria.escribirExterna(RUTA, alarma1, true, CODIFICACION);

                bundle.putBoolean("Alarma1chk",chkAlarma1.isChecked());
                bundle.putInt("Alarma1Time", Integer.valueOf(edtTime1.getText().toString()));
                bundle.putString("Alarma1Mess", edtMess1.getText().toString());

            }
            if (edtTime2.getText().length() > 0 && edtTime2.getText().length() < 3 && chkAlarma2.isChecked()) {
                alarma2 = edtTime2.getText().toString() + "," + edtMess2.getText().toString() + "\n";
                memoria.escribirExterna(RUTA, alarma2, true, CODIFICACION);
                bundle.putInt("Alarma2Time", Integer.valueOf(edtTime2.getText().toString()));
                bundle.putBoolean("Alarma2chk",chkAlarma2.isChecked());
                bundle.putString("Alarma2Mess", edtMess2.getText().toString());

            }
            if (edtTime3.getText().length() > 0 && edtTime3.getText().length() < 3 && chkAlarma3.isChecked()) {
                alarma3 = edtTime3.getText().toString() + ",  " + edtMess3.getText().toString() + "\n";
                memoria.escribirExterna(RUTA, alarma3, true, CODIFICACION);
                bundle.putInt("Alarma3Time", Integer.valueOf(edtTime3.getText().toString()));
                bundle.putBoolean("Alarma3chk",chkAlarma3.isChecked());
                bundle.putString("Alarma3Mess", edtMess3.getText().toString());

            }
            if (edtTime4.getText().length() > 0 && edtTime4.getText().length() < 3 && chkAlarma4.isChecked()) {
                alarma4 = edtTime4.getText().toString() + ", " + edtMess4.getText().toString() + "\n";
                memoria.escribirExterna(RUTA, alarma4, true, CODIFICACION);
                bundle.putInt("Alarma4Time", Integer.valueOf(edtTime4.getText().toString()));
                bundle.putBoolean("Alarma4chk",chkAlarma4.isChecked());
                bundle.putString("Alarma4Mess", edtMess4.getText().toString());


            }
            if (edtTime5.getText().length() > 0 && edtTime5.getText().length() < 3 && chkAlarma5.isChecked()) {
                alarma5 = edtTime5.getText().toString() + ", " + edtMess5.getText().toString() + "\n";
                memoria.escribirExterna(RUTA, alarma5, true, CODIFICACION);
                bundle.putInt("Alarma5Time", Integer.valueOf(edtTime5.getText().toString()));
                bundle.putBoolean("Alarma5chk",chkAlarma5.isChecked());
                bundle.putString("Alarma5Mess", edtMess5.getText().toString());

            }
            Toast.makeText(this, "Alarmas Guardadas", Toast.LENGTH_SHORT).show();

        }
    }
}
