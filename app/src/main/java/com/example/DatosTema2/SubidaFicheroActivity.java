package com.example.DatosTema2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class SubidaFicheroActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtRuta;
    private Button btnSubir;
    private Button btnExplorar;
    private TextView txvresultado;
    private static final int ABRIRFICHERO_REQUEST_CODE = 1;
    public final static String WEB = "http://alumno.mobi/~alumno/superior/diaz/upload.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subida_fichero);
        edtRuta = (EditText) findViewById(R.id.edtRuta);
        txvresultado =(TextView) findViewById(R.id.txvresultado);
        btnSubir = (Button) findViewById(R.id.btnSubir);
        btnSubir.setOnClickListener(this);
        btnExplorar = (Button) findViewById(R.id.btnExplorar);
        btnExplorar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnSubir:
                subida();
                break;
            case R.id.btnExplorar:
                explorar();
                break;

        }
    }


    private void subida() {
        final AsyncHttpClient RestClient = new AsyncHttpClient();
        String fichero = edtRuta.getText().toString();
        final ProgressDialog progreso = new ProgressDialog(SubidaFicheroActivity.this);
        File myFile;
        Boolean existe = true;
        myFile = new File(Environment.getExternalStorageDirectory(), fichero);
        //File myFile = new File("/path/to/file.png");
        RequestParams params = new RequestParams();
        try {
            params.put("fileToUpload", myFile);
        } catch (FileNotFoundException e) {
            existe = false;
            Toast.makeText(SubidaFicheroActivity.this,"Error en el fichero: " + e.getMessage(),Toast.LENGTH_SHORT);
            //Toast.makeText(this, "Error en el fichero: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (existe)
            RestClient.post(WEB, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    // called before request is started
                    progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progreso.setMessage("Conectando . . .");
                    //progreso.setCancelable(false);
                    progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            RestClient.cancelRequests(getApplicationContext(), true);
                        }
                    });
                    progreso.show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    progreso.setMessage("Error de subida del fichero");

                    progreso.dismiss();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    txvresultado.setText(responseString);
                    Toast.makeText(SubidaFicheroActivity.this,responseString,Toast.LENGTH_SHORT);
                    progreso.dismiss();
                }
            });
    }


    private void explorar() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, ABRIRFICHERO_REQUEST_CODE);
        else
            //informar que no hay ninguna aplicación para manejar ficheros
            Toast.makeText(this, "No hay aplicación para manejar ficheros", Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ABRIRFICHERO_REQUEST_CODE)
            if (resultCode == RESULT_OK) {
                // Mostramos en la etiqueta la ruta del archivo seleccionado
                String ruta = data.getData().getPath();
                int index = ruta.indexOf(':')+1;


                ruta = ruta.substring(index);
                edtRuta.setText(ruta);
            } else
                Toast.makeText(this, "Error: " + resultCode, Toast.LENGTH_SHORT).show();
    }
}

