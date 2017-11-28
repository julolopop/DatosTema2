package com.example.DatosTema2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Juanma
 */
public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnEj1,btnEj2, btnEj3, btnEj4,btnEj5,btnEj6,btnEj7;
    Intent intnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnEj1 =  (Button) findViewById(R.id.btnEj1);
        btnEj2 =  (Button) findViewById(R.id.btnEj2);
        btnEj3 =  (Button) findViewById(R.id.btnEj3);
        btnEj4 =  (Button) findViewById(R.id.btnEj4);
        btnEj5 =  (Button) findViewById(R.id.btnEj5);
        btnEj6 =  (Button) findViewById(R.id.btnEj6);
        btnEj7 =  (Button) findViewById(R.id.btnEj7);
        btnEj1.setOnClickListener(this);
        btnEj2.setOnClickListener(this);
        btnEj3.setOnClickListener(this);
        btnEj4.setOnClickListener(this);
        btnEj5.setOnClickListener(this);
        btnEj6.setOnClickListener(this);
        btnEj7.setOnClickListener(this);
    }

    //Este metodo comprobara cual es boton sobre el que se hace click y abrira la activity necesaria
    @Override
    public void onClick(View view) {
        if(view == btnEj1)
        {
            intnt = new Intent(this,AgendaActivity.class);
            startActivity(intnt);
        }
        if(view == btnEj2)
        {
            intnt = new Intent(this,AlarmaActivity.class);
            startActivity(intnt);
        }
        if(view == btnEj3)
        {
            intnt = new Intent(this,CalendarioActivity.class);
            startActivity(intnt);
        }
        if(view == btnEj4)
        {
            intnt = new Intent(this, NavegadorActivity.class);
            startActivity(intnt);
        }
        if(view == btnEj5)
        {
            intnt = new Intent(this,ImagenesActivity.class);
            startActivity(intnt);
        }
        if(view == btnEj6)
        {
            intnt = new Intent(this, ConversorActivity.class);
            startActivity(intnt);
        }
        if(view == btnEj7)
        {
            intnt = new Intent(this, SubidaFicheroActivity.class);
            startActivity(intnt);
        }

    }
}
