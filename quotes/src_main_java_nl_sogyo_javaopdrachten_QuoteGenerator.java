package nl.sogyo.javaopdrachten;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.TimeZone;

import nl.sogyo.javaopdrachten.quote.Quote;

public class QuoteGenerator {
    private static Quote quote = new Quote();
    private static Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    public static void main(String... args) {        
        int index = GetQuoteIndex();
        String[] chosenQuote = SetQuote(index);
        
        System.out.println("Quote for " + GetDayName() + " the " + GetDayInMonth() + "th of " + GetMonthName() + ":");
        System.out.println(chosenQuote[0] + " " + chosenQuote[1]);

        System.console().readLine();
    }

    private static String GetMonthName() {
        String result = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)).getMonth().toString().toLowerCase();
        return result.substring(0,1).toUpperCase() + result.substring(1);
    }

    private static String GetDayInMonth() {
        String result = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        return result;
    }

    private static String GetDayName() {
        //So the Calendar.MONTH variable is zero based, meaning month 0 is January and 11 is December
        //Using the LocalDate.of method to get the name of the day uses a 1 based index meaning month 1 is January and 12 is December
        //To ensure the correct month is returned add +1 to the month
        String result = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)).getDayOfWeek().toString().toLowerCase();
        //Take the first character of the string, change this to uppercase and append the result of the string afterwards
        return result.substring(0,1).toUpperCase() + result.substring(1);
    }

    private static String[] SetQuote(int index) {
        String[] chosenQuote = GetQuote(index);
        String[] result = new String[2];
        //Check if quote has punctuation(!,?), add full stop(.) if it doesn't
        if(!HasEndPunctuation(chosenQuote[0])){
            chosenQuote[0] = chosenQuote[0] + ".";
        }
        //Put quotation marks around quote and capitalize the begin
        result[0] = ConvertToQuote(chosenQuote[0]);
        //Split name by space, for every result add a capital letter
        result[1] = ConvertToAuthor(chosenQuote[1]);
        //Add -- with a space in front of the name
        return result;
    }

    private static String ConvertToAuthor(String string) {
        String[] names = string.split(" ");
        String result = "-- ";
        for(String name : names){
            result += name.substring(0,1).toUpperCase() + name.substring(1) + " ";
        }
        return result;
    }

    private static boolean HasEndPunctuation(String string) {
        //If string has punctuation at the end(!,? or .) return true, else return false
        char lastCharacter = string.charAt(string.length() -1);
        if(lastCharacter == '!' || lastCharacter == '?' || lastCharacter == '.') return true;
        return false;
    }

    private static String ConvertToQuote(String quote) {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, '\"');
        sb.insert(1, quote.substring(0,1).toUpperCase() + quote.substring(1));
        sb.append('\"');
        return sb.toString();
    }

    private static int GetQuoteIndex() {
        //Because indexes are zero-based and a year doesn't have a zeroth(?) day subtract one from the dayOfYear variable to ensure
        //index 0 will be used aswell
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        return (dayOfYear -1) % quote.quotes.length;
    }

    private static String[] GetQuote(int index) {
        String[] result = new String[2];
        //Flip indexes so that the quote is in the first entry and the name in the second for proper displaying
        result[0] = quote.quotes[index][1];
        result[1] = quote.quotes[index][0];
        return result;
    }
}
