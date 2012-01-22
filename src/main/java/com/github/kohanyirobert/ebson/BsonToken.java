package com.github.kohanyirobert.ebson;

import com.google.common.base.Preconditions;

/**
 * Representation of a <a href="http://bsonspec.org/">BSON</a> token type.
 */
public enum BsonToken {

  /**
   * <a href="http://bsonspec.org/">BSON</a> document.
   */
  DOCUMENT(DefaultReader.DOCUMENT, DefaultWriter.DOCUMENT),

  /**
   * Key-value pair in a {@linkplain #DOCUMENT document}.
   */
  FIELD(DefaultReader.FIELD, DefaultWriter.FIELD),

  /**
   * Key in a {@linkplain #FIELD field} ({@code \0} delimited UTF-8 string).
   */
  KEY(DefaultReader.KEY, DefaultWriter.KEY);

  private BsonReader reader;
  private BsonWriter writer;

  private BsonToken(BsonReader reader, BsonWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  private BsonToken() {}

  /**
   * Returns this token's associated {@linkplain BsonReader reader}.
   * 
   * @return this token's associated reader
   * @throws IllegalStateException if this token does not have an associated
   * reader
   */
  public BsonReader reader() {
    Preconditions.checkState(reader != null, "'%s' does not have an associated reader", this);
    return reader;
  }

  /**
   * Associates {@link BsonReader reader} with this token.
   * 
   * @param reader the reader to be associated with this token
   */
  public void reader(BsonReader reader) {
    Preconditions.checkNotNull(reader, "cannot associate a null reader with '%s'", this);
    this.reader = reader;
  }

  /**
   * Returns this token's associated {@linkplain BsonWriter writer}.
   * 
   * @return this token's associated writer
   * @throws IllegalStateException if this token does not have an associated
   * writer
   */
  public BsonWriter writer() {
    Preconditions.checkState(writer != null, "'%s' does not have an associated writer", this);
    return writer;
  }

  /**
   * Associates {@link BsonWriter writer} with this token.
   * 
   * @param writer the writer to be associated with this token
   */
  public void writer(BsonWriter writer) {
    Preconditions.checkNotNull(reader, "cannot associate a null writer with '%s'", this);
    this.writer = writer;
  }
}
