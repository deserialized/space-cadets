import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/*

Executor.java
Written by empulsion
- Executes code based off passed lexical tokens.

CREATED: 24/10/2022
LAST UPDATED: 26/10/2022

*/

public class Executor {
  private Integer pointer = 0;
  private final ArrayList<String[]> tokens;
  private HashMap<String, Variable> variableMap = new HashMap<>();
  private Stack<Integer> callstack = new Stack<>();

  public Executor(ArrayList lexicalTokens) {
    tokens = lexicalTokens;
  }

  public HashMap executeCode() {
    Integer lastPointer = -1;

    while (pointer < tokens.size()) {
      String[] functionData = tokens.get(pointer);
      Integer newPointer = executeLine(functionData);

      if (!lastPointer.equals(pointer)) {
        System.out.println("Executed line #" + (pointer + 1) + ": ");
        variableMap.forEach((k, v) -> {System.out.println(k + ": " + v.getValue());});
      }

      lastPointer = pointer.intValue();
      if (newPointer == -1) {
        pointer++;
      } else {
        pointer = newPointer;
      }
    }

    return variableMap;
  }

  private Integer executeLine(String[] functionData) {
    // Ensures that any 1 word statements, such as end, aren't processed by the calculator or executor.
    if (functionData.length > 2) {
      functionData = performOperations(functionData);
      executeFunction(functionData[1], functionData[2]);
    }

    // NOTE: handlePointer should always be the last function call for a block of data.
    return handlePointer(functionData);
  }

  private String[] performOperations(String[] functionData) {
    if (functionData[2].equals("=") && variableMap.containsKey(functionData[1])) {
      String[] expression = new String[functionData.length - 3];
      System.arraycopy(functionData, 3, expression, 0, (functionData.length - 3));

      for (Integer i = 0; i < expression.length; i++) {
        String subExpression = expression[i];
        if (variableMap.containsKey(subExpression)) {
          expression[i] = "" + variableMap.get(subExpression).getValue();
        }
      }

      if (expression.length == 1) {
        variableMap.get(functionData[1]).setValue(Double.valueOf(functionData[3]));
      } else {
        Calculator newCalc = new Calculator(expression);
        variableMap.get(functionData[1]).setValue(newCalc.calculate());
      }
    }

    return functionData;
  }

  private void executeFunction(String function, String varId) {
    switch (function) {
      case "clear" -> {fetchVariable(varId).clear();}
      case "incr" -> {fetchVariable(varId).incr();}
      case "decr" -> {fetchVariable(varId).decr();}
      case "input" -> {
        Scanner inputScanner = new Scanner(System.in);
        try {
          fetchVariable(varId).setValue(Double.parseDouble(inputScanner.nextLine()));
        } catch (Exception ex) {
          System.out.println("Invalid user input. A value of type double is required.");
          System.exit(0);
        }
      }
      case "output" -> {System.out.println(varId);}
    }
  }

  private Integer handlePointer(String[] functionData) {
    if (functionData[1].equals("while")) {
      Boolean endLoop = false;

      switch (functionData[3]) {
        case "not" -> {
          if (variableMap.get(functionData[2]).getValue().equals(Double.valueOf(functionData[4]))) {endLoop = true;}
        }
        case "=" -> {
          if (!variableMap.get(functionData[2]).getValue().equals(Double.valueOf(functionData[4]))) {endLoop = true;}
        }
        case ">" -> {
          if (variableMap.get(functionData[2]).getValue() <= Double.parseDouble(functionData[4])) {endLoop = true;}
        }
        case "<" -> {
          if (variableMap.get(functionData[2]).getValue() >= Double.parseDouble(functionData[4])) {endLoop = true;}
        }
        case ">=" -> {
          if (variableMap.get(functionData[2]).getValue() < Double.parseDouble(functionData[4])) {endLoop = true;}
        }
        case "<=" -> {
          if (variableMap.get(functionData[2]).getValue() > Double.parseDouble(functionData[4])) {endLoop = true;}
        }
      }

      if (endLoop) {
        Integer nestedEnds = 0;

        if (!callstack.isEmpty() && callstack.peek().equals(pointer)) {callstack.pop();}

        while (!nestedEnds.equals(-1)) {
          pointer++;

          switch (tokens.get(pointer)[1]) {
            case "while" -> {nestedEnds++;}
            case "end" -> {nestedEnds--;}
          }
        }
      } else {
        if (callstack.empty() || !callstack.peek().equals(pointer)) {
          callstack.push(pointer);
        }
      }

      return -1;
    } else if (functionData[1].equals("end")) {
      return callstack.peek();
    } else {
      return -1;
    }
  }

  private Variable fetchVariable(String id) {
    if (variableMap.containsKey(id)) {
      return variableMap.get(id);
    } else {
      Variable newVar = new Variable();
      variableMap.put(id, newVar);
      return newVar;
    }
  }
}