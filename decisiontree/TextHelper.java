package nl.sogyo.javaopdrachten;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//A helper class to load the file contents in a string arraylist
public class TextHelper {
    //Run this program from the root of the program directory..
    File source = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\intermediate\\decision-tree-data.txt");
    ArrayList<String> fileContent = new ArrayList<String>();
    
    public TextHelper() { 
        GetStringsFromText();
    }

    private void GetStringsFromText(){
        try {
            Scanner scanner = new Scanner(source);
            while(scanner.hasNextLine()){
                fileContent.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //Nodes could be created here but it's not the place since the scope of this class is to do stuff with the text file and resulting strings
    //Nodes are created in the DecisionTreeManager
    public ArrayList<String[]> GetNodesStringList(){
        ArrayList<String[]> result = new ArrayList<String[]>();
        for(String s : fileContent){
            String[] lines = s.split(",");
            if(lines.length == 2){
                result.add(lines);
            }
        }
        return result;
    }
    //Edges cannot be created here because a node reference has to be set as well and this is not the place to do that
    //Edges and references are created in the DecisionTreeManager
    public ArrayList<String[]> GetEdgesStringList(){
        ArrayList<String[]> result = new ArrayList<String[]>();
        for(String s : fileContent){
            String[] lines = s.replaceAll("\\s+","").split(",");
            if(lines.length > 2){
                result.add(lines);
            }
        }
        return result;
    }
}
