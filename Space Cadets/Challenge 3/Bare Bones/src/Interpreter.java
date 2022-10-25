import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import java.nio.file.Paths;
import java.nio.file.InvalidPathException;

/*

Interpreter.java
Written by empulsion
- Utilises the Lexer and Executor classes in order to interpret and execute a provided text file

CREATED: 24/10/2022
LAST UPDATED: 24/10/2022

*/

public class Interpreter {
  public static void main(String[] args) {
    String filePath = fetchFilePath();
    Lexer fileLexer = new Lexer(filePath);
    ArrayList lexicalTokens = fileLexer.analyseFile();
    Executor fileExecutor = new Executor(lexicalTokens);
    HashMap<String, Variable> variableMap = fileExecutor.executeCode();
  }

  private static String fetchFilePath() {
    Scanner inputReader = new Scanner(System.in);

    System.out.println("Please enter the filepath of the file you would like to interpret.");
    String filePath = inputReader.nextLine();

    try {
      Paths.get(filePath);
    } catch (InvalidPathException ex) {
      System.out.println("The filepath you have entered is invalid, please try again.");
      return fetchFilePath();
    }

    return filePath;
  }
}