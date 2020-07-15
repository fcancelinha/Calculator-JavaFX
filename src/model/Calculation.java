package model;

import java.text.*;
import java.util.ArrayList;


public class Calculation {

    /*Olá Professora, por favor não estranhe o facto de estar tudo em inglês porque quero colocar no meu gihub
    * espero que esteja bom.*/

    private Operand OPERAND;
    private double currentNumber;
    private double currentResult;
    private DecimalFormat df = new DecimalFormat("0.######");

    public Calculation(){
        currentNumber = 0;
        currentResult = 0;
    }


    //Methods

    /* Lógica Principal */
    public String calculate(ArrayList<String> x){

        checkPrecedence(x);
        calculation(x);

        return String.valueOf(df.format(currentResult));
    }

    private void calculation(ArrayList<String> x){

        for(int i = 0; i < x.size() ; i++){

            if(i % 2 == 0) {
                currentNumber = Double.parseDouble(x.get(i));
                currentResult = (i >= 2) ? operations(currentResult, currentNumber) : Double.parseDouble(x.get(0));
            }else
                for(Operand c : Operand.values())
                    if(c.getOperandSymbol() == x.get(i).charAt(0))
                        OPERAND = c;
        }
    }

    private void checkPrecedence(ArrayList<String> x){

        //tentativa de fazer precedências
        if(x.size() > 3) {
            for (int i = 0; i < x.size(); ++i) {
                for (int j = 0; j < x.size(); j++) {
                    if (x.get(j).contains("*")) {
                        OPERAND = Operand.MULTIPLYING;
                        x.set(j, String.valueOf(operations(Double.parseDouble(x.get(j - 1)), Double.parseDouble(x.get(j + 1)))));
                        x.remove(j + 1);
                        x.remove(j - 1);
                    } else if (x.get(j).contains("/")) {
                        OPERAND = Operand.DIVISION;
                        x.set(j, String.valueOf(operations(Double.parseDouble(x.get(j - 1)), Double.parseDouble(x.get(j + 1)))));
                        x.remove(j + 1);
                        x.remove(j - 1);
                    }
                }
            }
        }

    }

    private double operations(double x, double y){

        switch(OPERAND.getOperandSymbol()){

            case '+': x += y; break;
            case '-': x -= y; break;
            case '*': x *= y; break;
            case '/': x /= y; break; //O erro está tratado, teste na calculadora.
        }

        return x;
    }


    public void clear(){
        currentResult = 0;
        currentNumber = 0;
    }


    //Getters & Setters

    public void setCurrentNumber(double currentNumber) {
        this.currentNumber = currentNumber;
    }

    public void setOPERAND(Operand OPERAND) {
        this.OPERAND = OPERAND;
    }

    public double getCurrentResult() {
        return currentResult;
    }
}
