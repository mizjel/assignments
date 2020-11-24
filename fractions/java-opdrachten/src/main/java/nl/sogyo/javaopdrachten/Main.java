package nl.sogyo.javaopdrachten;

public class Main {
    public static void main(String... args){
        Fraction fraction = new Fraction(1,3);
        System.out.println(fraction.toDecimalNotation());
        Fraction addNumberFraction = fraction.add(2);
        System.out.println(addNumberFraction.numerator + ", " + addNumberFraction.denominator);

        Fraction fraction2 = new Fraction(2,4);
        Fraction addFraction2 = new Fraction(1,3);
        Fraction addFraction2Result = fraction2.add(addFraction2);
        System.out.println("adding a Fraction in another Fraction results #2: " + addFraction2Result.toString());

        Fraction subtractNumberFraction = new Fraction(4,3);
        Fraction subtractNumberFractionResult = subtractNumberFraction.subtract(1);
        System.out.println("Subtracting a number from fraction: " + subtractNumberFractionResult.toString());

        Fraction subtractFraction = new Fraction(1,2);
        Fraction subtractFraction2 = new Fraction(1,6);
        Fraction subtractFractionResult = subtractFraction.subtract(subtractFraction2);
        System.out.println("Subtracting a fraction from another fraction: " + subtractFractionResult.toString());

        Fraction multiplyNumberFraction = new Fraction(3,4);
        Fraction multiplyNumberFractionResult = multiplyNumberFraction.multiply(2);
        System.out.println("Multiply fraction by number: " + multiplyNumberFractionResult.toString());

        Fraction multiplyFraction = new Fraction(3,4);
        Fraction multiplyFraction2 = new Fraction(2,5);
        Fraction multiplyFractionResult = multiplyFraction.multiply(multiplyFraction2);
        System.out.println("Multiply fraction by another fraction: " + multiplyFractionResult.toString());

        Fraction divideNumberFraction = new Fraction(3,2);
        Fraction divideNumberFractionResult = divideNumberFraction.divide(2);
        System.out.println("Divide fraction by number: " + divideNumberFractionResult.toString());

        Fraction divideFraction = new Fraction(3,10);
        Fraction divideFraction2 = new Fraction(2,5);
        Fraction divideFractionResult = divideFraction.divide(divideFraction2);
        System.out.println("Divide fraction by another fraction: " + divideFractionResult.toString());
    }
}
