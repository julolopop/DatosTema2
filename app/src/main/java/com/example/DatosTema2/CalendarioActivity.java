package com.example.DatosTema2;


import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;

import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.DatosTema2.pojo.Fechas;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarioActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText tieDateIni;
    EditText tieDateFin;
    EditText edtFechasFestivas;
    Date dateCalendar;
    Date dateIni;
    Date dateEnd;
    DatePickerDialog dpIni;
    DatePickerDialog dpFin;
    Button btnVerFestivos;
    ArrayList<Date> margenFechas;
    File fechasFestivasFile;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        File ruta_sd = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        new File(ruta_sd.getAbsolutePath(), "fechas.txt").delete();
        fechasFestivasFile = new File(ruta_sd.getAbsolutePath(), "fechas.txt");

        tieDateIni = (EditText) findViewById(R.id.tieDateIni);
        tieDateIni.setOnClickListener(this);

        tieDateFin = (EditText) findViewById(R.id.tieDateFin);
        tieDateFin.setOnClickListener(this);

        edtFechasFestivas = (EditText) findViewById(R.id.edtFechasFestivas);

        btnVerFestivos = (Button) findViewById(R.id.btnVerCalendario);
        btnVerFestivos.setOnClickListener(this);

        try {
            Fechas.CrearFicheroFestivos(fechasFestivasFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateCalendar = new Date(year, monthOfYear, dayOfMonth);
        if (view == dpIni) {
            dateIni = dateCalendar;
            tieDateIni.setText(new SimpleDateFormat("dd-MM-yy").format(dateIni));

        }
        if (view == dpFin) {
            dateEnd = dateCalendar;
            tieDateFin.setText(new SimpleDateFormat("dd-MM-yy").format(dateEnd));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == tieDateIni) {
            Calendar calendar = new GregorianCalendar();
            dpIni = DatePickerDialog.newInstance(
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dpIni.show(getFragmentManager(), "Datepickerdialog");
        }

        if (v == tieDateFin) {
            Calendar calendar = new GregorianCalendar();
            dpFin = DatePickerDialog.newInstance(
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dpFin.show(getFragmentManager(), "Datepickerdialog");
        }

        if (v == btnVerFestivos) {

            try {
                margenFechas = Fechas.margenDeFechas(dateIni, dateEnd);
                if (dateIni == null || dateEnd == null)
                    Toast.makeText(this, "Debes introducir las dos fechas", Toast.LENGTH_LONG).show();
                else if (margenFechas.size() == 0) {
                    Toast.makeText(this, "El margen de fechas no es válido", Toast.LENGTH_LONG).show();

                } else {
                    String texto = "";
                    Fechas.ComparaFechasFichero(fechasFestivasFile, margenFechas);
                    for (int i = 0; i < margenFechas.size(); i++) {
                        texto += (new SimpleDateFormat("dd-MM-yy").format(margenFechas.get(i)) + "\n");
                    }
                    edtFechasFestivas.setText(texto);
                }
            } catch (IOException e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (NullPointerException e) {
                Toast.makeText(this, "Debes introducir un margen de fechas", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
