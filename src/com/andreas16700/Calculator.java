package com.andreas16700;

import java.math.BigDecimal;
import java.math.MathContext;

public class Calculator extends Thread{
    private final int increaseBy;
    private boolean isPositive;
    public static final int CALCULATIONS_BFORE_UPDATING = 1000;
    public static final MathContext precise = new MathContext(100);
    public Calculator(int incr, boolean isPositive){
        this.increaseBy = incr;
        this.isPositive=isPositive;
    }

    @Override
    public void run() {
        BigDecimal number = new BigDecimal(increaseBy, precise);
        BigDecimal zero = new BigDecimal(0);
        BigDecimal sum = zero;
        BigDecimal one = new BigDecimal(1, precise);
        BigDecimal ten = new BigDecimal(10);
        int runs=0;
        while (true){
            BigDecimal div = one.divide(number, precise);
            sum = isPositive ? sum.add(div) : sum.subtract(div);
            runs++;number=number.add(ten);
            isPositive=!isPositive;
            if (runs==CALCULATIONS_BFORE_UPDATING){
                SumMonitor.shared.add(sum);
                sum=zero;runs=0;
            }
        }
    }
}
