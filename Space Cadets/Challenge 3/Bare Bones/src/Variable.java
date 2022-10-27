/*

Variable.java
Written by empulsion
- Class that stores the value for a specific variable ID and performs simple methods on it.

CREATED: 24/10/2022
LAST UPDATED: 25/10/2022

*/

public class Variable {
  private Double value;

  public Variable() {
    value = Double.valueOf(0);
  }

  public void clear() {
    value = Double.valueOf(0);
  }

  public void incr() {
    value++;
  }

  public void decr() {
    value--;
  }

  public void setValue(Double newVal) {
    value = newVal;
  }

  public Double getValue() {
    return value;
  }
}