/*

Calculator.java
Written by empulsion
- Performs operations based on a passed array of contents.
- Division -> Multiplication -> Addition -> Subtraction

CREATED: 25/10/2022
LAST UPDATED: 25/10/2022

*/

public class Calculator {
  private String[] expression;

  public Calculator(String[] toCalculate) {
    expression = toCalculate;
  }

  public Float calculate() {
    findOperation("/");
    findOperation("*");
    findOperation("+");
    findOperation("-");

    return Float.valueOf(expression[0]);
  }

  private void findOperation(String operation) {
    for (int i = 0; i < expression.length; i++) {
      if (expression[i].equals(operation)) {
        Float num1 = Float.valueOf(expression[i - 1]);
        Float num2 = Float.valueOf(expression[i + 1]);
        Float result = handleOperation(operation, num1, num2);

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

  private Float handleOperation(String operation, Float num1, Float num2) {
    switch (operation) {
      case "/" -> {return (num1 / num2);}
      case "*" -> {return (num1 * num2);}
      case "+" -> {return (num1 + num2);}
      case "-" -> {return (num1 - num2);}
      default -> {return Float.valueOf(0);}
    }
  }
}