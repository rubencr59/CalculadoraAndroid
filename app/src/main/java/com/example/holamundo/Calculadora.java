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


    TextView txtOperacion;

    TextView txtResultado;
    String texto;

    String numeroMemoria;

    Float tamañoOriginalTexto;

    private int[] buttonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtOperacion = findViewById(R.id.operacion);
        texto = txtOperacion.getText().toString();
        tamañoOriginalTexto = txtOperacion.getTextSize() / getResources().getDisplayMetrics().scaledDensity;

        txtResultado = findViewById(R.id.resultado);

        buttonList = new int[]{
                R.id.btn0,R.id.btn1, R.id.btn2, R.id.btn3,R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnNegativo, R.id.btnDividir, R.id.btnMultiplicar, R.id.btnSumar, R.id.btnRestar,
                R.id.btnPorcentaje, R.id.btnComa, R.id.btnIgual, R.id.btnBorrarMemoria,
                R.id.btnSumarMemoria, R.id.btnRestarMemoria, R.id.btnNumeroMemoria
        };
    }

    //Cambia el signo del primer número.
    public void onClickCambiarSigno(){

        texto = txtOperacion.getText().toString();

        ArrayList<Character> listaChar = new ArrayList<>();
        for (int i = 0; i<texto.length();i++) {
            listaChar.add(texto.charAt(i));
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
            texto = "-"+texto;
            txtOperacion.setText(texto);
        }

    }

    //Introduce los número y símbolos a la operación.
    public void onClickOperacion(String valor){
        if (Objects.equals(texto,"0")){
            texto = "";
            txtOperacion.setText("");
        }

        if (valor.equals("X")){
            texto += "*";
            txtOperacion.setText(texto);
        }else{
            texto += valor;
            txtOperacion.setText(texto);
        }
    }


    //Calcula y muestra el resultado.
    public void onClickResultado(){
        Expression exp = new ExpressionBuilder(texto).build();
        Double resultado = exp.evaluate(); 
        txtResultado.setText(exp.evaluate()+"");

    }

    //Hace un reset en la operación
    public void onClickReset(){
        texto = "0";
        txtOperacion.setText("0");
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
        if (texto.length() != 40){

            //Dependiendo del tamaño de la operación cambia el tamaño.
            if(texto.length() == 10 ||texto.length() == 12 || texto.length() == 20 ){
                CambiarTamaño();

            }


            for (Integer botonid:buttonList){
                Button botonEnLista = findViewById(botonid);

                if (view.getId()== R.id.btnNegativo && !Objects.equals(texto,"0")){
                    onClickCambiarSigno();
                } else if (view.getId() == R.id.btnIgual){
                    onClickResultado();
                }else if(view.getId() == R.id.btnBorrarMemoria){
                    txtResultado.setText("");

                } else if(botonid == view.getId()){
                    String valor = botonEnLista.getText().toString();
                    onClickOperacion(valor);
                }

            }
        }

    }



}



