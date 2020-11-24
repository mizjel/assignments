package nl.sogyo.javaopdrachten;

import java.math.BigInteger;

public class Fraction {
    //Teller(bovenste in breuk) is numerator
    final int numerator;
    //Noemer(onderste in breuk) is denominator
    final int denominator;
    
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    public Fraction divide(Fraction fraction){
        int reversedNumerator = fraction.denominator;
        int reversedDenominator = fraction.numerator;

        Fraction result = multiply(new Fraction(reversedNumerator, reversedDenominator));

        return result;
    }
    public Fraction divide(int number){
        //The result of fraction division can be found by first converting the number to a fraction and reversing the fraction
        //So the numerator becomes the denominator and vice versa
        //After the fraction has been reversed the result can be found by using the multiply method on the current fraction
        //With the reversed fraction as a parameter
        int reversedNumerator = 1;
        int reversedDenominator = number;

        Fraction result = multiply(new Fraction(reversedNumerator, reversedDenominator));

        return result;
    }
    public Fraction multiply(Fraction fraction){
        int unroundedDenominator = denominator * fraction.denominator;
        int unroundedNumerator = numerator * fraction.numerator;

        BigInteger b1 = BigInteger.valueOf(unroundedDenominator);
        BigInteger gcd = b1.gcd(BigInteger.valueOf(unroundedNumerator));

        int roundedDenominator = unroundedDenominator / gcd.intValue();
        int roundedNumerator = unroundedNumerator / gcd.intValue();

        return new Fraction(roundedNumerator, roundedDenominator);
    }
    public Fraction multiply(int number){
        //Multiply the numerator
        //Round the multiplied numerator and the current denominator
        int unroundedNumerator = numerator * number;

        BigInteger b1 = BigInteger.valueOf(denominator);
        BigInteger gcd = b1.gcd(BigInteger.valueOf(unroundedNumerator));

        int roundedDenominator = denominator / gcd.intValue();
        int roundedNumerator = unroundedNumerator / gcd.intValue();

        return new Fraction(roundedNumerator, roundedDenominator);
    }
    public Fraction subtract(int number){
        int newNumerator = numerator - (denominator * number);
        return new Fraction(newNumerator, denominator);
    }
    public Fraction subtract(Fraction fraction){
        int unroundedDenominator =  denominator * fraction.denominator;
        int unroundedNumerator = (fraction.denominator * numerator) - (denominator * fraction.numerator);

        BigInteger b1 = BigInteger.valueOf(unroundedDenominator);
        BigInteger gcd = b1.gcd(BigInteger.valueOf(unroundedNumerator));
        
        int roundedDenominator = unroundedDenominator / gcd.intValue();
        int roundedNumerator = unroundedNumerator / gcd.intValue();

        return new Fraction(roundedNumerator, roundedDenominator);
    }
    public Fraction add(int number){
        int newNumerator = numerator + (denominator * number);
        return new Fraction(newNumerator, denominator);
    }
    public Fraction add(Fraction fraction){
        int unroundedDenominator =  denominator * fraction.denominator;
        int unroundedNumerator = (fraction.denominator * numerator) + (denominator * fraction.numerator);

        BigInteger b1 = BigInteger.valueOf(unroundedDenominator);
        BigInteger gcd = b1.gcd(BigInteger.valueOf(unroundedNumerator));
        
        int roundedDenominator = unroundedDenominator / gcd.intValue();
        int roundedNumerator = unroundedNumerator / gcd.intValue();
        return new Fraction(roundedNumerator, roundedDenominator);
    }
    public double toDecimalNotation(){
        return (double)numerator / (double)denominator;
    }
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
