/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.phybros.thrift;



/**
 * Valid game modes
 */
public enum GameMode implements org.apache.thrift.TEnum {
  /**
   * Survival Mode
   */
  SURVIVAL(0),
  /**
   * Creative Mode
   */
  CREATIVE(1),
  /**
   * Adventure Mode
   */
  ADVENTURE(2);

  private final int value;

  private GameMode(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static GameMode findByValue(int value) { 
    switch (value) {
      case 0:
        return SURVIVAL;
      case 1:
        return CREATIVE;
      case 2:
        return ADVENTURE;
      default:
        return null;
    }
  }
}
