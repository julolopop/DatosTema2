package com.example.DatosTema2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DatosTema2.pojo.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.Header;


public class ConversorActivity extends AppCompatActivity {


    private RadioButton _euroAUsa;
    private RadioButton _usaAEuro;
    private Button _btnConvertir;
    private EditText _editNumero1;
    private EditText _editNumero2;
    private TextView _txv1;
    private TextView _txv2;
    private String cambio;
    final String url = "http://alumno.mobi/~alumno/superior/diaz/cambio.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor);

        _euroAUsa = ((RadioButton) findViewById(R.id.checkEuro));
        _usaAEuro = ((RadioButton) findViewById(R.id.checkUsa));
        _btnConvertir = ((Button) findViewById(R.id.button));
        _editNumero1 = ((EditText) findViewById(R.id.Numero1));
        _editNumero2 = ((EditText) findViewById(R.id.Numero2));
        _txv1 = ((TextView) findViewById(R.id.textView));
        _txv2 = ((TextView) findViewById(R.id.textView2));

        leerCambio();
        _btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double doble;
                try {
                    doble = Double.parseDouble(_editNumero1.getText().toString());
                } catch (Exception e) {
                    _editNumero1.setText("0");
                    doble = 0;
                }


                convertir(doble);


            }
        });
        _euroAUsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _txv1.setText(R.string.euros);
                _txv2.setText(R.string.usa);
            }
        });
        _usaAEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _txv1.setText(R.string.usa);
                _txv2.setText(R.string.euros);
            }
        });


    }

    private void convertir(double doble) {

        if (_euroAUsa.isChecked()) {
            _editNumero2.setText(convertirADolares(doble,cambio));
        } else {
            _editNumero2.setText(convertirAEuros(doble,cambio));
        }

    }
    public void leerCambio(){
        File miFichero = new File (Environment.getExternalStorageDirectory().getAbsolutePath());
        RestClient.get(url, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(getApplicationContext(), "Fallo: " + statusCode + "\n" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                String cadena=null;
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                    while ((cadena = in.readLine()) != null) {
                        cambio=cadena;
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String convertirADolares(Double cantidad,String cambio) {
        double valor =cantidad / Double.parseDouble(cambio);
        return
                Double.toString(valor);
    }

    public String convertirAEuros(Double cantidad,String cambio) {
        double valor =cantidad * Double.parseDouble(cambio);
        return
                Double.toString(valor);
    }


}
