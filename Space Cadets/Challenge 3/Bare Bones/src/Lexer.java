import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;

/*

Lexer.java
Written by empulsion
- Performs lexical analysis on Bare Bones esoslang to prepare it for execution.

CREATED: 24/10/2022
LAST UPDATED: 25/10/2022

*/

public class Lexer {
  private final String path;
  private ArrayList<String[]> lexicalTokens = new ArrayList<>();

  public Lexer(String filePath) {
    path = filePath;
  }

  public ArrayList analyseFile() {
    File file = new File(path);
    processFile(file);
    adjustScope();

    return lexicalTokens;
  }

  private void processFile(File file) {
    Scanner fileScanner;

    try {
      fileScanner = new Scanner(file);
    } catch (FileNotFoundException ex) {
      System.out.println(ex.getMessage());
      System.exit(0);
      return;
    }

    while (fileScanner.hasNextLine()) {
      String line = fileScanner.nextLine();
      processLine(line);
    }
  }

  private void processLine(String line) {
    String[] functionArray = line.split(";");

    for (String dataString : functionArray) {
      dataString = dataString.trim();
      dataString = "0 " + dataString;
      String[] functionData = dataString.split(" ");
      lexicalTokens.add(functionData);
    }
  }

  private void adjustScope() {
    lexicalTokens.forEach(functionData -> {
      // TODO: Handle scoping. Use index 0 of functionData to store scope.
    });
  }
}