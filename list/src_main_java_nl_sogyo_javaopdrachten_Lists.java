package nl.sogyo.javaopdrachten;

import java.util.Random;

public class Lists {
    static Random randomGenerator = new Random();
    static int[] array = new int[10];
    static int[] dividableByTwoArray;
    static int[] dividableByThreeArray;
    static int[] dividableByFiveArray;
    static int[] remainingNumbersArray;
    public static void main(String... args) {
        InitializeArray();
        
        System.out.println("Displaying array:");
        DisplayArray();

        System.out.println("Highest value is: " + GetMaxValue());

        System.out.println("Displaying even values:");
        DisplayEvenValues();

        System.out.println("Displaying the split arrays with dividable numbers:");
        SplitArray();

        DisplayDividableArrays();

        BubbleSort();

        System.out.println("Displaying array after bubblesort in ascending order:");
        DisplayArray();

        System.console().readLine();
    }
    
    private static void BubbleSort() {
        boolean solved = false;
        while(!solved){
            for(int i = 1; i < array.length; i++){
                if(array[i-1] > array[i]){
                    int temp = array[i-1];
                    array[i-1] = array[i];
                    array[i] = temp;
                }
            }
            if(ArraySorted()) solved = true;
        }
    }

    private static boolean ArraySorted() {
        for(int i = 1; i < array.length; i++){
            if(array[i-1] > array[i]) return false;
        }
        return true;
    }

    private static void DisplayDividableArrays() {
        System.out.println("Numbers dividable by 2:");
        for(int number : dividableByTwoArray){
            System.out.println(number);
        }
        System.out.println("Numbers dividable by 3:");
        for(int number : dividableByThreeArray){
            System.out.println(number);
        }
        System.out.println("Numbers dividable by 5:");
        for(int number : dividableByFiveArray){
            System.out.println(number);
        }
        System.out.println("Numbers that are not dividable:");
        for(int number : remainingNumbersArray){
            System.out.println(number);
        }
    }

    private static void SplitArray() {
        dividableByTwoArray = GetValuesForDivision(2);
        dividableByThreeArray = GetValuesForDivision(3);
        dividableByFiveArray = GetValuesForDivision(5);
        remainingNumbersArray = GetValuesForDivision(0);
    }
    private static int[] GetValuesForDivision(int division){
        int[] result = new int[0];
        if(division != 0){
            result = new int[GetAmountForDivision(division)];
            int index = 0;
    
            for(int number : array){
                if(number % division == 0){
                    result[index] = number;
                    index++;
                }
            }
        }
        else{
            result = new int[GetAmountWithoutDivision()];
            int index = 0;
            for(int number : array){
                if(number % 2 != 0 && number % 3 != 0 && number % 5 != 0){
                    result[index] = number;
                    index++;
                }
            }
        }
        return result;
    }
    private static int GetAmountWithoutDivision(){
        int amount = 0;
        for(int number : array){
            if(number % 2 != 0 && number % 3 != 0 && number % 5 != 0) amount++;
        }
        return amount;
    }
    private static int GetAmountForDivision(int division){
        int amount = 0;
        for(int number : array){
            if(number % division == 0) amount++;
        }
        return amount;
    }
    private static void DisplayEvenValues() {
        for(int number : array){
            if(number % 2 == 0) System.out.println(number + " is an even number");
        }
    }

    private static int GetMaxValue() {
        int max = 0;

        for(int i = 0; i < array.length; i++)
        {
            if (array[i] > max)
                max = array[i];
        }
        return max;
    }
    private static void DisplayArray() {
        for(int number : array){
            System.out.println(number);
        }
        int[] lowestTwo = GetLowestValues();
        System.out.println("Lowest two: " + lowestTwo[0] + ", " + lowestTwo[1]);
    }

    private static int[] GetLowestValues() {
        int lowest = 99;
        int[] result = new int[2];
        int foundIndex = -1;

        for(int i = 0; i < array.length; i++){
            if(array[i] < lowest){
                lowest = array[i];
                foundIndex = i;
            }
        }
        result[0] = lowest;
        result[1] = GetLowestValues(foundIndex);

        return result;
    }
    private static int GetLowestValues(int excludeIndex) {
        int lowest = 99;

        for(int i = 0; i < array.length; i++){
            if(i != excludeIndex && array[i] < lowest) lowest = array[i];
        }
        return lowest;
    }

    private static void InitializeArray() {
        for(int i = 0; i < 10; i++){
            array[i] = randomGenerator.nextInt(99 - 1) + 1;
        }
    }
}
