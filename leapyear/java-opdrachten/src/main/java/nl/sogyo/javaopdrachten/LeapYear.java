package nl.sogyo.javaopdrachten;

public class LeapYear {
    public static void main(String... args) {
        while(true){
            GetLeapYear();
        }
    }
    public static void GetLeapYear(){
        System.out.println("Enter a year:");
        int year = Integer.parseInt(System.console().readLine());
        int result = 0;
        
        if(IsCentury(year)){
            System.out.println("Is a century year");
            result = year % 400;
        }
        else{
            System.out.println("Is not a century year");
            result = year % 4;
        }
        if(HasNoRemainder(result)){
            System.out.println("Is a leap year");
        }
        else{
            System.out.println("Is not a leap year");
        }
        System.out.println("Enter to continue");
        System.console().readLine();
        System.out.flush();
    }
    public static boolean HasNoRemainder(int number){
        return number == 0 ? true : false;
    }
    public static boolean IsCentury(int year){
        int numOfDigits = (int)(Math.log10(year) + 1);
        
        if(numOfDigits < 4) return false;

        if(year % 100 == 0) return true;
        return false;
    }
}
