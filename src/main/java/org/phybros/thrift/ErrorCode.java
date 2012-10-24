/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.phybros.thrift;



/**
 * Various codes used for catching errors
 */
public enum ErrorCode implements org.apache.thrift.TEnum {
  /**
   * If a parameter was invalid
   */
  INVALID_REQUEST(0),
  /**
   * Authentication failed
   */
  INVALID_AUTHSTRING(1),
  /**
   * Requested data could not be found
   */
  NOT_FOUND(2),
  /**
   * Something went wrong during a download operation
   */
  DOWNLOAD_ERROR(3),
  /**
   * Something went wrong during a file operation
   */
  FILE_ERROR(4);

  private final int value;

  private ErrorCode(int value) {
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
  public static ErrorCode findByValue(int value) { 
    switch (value) {
      case 0:
        return INVALID_REQUEST;
      case 1:
        return INVALID_AUTHSTRING;
      case 2:
        return NOT_FOUND;
      case 3:
        return DOWNLOAD_ERROR;
      case 4:
        return FILE_ERROR;
      default:
        return null;
    }
  }
}
