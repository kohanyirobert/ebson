package com.github.kohanyirobert.ebson;

/**
 * Frequently used byte values.
 */
public final class BsonBytes {

  /**
   * EOF (-1).
   */
  public static final byte EOF = -1;

  /**
   * EOO (0).
   */
  public static final byte EOO = 0;

  /**
   * DOUBLE (1).
   */
  public static final byte DOUBLE = 1;

  /**
   * STRING (2).
   */
  public static final byte STRING = 2;

  /**
   * EMBEDDED (3).
   */
  public static final byte EMBEDDED = 3;

  /**
   * ARRAY (4).
   */
  public static final byte ARRAY = 4;

  /**
   * BINARY (5).
   */
  public static final byte BINARY = 5;

  /**
   * GENERIC (0).
   */
  public static final byte GENERIC = 0;

  /**
   * FUNCTION (1).
   */
  public static final byte FUNCTION = 1;

  /**
   * OLD (2).
   */
  public static final byte OLD = 2;

  /**
   * UUID (3).
   */
  public static final byte UUID = 3;

  /**
   * MD5 (5).
   */
  public static final byte MD5 = 5;

  /**
   * USER (128).
   */
  public static final byte USER = (byte) 128;

  /**
   * UNDEFINED (6).
   * 
   * @deprecated See the <a href="http://bsonspec.org/#/">BSON</a> specification
   * for details.
   */
  @Deprecated
  public static final byte UNDEFINED = 6;

  /**
   * OBJECT_ID (7).
   */
  public static final byte OBJECT_ID = 7;

  /**
   * BOOLEAN (8).
   */
  public static final byte BOOLEAN = 8;

  /**
   * FALSE (0).
   */
  public static final byte FALSE = 0;

  /**
   * TRUE (1).
   */
  public static final byte TRUE = 1;

  /**
   * UTC_DATE_TIME (9).
   */
  public static final byte UTC_DATE_TIME = 9;

  /**
   * NULL (10).
   */
  public static final byte NULL = 10;

  /**
   * REGULAR_EXPRESSION (11).
   */
  public static final byte REGULAR_EXPRESSION = 11;

  /**
   * DB_POINTER (12).
   * 
   * @deprecated See the <a
   * href="http://api.mongodb.org/java/1.3/com/mongodb/DBPointer.html">following
   * link</a> for more details.
   */
  @Deprecated
  public static final byte DB_POINTER = 12;

  /**
   * JAVASCRIPT_CODE (13).
   */
  public static final byte JAVASCRIPT_CODE = 13;

  /**
   * SYMBOL (14).
   */
  public static final byte SYMBOL = 14;

  /**
   * JAVASCRIPT_CODE_WITH_SCOPE (15).
   */
  public static final byte JAVASCRIPT_CODE_WITH_SCOPE = 15;

  /**
   * INT32 (16).
   */
  public static final byte INT32 = 16;

  /**
   * TIMESTAMP (17).
   */
  public static final byte TIMESTAMP = 17;

  /**
   * INT64 (18).
   */
  public static final byte INT64 = 18;

  /**
   * MAX_KEY (127).
   */
  public static final byte MAX_KEY = 127;

  /**
   * MIN_KEY (255).
   */
  public static final byte MIN_KEY = (byte) 255;

  private BsonBytes() {}
}
