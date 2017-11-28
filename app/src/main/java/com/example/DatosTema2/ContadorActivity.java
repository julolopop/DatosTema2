package com.example.DatosTema2;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ContadorActivity extends AppCompatActivity {
    int alarma1Time;
    String alarma1Mess;
    boolean alarma1chk;
    int alarma2Time;
    String alarma2Mess;
    boolean alarma2chk;
    int alarma3Time;
    String alarma3Mess;
    boolean alarma3chk;
    int alarma4Time;
    String alarma4Mess;
    boolean alarma4chk;
    int alarma5Time;
    String alarma5Mess;
    boolean alarma5chk;
    int contadorAlarmas = 0;
    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        Bundle bundle = getIntent().getExtras().getBundle("alarmas");
        view = (TextView) findViewById(R.id.txvContador);
        view.setText("00");
        //try {
        alarma1Time = bundle.getInt("Alarma1Time");
        alarma1Mess = bundle.getString("Alarma1Mess");
        alarma1chk = bundle.getBoolean("Alarma1chk");
        alarma2Time = bundle.getInt("Alarma2Time");
        alarma2Mess = bundle.getString("Alarma2Mess");
        alarma2chk = bundle.getBoolean("Alarma2chk");
        alarma3Time = bundle.getInt("Alarma3Time");
        alarma3Mess = bundle.getString("Alarma3Mess");
        alarma3chk = bundle.getBoolean("Alarma3chk");
        alarma4Time = bundle.getInt("Alarma4Time");
        alarma4Mess = bundle.getString("Alarma4Mess");
        alarma4chk = bundle.getBoolean("Alarma4chk");
        alarma5Time = bundle.getInt("Alarma5Time");
        alarma5Mess = bundle.getString("Alarma5Mess");
        alarma5chk = bundle.getBoolean("Alarma5chk");
        IniciarContadores();
        //}catch (Exception e){

        //}

    }

    public void IniciarContadores() {
        int intervalo = 0;
        switch (contadorAlarmas) {
            case 0:
                if (alarma1chk) {
                    IniciarContador(alarma1Time, alarma1Mess);
                    alarma1chk = false;
                } else {
                    contadorAlarmas++;
                    IniciarContadores();
                }
                break;
            case 1:
                if (alarma2chk) {
                    IniciarContador(alarma2Time, alarma2Mess);
                    alarma2chk = false;
                } else {
                    contadorAlarmas++;
                    IniciarContadores();
                }
                break;
            case 2:
                if (alarma3chk) {
                    IniciarContador(alarma3Time, alarma3Mess);
                    alarma3chk = false;
                } else {
                    contadorAlarmas++;
                    IniciarContadores();
                }
                break;
            case 3:
                if (alarma4chk) {
                    IniciarContador(alarma4Time, alarma4Mess);
                    alarma4chk = false;
                } else {
                    contadorAlarmas++;
                    IniciarContadores();
                }
                break;
            case 4:
                if (alarma5chk) {
                    IniciarContador(alarma5Time, alarma5Mess);
                    alarma5chk = false;
                } else {
                    contadorAlarmas++;
                    IniciarContadores();
                }
                break;
            default:
                startActivity(new Intent(ContadorActivity.this, AlarmaActivity.class));
                break;
        }
    }

    public void IniciarContador(int minutes, final String message) {
        new CountDownTimer(minutes * 60 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

                view.setText("Segundos hasta siguiente alarma: " + "\n" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Toast.makeText(ContadorActivity.this, message, Toast.LENGTH_SHORT).show();
                contadorAlarmas++;
                IniciarContadores();
            }
        }.start();
    }
}
