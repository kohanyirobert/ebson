package com.github.kohanyirobert.ebson;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import javax.annotation.Nullable;

/**
 * Representation of a <a href="http://bsonspec.org/">BSON</a> binary
 * (sub-)type.
 */
public enum BsonBinary {

  /**
   * Generic binary.
   * <p>
   * <b>Note:</b> the most commonly used binary sub-type and should be the
   * 'default' for drivers and tools.
   * </p>
   */
  GENERIC(BsonBytes.GENERIC, DefaultPredicate.GENERIC, DefaultReader.GENERIC,
      DefaultWriter.GENERIC),

  /**
   * Function binary.
   */
  FUNCTION(BsonBytes.FUNCTION),

  /**
   * Old binary.
   * <p>
   * <b>Note:</b> this used to be the default subtype, but was deprecated in
   * favor of the generic binary type.
   * </p>
   */
  OLD(BsonBytes.OLD),

  /**
   * UUID binary.
   */
  UUID(BsonBytes.UUID),

  /**
   * MD5 binary.
   */
  MD5(BsonBytes.MD5),

  /**
   * User-defined binary.
   */
  USER(BsonBytes.USER);

  private final byte terminal;

  private Predicate<Class<?>> predicate;
  private BsonReader reader;
  private BsonWriter writer;

  private BsonBinary(byte terminal) {
    this(terminal, Predicates.<Class<?>>alwaysFalse(), null, null);
  }

  private BsonBinary(byte terminal, Predicate<Class<?>> predicate,
      BsonReader reader, BsonWriter writer) {
    this.terminal = terminal;
    this.predicate = predicate;
    this.reader = reader;
    this.writer = writer;
  }

  /**
   * Returns this binary's associated terminal.
   * 
   * @return this binary's associated terminal
   */
  public byte terminal() {
    return terminal;
  }

  /**
   * Returns this binary's associated {@linkplain Predicate predicate}.
   * 
   * @return this binary's associated predicate
   * @throws IllegalStateException if this binary does not have an associated
   * predicate
   */
  public Predicate<Class<?>> predicate() {
    Preconditions.checkState(predicate != null, "'%s' does not have an associated predicate", this);
    return predicate;
  }

  /**
   * Associates {@link Predicate predicate} with this binary.
   * 
   * @param predicate the predicate to be associated with this binary
   */
  public void predicate(Predicate<Class<?>> predicate) {
    Preconditions.checkNotNull(predicate, "cannot associate a null predicate with '%s'", this);
    this.predicate = predicate;
  }

  /**
   * Returns this binary's associated {@linkplain BsonReader reader}.
   * 
   * @return this binary's associated reader
   * @throws IllegalStateException if this binary does not have an associated
   * reader
   */
  public BsonReader reader() {
    Preconditions.checkState(reader != null, "'%s' does not have an associated reader", this);
    return reader;
  }

  /**
   * Associates {@link BsonReader reader} with this binary.
   * 
   * @param reader the reader to be associated with this binary
   */
  public void reader(BsonReader reader) {
    Preconditions.checkNotNull(reader, "cannot associate a null reader with '%s'", this);
    this.reader = reader;
  }

  /**
   * Returns this binary's associated {@linkplain BsonWriter writer}.
   * 
   * @return this binary's associated writer
   * @throws IllegalStateException if this binary does not have an associated
   * writer
   */
  public BsonWriter writer() {
    Preconditions.checkState(writer != null, "'%s' does not have an associated writer", this);
    return writer;
  }

  /**
   * Associates {@link BsonWriter writer} with this binary.
   * 
   * @param writer the writer to be associated with this binary
   */
  public void writer(BsonWriter writer) {
    Preconditions.checkNotNull(writer, "cannot associate a null writer with '%s'", this);
    this.writer = writer;
  }

  /**
   * Returns the binary representing {@code clazz}.
   * 
   * @param clazz the class to return a binary representation for
   * @return the binary representing {@code clazz}
   * @throws IllegalArgumentException if no binary representing {@code clazz}
   * was found
   */
  public static BsonBinary find(@Nullable Class<?> clazz) {
    for (BsonBinary binary : values()) {
      if (binary.predicate().apply(clazz)) {
        return binary;
      }
    }
    throw new IllegalArgumentException(String.format("no binary "
        + "representing the '%s' type value was found", clazz));
  }

  /**
   * Returns the binary representing {@code terminal}.
   * 
   * @param terminal the terminal to return a binary representation for
   * @return the binary representing {@code terminal}
   * @throws IllegalArgumentException if no binary representing {@code terminal}
   * was found
   */
  public static BsonBinary find(byte terminal) {
    for (BsonBinary binary : values()) {
      if (binary.terminal() - terminal == 0) {
        return binary;
      }
    }
    throw new IllegalArgumentException(String.format("no binary representing "
        + "the '%s' terminal value was found", Byte.valueOf(terminal)));
  }
}
