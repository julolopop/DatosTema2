package com.example.DatosTema2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class ImagenesActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUrl;
    private Button btnDescargar;
    private Button btnSiguiente;
    private Button btnAnterior;
    private ImageView imgImagen;
    private int imagenActual;
    private ArrayList<String> urls;
    private boolean exito;
    private static final int MAX_TIMEOUT = 2000;
    private static final int RETRIES = 1;
    private static final int TIMEOUT_BETWEEN_RETRIES = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenes);

        urls = new ArrayList<>();
        exito = false;

        edtUrl = (EditText) findViewById(R.id.edtUrl);
        btnDescargar = (Button) findViewById(R.id.btnDescargar);
        btnDescargar.setOnClickListener(this);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(this);
        btnAnterior = (Button) findViewById(R.id.btnAnterior);
        btnAnterior.setOnClickListener(this);
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDescargar) {
            descargarImagenes();
        }
        if (v == btnSiguiente) {
            if (exito) {
                imagenActual++;
                Picasso.with(this)
                        .load(urls.get(imagenActual % (urls.size() - 1)))
                        .into(imgImagen);
            }
        }
        if (v == btnAnterior) {
            if (exito) {
                if (imagenActual > 0) {
                    imagenActual--;
                } else {
                    imagenActual = urls.size() - 1;
                }
                Picasso.with(this)
                        .load(urls.get(imagenActual % (urls.size() - 1)))
                        .into(imgImagen);
            }
        }
    }



    private void descargarImagenes() {
        final ProgressDialog progreso = new ProgressDialog(this);
        final AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.setTimeout(MAX_TIMEOUT);
        cliente.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);

        if (URLUtil.isValidUrl(edtUrl.getText().toString())) {
            cliente.get(edtUrl.getText().toString(), new FileAsyncHttpResponseHandler(this) {

                @Override
                public void onStart() {
                    progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progreso.setMessage("Descargando imagenes...");
                    progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            cliente.cancelAllRequests(true);
                        }
                    });
                    progreso.show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    progreso.dismiss();
                    Toast.makeText(ImagenesActivity.this, "error: " + statusCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    String linea;
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        while ((linea = br.readLine()) != null) {
                            urls.add(linea);
                        }
                    } catch (FileNotFoundException e) {
                        Toast.makeText(ImagenesActivity.this, "No se ha encontrado el fichero", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(ImagenesActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                    exito = true;
                    progreso.dismiss();
                }
            });

        } else {
            Toast.makeText(ImagenesActivity.this, "La URL no válida", Toast.LENGTH_SHORT).show();
        }
    }


}
