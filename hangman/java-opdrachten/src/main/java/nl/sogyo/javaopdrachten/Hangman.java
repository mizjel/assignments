package nl.sogyo.javaopdrachten;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Hangman {
    static List<String> Words = Arrays.asList("Pizza","Bagels","Hotdog","Kroket","Shoarma","Paparazzi", "Hangman");
    static char[] Board;
    static String ChosenWord = "";
    static char[] CharactersInWord;

    static int CurrentFailedAttempts = 0;
    static int MaxAttempts = 10;
    static char[] MissedLetters = new char[0];

    static Scanner scanner = new Scanner(System.in);
    public static void main(String... args){
        //Pick the word to be used and set the Board variables
        Initialize();
        
        boolean done = false;
        //Start the loop
        while(!done){
            //Print the Board array variables
            PrintBoard();

            System.out.println(MaxAttempts - CurrentFailedAttempts + " guesses left. Missed letters: " + PrintMissedLetters());

            //Check if human correctly guessed the word
            if(WordCompleted()){
                System.out.println("Game over, human wins!");
                done = true;
                break;
            }
            //Check if the current amount of failed attempts is the same as the max allowed attempts, if true then game over and skynet wins
            if(CurrentFailedAttempts == MaxAttempts){
                System.out.println("Game over, computer wins!");
                done = true;
                break;
            }
            
            System.out.print("Guess a letter: ");

            //Get user input and convert it to lower case
            char guess = Character.toLowerCase(scanner.next().charAt(0));

            //Check if the entered character exists in the selected word
            if(CharacterExists(guess)){
                //Get the positions for the entered character and place that character on the same position in the board
                int[] positions = GetCharacterPositions(guess);
                for(int position : positions){
                    Board[position] = guess;
                }
            }
            else{
                //Add a failed attempt and failed letter to show the user
                CurrentFailedAttempts++;
                AddFailedLetter(guess);
            }
        }
    }

    //Returns true if board variables are the same as the characters in the used word
    private static boolean WordCompleted() {
        for(int i = 0; i < CharactersInWord.length; i++){
            if(Board[i] != Character.toLowerCase(CharactersInWord[i]))
                return false;   
        }
        return true;
    }

    //Print the array of missed letters
    private static String PrintMissedLetters() {
        String result = "";
        for(char character : MissedLetters){
            result += character + " ";
        }
        return result;
    }

    //Adds a missed/failed letter to the MissedLetters array to show the user
    private static void AddFailedLetter(char guess) {
        if(MissedLetters.length == 0) MissedLetters = new char[1];
        if(!SpaceLeftInArray())
            MissedLetters = ResizeArray();

        for(int i = 0; i < MissedLetters.length; i++){
            if(MissedLetters[i] == 0){
                MissedLetters[i] = guess;
            }
        }
    }

    //Resizes the missed letters array if the MissedLetters array has been completely filled
    private static char[] ResizeArray() {
        char[] newArray = new char[MissedLetters.length + 1];
        System.arraycopy(MissedLetters, 0, newArray, 0, MissedLetters.length);
        return newArray;
    }

    //Returns true if there's space left in the MissedLetters array
    private static boolean SpaceLeftInArray() {
        for(int i = 0; i < MissedLetters.length; i++){
            if(MissedLetters[i] == 0) return true;
        }
        return false;
    }

    //Returns an integer array containing the positions of the guessed character in the used word
    private static int[] GetCharacterPositions(char guess) {
        int[] result = new int[(int)GetCharacterAmount(guess)];

        int index = 0;
        for(int i = 0; i < CharactersInWord.length; i++){
            if(Character.toLowerCase(CharactersInWord[i]) == guess){
                result[index] = i;
                index++;
            } 
        }
        return result;
    }

    //Returns the amount of characters that have been guessed and are available in the used word
    private static long GetCharacterAmount(char guess) {
        //One line solution
        return ChosenWord.toLowerCase().chars().filter(ch -> ch == guess).count();

        //Solution using for loop
        // int amount = 0;
        // for(int i = 0; i < CharactersInWord.length; i++){
        //     if(CharactersInWord[i] == guess) amount++;
        // }
        // return amount;
    }

    //Returns true if the guessed character exists in the used word
    private static boolean CharacterExists(char guess) {
        for(char character : CharactersInWord){
            if(Character.toLowerCase(character) == guess) return true;
        }
        return false;
    }

    //Prints the board by printing all Board variables next to each other
    private static void PrintBoard() {
        System.out.flush();
        for(int i = 0; i < Board.length; i++){
            System.out.print(Board[i] + " ");
        }
        System.out.print("\n");
    }

    //Gets a random word from the list and populates the Board array with default values
    private static void Initialize() {
        int randomIndex = GetRandomIndex();
        ChosenWord = Words.get(randomIndex);
        CharactersInWord = ChosenWord.toCharArray();
        
        PopulateBoard(ChosenWord.length());
    }

    //Populates the Board array with default an underscore(_) as default value
    private static void PopulateBoard(int length) {
        Board = new char[length];
        for(int i = 0; i < length; i++){
            Board[i] = '_';
        }
    }

    //Gets a random index based on the size of the Words array
    private static int GetRandomIndex() {
        return ThreadLocalRandom.current().nextInt(0, Words.size());
    }
}
