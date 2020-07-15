package model;

import jdk.jfr.Percentage;

public enum Operand {

    SUM('+'),
    SUBTRACTION('-'),
    MULTIPLYING('*'),
    DIVISION('/'),
    PERCENTAGE('%');

    private char operandSymbol;

    private Operand(char operandSymbol){
        this.operandSymbol = operandSymbol;
    }

    public char getOperandSymbol() {
        return operandSymbol;
    }
}
