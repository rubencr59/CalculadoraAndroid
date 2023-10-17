package com.example.holamundo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.Objects;

public class Calculadora extends AppCompatActivity implements View.OnClickListener {

    //TextView donde se encuentra la operación
    TextView txtOperacion;

    //TextView donde se encuentra el resultado.
    TextView txtResultado;

    //Texto dentro del textView txtOperacion.
    String textoEnLaOperacion;

    //Número que  se guarda en memoria.
    Double numeroMemoria;

    //Tamaño original del texto.
    Float tamañoOriginalTexto;

    //Lista donde se guardan las id de los botones.
    private int[] buttonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtOperacion = findViewById(R.id.operacion); //Obtengo el txtView de la operacion.
        textoEnLaOperacion = txtOperacion.getText().toString(); //Obtengo el texto dentro del txtViewOperacion.
        tamañoOriginalTexto = txtOperacion.getTextSize() / getResources().getDisplayMetrics().scaledDensity; //Obtenemos el tamaño del texto en sp.

        txtResultado = findViewById(R.id.resultado); //Obtengo el txtView del resultado.

        //Lista de botones.
        buttonList = new int[]{
                R.id.btn0,R.id.btn1, R.id.btn2, R.id.btn3,R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnNegativo, R.id.btnDividir, R.id.btnMultiplicar, R.id.btnSumar, R.id.btnRestar,
                R.id.btnComa, R.id.btnIgual, R.id.btnBorrarMemoria,
                R.id.btnSumarMemoria, R.id.btnRestarMemoria, R.id.btnNumeroMemoria
        };
    }

    //Cambia el signo del primer número.
    public void onClickCambiarSigno(){

        textoEnLaOperacion = txtOperacion.getText().toString();//Actualizo el texto de la operación.


        if(!textoEnLaOperacion.equals("0")){ //Si la operación no es 0, cambia el signo del primer número.
            ArrayList<Character> listaChar = new ArrayList<>();
            for (int i = 0; i< textoEnLaOperacion.length(); i++) {
                listaChar.add(textoEnLaOperacion.charAt(i));
            }
            if (listaChar.get(0).equals('-')){
                listaChar.remove(0);
                StringBuilder sb = new StringBuilder(listaChar.size());
                for (Character c : listaChar) {
                    sb.append(c);
                }
                String textocreado = sb.toString();
                txtOperacion.setText(textocreado);
            }else{
                textoEnLaOperacion = "-"+ textoEnLaOperacion;
                txtOperacion.setText(textoEnLaOperacion);
            }
        }
    }

    //Introduce los número y símbolos a la operación.
    public void onClickOperacion(String valor){
        if (Objects.equals(textoEnLaOperacion,"0")){
            textoEnLaOperacion = "";
            txtOperacion.setText("");
        }

        if (valor.equals("X")){
            textoEnLaOperacion += "*";
            txtOperacion.setText(textoEnLaOperacion);
        }else{
            textoEnLaOperacion += valor;
            txtOperacion.setText(textoEnLaOperacion);
        }
    }


    //Calcula y muestra el resultado.
    public double onClickResultado(){
        Expression exp = new ExpressionBuilder(textoEnLaOperacion).build();
        Double resultado = exp.evaluate();
        return resultado;
    }

    //Hace un reset en la operación.
    public void onClickReset(){
        textoEnLaOperacion = "0";
        txtOperacion.setText("0");
    }

    //Suma el número que haya en memoria a la operación.
    public void onClickSumarMemoria(){
        Double resultado = onClickResultado();
        resultado += numeroMemoria; // Sumar el número de memoria al resultado
        textoEnLaOperacion = resultado.toString(); // Actualizar texto con el nuevo resultado
        txtOperacion.setText(textoEnLaOperacion);
    }

    //Resta el número que haya en memoria a la operación.
    public void onClickRestarMemoria(){
        Double resultado = onClickResultado();
        resultado -= numeroMemoria; // Restar el número de memoria al resultado
        textoEnLaOperacion = resultado.toString(); // Actualizar texto con el nuevo resultado
        txtOperacion.setText(textoEnLaOperacion);
    }


    //Cambia el tamaño de la operación dependiendo del tamaño.
    public void CambiarTamaño(){
        //Obtiene el tamaño del texto en SP.
        float tamañoTextoSp = txtOperacion.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
        txtOperacion.setTextSize(tamañoTextoSp-10);

    }

    @Override
    public void onClick(View view) {

        //Hace reset la operación y vuelve a su tamaño original.
        if (view.getId() ==  R.id.btnReset) {
            onClickReset();
            txtOperacion.setTextSize(tamañoOriginalTexto);
        }

        //El límite de la operación es 40.
        if (textoEnLaOperacion.length() != 40){

            //Dependiendo del tamaño de la operación cambia el tamaño.
            if(textoEnLaOperacion.length() == 10 || textoEnLaOperacion.length() == 12 || textoEnLaOperacion.length() == 20 ){
                CambiarTamaño();
            }
            if (view.getId()== R.id.btnNegativo){
                onClickCambiarSigno();
            } else if (view.getId() == R.id.btnIgual){
                Double resultado = onClickResultado();
                txtResultado.setText(resultado+"");
                numeroMemoria = resultado;
            }else if(view.getId() == R.id.btnBorrarMemoria){
                numeroMemoria = 0.0;
            }else if(view.getId()==R.id.btnNumeroMemoria){
                txtOperacion.setText(numeroMemoria+"");
            }else if(view.getId() == R.id.btnSumarMemoria){
                onClickSumarMemoria();
            }else if(view.getId() == R.id.btnRestarMemoria){
                onClickRestarMemoria();
            }else{
                for (Integer botonid:buttonList) {
                    Button botonEnLista = findViewById(botonid);
                    if (botonid == view.getId()) {
                        String valor = botonEnLista.getText().toString();
                        onClickOperacion(valor);
                    }
                }
            }
        }
    }
}



