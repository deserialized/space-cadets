import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;
import java.io.File;

public class Interpreter {
  private static HashMap<String, Integer> variableMap = new HashMap<String, Integer>();
  private static ArrayList<String[]> processedCode = new ArrayList<String[]>();
  private static Stack<Integer> pointerStack = new Stack<Integer>();

  public static void main(String[] args) {
    String filePath = fetchFilePath();
    interpretFile(filePath);
    executeCode();

    variableMap.forEach((k, v) -> System.out.println(k + ": " + v));
  }

  public static String fetchFilePath() {
    Scanner inputScanner = new Scanner(System.in);

    System.out.println("Please enter the name of the file you would like to interpret (does not require extension).");
    String fileName = inputScanner.nextLine();

    return "toInterpret/" + fileName + ".txt";
  }

  public static void interpretFile(String filePath) {
    Scanner fileScanner;

    try {
      fileScanner = new Scanner(new File(filePath));
    } catch (Exception err) {
      System.out.println("An invalid filename has been provided.");
      return;
    }

    while(fileScanner.hasNextLine()) {
      String[] lineData = interpretLine(fileScanner.nextLine());
      processedCode.add(lineData);
    }
  }

  public static void executeCode() {
    Integer pointer = 0;
    Integer nestedLoops = 0;
    Boolean loopEnded = false;

    while(pointer < processedCode.size()) {
      String[] lineData = processedCode.get(pointer);
      String function = lineData[0];

      if (!loopEnded) {
        if (function.equals("clear")) {
          variableMap.put(lineData[1], 0);
        } else if (function.equals("incr")) {
          variableMap.replace(lineData[1], variableMap.get(lineData[1]) + 1);
        } else if (function.equals("decr")) {
          variableMap.replace(lineData[1], variableMap.get(lineData[1]) - 1);
        }
      }

      if (function.equals("while")) {
        // check if the loop condition is met
        if (!loopEnded) {
          if (variableMap.get(lineData[1]) != 0) {
            if (pointerStack.isEmpty() || pointerStack.peek() != pointer) {
              pointerStack.push(pointer);
            }
          } else {
            if (pointerStack.peek() == pointer) {
              pointerStack.pop();
            }
            loopEnded = true;
          }
        } else {
          nestedLoops += 1;
        }
        pointer += 1;
      } else if (function.equals("end")) {
        if (!loopEnded) {
          if (pointerStack.size() > 0) {
            pointer = pointerStack.peek();
          } else {
            pointer += 1;
          }
        } else {
          if (nestedLoops > 0) {
            nestedLoops -= 1;
          } else {
            loopEnded = false;
          }
          pointer += 1;
        }
      } else {
        // increment pointer for any exceptions
        pointer += 1;
      }
    }
  }

  public static String[] interpretLine(String line) {
    String[] segments = line.replace(";", "").trim().split(" ");
    return segments;
  }
}