/*

Calculator.java
Written by empulsion
- Performs operations based on a passed array of contents.
- Indices -> Division -> Multiplication -> Addition -> Subtraction

CREATED: 25/10/2022
LAST UPDATED: 25/10/2022

*/

public class Calculator {
  private String[] expression;

  public Calculator(String[] toCalculate) {
    expression = toCalculate;
  }

  public Double calculate() {
    findOperation("^");
    findOperation("/");
    findOperation("*");
    findOperation("+");
    findOperation("-");

    return Double.valueOf(expression[0]);
  }

  private void findOperation(String operation) {
    for (int i = 0; i < expression.length; i++) {
      if (expression[i].equals(operation)) {
        Double num1 = Double.valueOf(expression[i - 1]);
        Double num2 = Double.valueOf(expression[i + 1]);
        Double result = handleOperation(operation, num1, num2);

        expression[i + 1] = result.toString();
        String[] newExpression = new String[expression.length - 2];

        int highestIndex = 0;
        for (int i2 = 0; i2 < expression.length; i2++) {
          if (i2 != i && i2 != i - 1) {
            newExpression[highestIndex] = expression[i2];
            highestIndex++;
          }
        }
        expression = newExpression.clone();

        findOperation(operation);
        break;
      }
    }
  }

  private Double handleOperation(String operation, Double num1, Double num2) {
    switch (operation) {
      case "^" -> {return Math.pow(num1, num2);}
      case "/" -> {return (num1 / num2);}
      case "*" -> {return (num1 * num2);}
      case "+" -> {return (num1 + num2);}
      case "-" -> {return (num1 - num2);}
      default -> {return Double.valueOf(0);}
    }
  }
}