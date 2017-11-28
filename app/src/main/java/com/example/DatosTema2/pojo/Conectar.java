package com.example.DatosTema2.pojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase Conectar
 */

public class Conectar {
    private static String leer(InputStream entrada) throws IOException {
        BufferedReader in;
        String linea;
        StringBuilder miCadena = new StringBuilder();
        in = new BufferedReader(new InputStreamReader(entrada), 32000);
        while ((linea = in.readLine()) != null)
            miCadena.append(linea);
        in.close();
        return miCadena.toString();
    }

    public static Resultado conectarJava(String texto) {
        URL url;
        HttpURLConnection urlConnection = null;
        int respuesta;
        Resultado resultado = new Resultado();
        try {
            url = new URL(texto);
            urlConnection = (HttpURLConnection) url.openConnection();
            respuesta = urlConnection.getResponseCode();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                resultado.setCodigo(true);
                resultado.setContenido(leer(urlConnection.getInputStream()));
            } else {
                resultado.setCodigo(false);
                resultado.setMensaje("Error en el acceso a la web: " + String.valueOf(respuesta));
            }
        } catch (IOException e) {
            resultado.setCodigo(false);
            resultado.setMensaje("Excepción: " + e.getMessage());
        } finally {
            try {
                if (urlConnection != null)
                    urlConnection.disconnect();
            } catch (Exception e) {
                resultado.setCodigo(false);
                resultado.setMensaje("Excepción: " + e.getMessage());
            }
            return resultado;
        }
    }


}
