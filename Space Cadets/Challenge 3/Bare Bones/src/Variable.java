/*

Variable.java
Written by empulsion
- Class that stores the value for a specific variable ID and performs simple methods on it.

CREATED: 24/10/2022
LAST UPDATED: 25/10/2022

*/

public class Variable {
  private Float value;

  public Variable() {
    value = Float.valueOf(0);
  }

  public void clear() {
    value = Float.valueOf(0);
  }

  public void incr() {
    value++;
  }

  public void decr() {
    value--;
  }

  public void setValue(Float newVal) {
    value = newVal;
  }

  public Float getValue() {
    return value;
  }
}