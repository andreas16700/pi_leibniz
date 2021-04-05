package com.andreas16700;

import java.math.BigDecimal;
import java.math.MathContext;

public class Main {
    private static final BigDecimal four = new BigDecimal(4, Calculator.precise);
    private static final MathContext lessPrecise = new MathContext(100);
    private static BigDecimal getPi(BigDecimal sum){
        return sum.multiply(four);
    }
    public static void main(String[] args) throws InterruptedException {
        int threads = 5;
        Calculator[] calculators = new Calculator[threads];
        boolean isPositive=true;
        int n=1;
        for (int i = 0; i < threads; i++) {
            calculators[i] = new Calculator(n,isPositive);
            n+=2;isPositive=!isPositive;
        }
        for (int i = 0; i < threads; i++) {
            calculators[i].start();
        }
        int seconds = 2;
        long additions=0;
        while (true) {
            Thread.sleep(seconds * 1000L);
            while (additions==SumMonitor.shared.getAdditions())
                Thread.sleep(seconds * 1000);
            additions=SumMonitor.shared.getAdditions();
            BigDecimal sum = SumMonitor.shared.getSum();
            System.out.println("Additions: "+additions+"*"+threads+"*"+Calculator.CALCULATIONS_BFORE_UPDATING);
            System.out.println("Approximate pi: "+ getPi(sum));
        }
    }
}
