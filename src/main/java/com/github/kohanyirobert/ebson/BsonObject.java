package com.github.kohanyirobert.ebson;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import javax.annotation.Nullable;

/**
 * Representation of a <a href="http://bsonspec.org/">BSON</a> object type.
 * 
 * @see BsonToken
 * @see BsonBinary
 */
public enum BsonObject {

  /**
   * 64-bit IEEE 754 floating point.
   */
  DOUBLE(BsonBytes.DOUBLE, DefaultPredicate.DOUBLE, DefaultReader.DOUBLE,
      DefaultWriter.DOUBLE),

  /**
   * UTF-8 string.
   */
  STRING(BsonBytes.STRING, DefaultPredicate.STRING, DefaultReader.STRING,
      DefaultWriter.STRING),

  /**
   * Embedded {@linkplain BsonToken#DOCUMENT document}.
   */
  EMBEDDED(BsonBytes.EMBEDDED, DefaultPredicate.EMBEDDED,
      BsonToken.DOCUMENT.reader(), BsonToken.DOCUMENT.writer()),

  /**
   * Special embedded {@linkplain #EMBEDDED document}.
   * <p>
   * <b>Note:</b> an array is a document whose keys are integer values starting
   * with 0 and continuing sequentially.
   * </p>
   */
  ARRAY(BsonBytes.ARRAY, DefaultPredicate.ARRAY, DefaultReader.ARRAY,
      DefaultWriter.ARRAY),

  /**
   * Binary data.
   */
  BINARY(BsonBytes.BINARY, DefaultPredicate.BINARY, DefaultReader.BINARY,
      DefaultWriter.BINARY),

  /**
   * Undefined.
   * 
   * @deprecated See the <a href="http://bsonspec.org/#/">BSON</a> specification
   * for details.
   */
  @Deprecated
  UNDEFINED(BsonBytes.UNDEFINED),

  /**
   * <a href="http://www.mongodb.org/display/DOCS/Object+IDs">Object ID</a>.
   * <p>
   * <b>Note:</b> special <a href="http://mongodb.org">MongoDB</a> related type.
   * </p>
   */
  OBJECT_ID(BsonBytes.OBJECT_ID),

  /**
   * Boolean.
   * <p>
   * <b>Note:</b> 0 is <em>false</em>; 1 is <em>true</em>.
   * </p>
   */
  BOOLEAN(BsonBytes.BOOLEAN, DefaultPredicate.BOOLEAN, DefaultReader.BOOLEAN,
      DefaultWriter.BOOLEAN),

  /**
   * UTC date-time.
   * <p>
   * <b>Note</b>: milliseconds since the Unix epoch.
   * </p>
   */
  UTC_DATE_TIME(BsonBytes.UTC_DATE_TIME, DefaultPredicate.UTC_DATE_TIME,
      DefaultReader.UTC_DATE_TIME, DefaultWriter.UTC_DATE_TIME),

  /**
   * <em>null</em>.
   */
  NULL(BsonBytes.NULL, DefaultPredicate.NULL, DefaultReader.NULL,
      DefaultWriter.NULL),

  /**
   * Regular expression.
   */
  REGULAR_EXPRESSION(BsonBytes.REGULAR_EXPRESSION, DefaultPredicate.REGULAR_EXPRESSION,
      DefaultReader.REGULAR_EXPRESSION, DefaultWriter.REGULAR_EXPRESSION),

  /**
   * DB pointer.
   * 
   * @deprecated See the <a
   * href="http://api.mongodb.org/java/1.3/com/mongodb/DBPointer.html">following
   * link</a> for more details.
   */
  @Deprecated
  DB_POINTER(BsonBytes.DB_POINTER),

  /**
   * JavaScript code.
   */
  JAVASCRIPT_CODE(BsonBytes.JAVASCRIPT_CODE),

  /**
   * Symbol.
   * <p>
   * <b>Note:</b> similar to a string but for languages with a distinct symbol
   * type.
   * </p>
   */
  SYMBOL(BsonBytes.SYMBOL),

  /**
   * JavaScript code with scope.
   */
  JAVASCRIPT_CODE_WITH_SCOPE(BsonBytes.JAVASCRIPT_CODE_WITH_SCOPE),

  /**
   * 32-bit signed integer.
   */
  INT32(BsonBytes.INT32, DefaultPredicate.INT32, DefaultReader.INT32,
      DefaultWriter.INT32),

  /**
   * <a href="http://www.mongodb.org/display/DOCS/Timestamp+data+type"
   * >Timestamp</a>.
   * <p>
   * <b>Note:</b> special internal type used by MongoDB replication and
   * sharding.
   * </p>
   */
  TIMESTAMP(BsonBytes.TIMESTAMP),

  /**
   * 64-bit signed integer.
   */
  INT64(BsonBytes.INT64, DefaultPredicate.INT64, DefaultReader.INT64,
      DefaultWriter.INT64),

  /**
   * <a href="http://www.mongodb.org/display/DOCS/min+and+max+Query+Specifiers">
   * Max key</a>.
   * <p>
   * <b>Note:</b> special type which compares higher than all other possible
   * {@code BSON} element values.
   * </p>
   */
  MAX_KEY(BsonBytes.MAX_KEY),

  /**
   * <a href="http://www.mongodb.org/display/DOCS/min+and+max+Query+Specifiers">
   * Min key</a>.
   * <p>
   * <b>Note:</b> special type which compares lower than all other possible
   * {@code BSON} element values.
   * </p>
   */
  MIN_KEY(BsonBytes.MIN_KEY);

  private final byte terminal;

  private Predicate<Class<?>> predicate;
  private BsonReader reader;
  private BsonWriter writer;

  private BsonObject(byte terminal) {
    this(terminal, Predicates.<Class<?>>alwaysFalse(), null, null);
  }

  private BsonObject(byte terminal, Predicate<Class<?>> predicate,
      BsonReader reader, BsonWriter writer) {
    this.terminal = terminal;
    this.predicate = predicate;
    this.reader = reader;
    this.writer = writer;
  }

  /**
   * Returns this object's associated terminal.
   * 
   * @return this object's associated terminal
   */
  public byte terminal() {
    return terminal;
  }

  /**
   * Returns this object's associated {@linkplain Predicate predicate}.
   * 
   * @return this object's associated predicate
   * @throws IllegalStateException if this object does not have an associated
   * predicate
   */
  public Predicate<Class<?>> predicate() {
    Preconditions.checkState(predicate != null, "'%s' does not have an associated predicate", this);
    return predicate;
  }

  /**
   * Associates {@link Predicate predicate} with this object.
   * 
   * @param predicate the predicate to be associated with this object
   */
  public void predicate(Predicate<Class<?>> predicate) {
    Preconditions.checkNotNull(predicate, "cannot associate a null predicate with '%s'", this);
    this.predicate = predicate;
  }

  /**
   * Returns this object's associated {@linkplain BsonReader reader}.
   * 
   * @return this object's associated reader
   * @throws IllegalStateException if this object does not have an associated
   * reader
   */
  public BsonReader reader() {
    Preconditions.checkState(reader != null, "'%s' does not have an associated reader", this);
    return reader;
  }

  /**
   * Associates {@link BsonReader reader} with this object.
   * 
   * @param reader the reader to be associated with this object
   */
  public void reader(BsonReader reader) {
    Preconditions.checkNotNull(predicate, "cannot associate a null reader with '%s'", this);
    this.reader = reader;
  }

  /**
   * Returns this object's associated {@linkplain BsonWriter writer}.
   * 
   * @return this object's associated writer
   * @throws IllegalStateException if this object does not have an associated
   * writer
   */
  public BsonWriter writer() {
    Preconditions.checkState(writer != null, "'%s' does not have an associated writer", this);
    return writer;
  }

  /**
   * Associates {@link BsonWriter writer} with this object.
   * 
   * @param writer the writer to be associated with this object
   */
  public void writer(BsonWriter writer) {
    Preconditions.checkNotNull(writer, "cannot associate a null writer with '%s'", this);
    this.writer = writer;
  }

  /**
   * Returns the object representing {@code clazz}.
   * 
   * @param clazz the class to return a object representation for
   * @return the object representing {@code clazz}
   * @throws IllegalArgumentException if no object representing {@code clazz}
   * was found
   */
  public static BsonObject find(@Nullable Class<?> clazz) {
    for (BsonObject object : values())
      if (object.predicate().apply(clazz))
        return object;
    throw new IllegalArgumentException(String.format("no object "
        + "representing the '%s' type value was found", clazz));
  }

  /**
   * Returns the object representing {@code terminal}.
   * 
   * @param terminal the terminal to return a object representation for
   * @return the object representing {@code terminal}
   * @throws IllegalArgumentException if no object representing {@code terminal}
   * was found
   */
  public static BsonObject find(byte terminal) {
    for (BsonObject object : values())
      if (object.terminal() - terminal == 0)
        return object;
    throw new IllegalArgumentException(String.format("no object representing "
        + "the '%s' terminal value was found", Byte.valueOf(terminal)));
  }
}
