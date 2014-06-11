package com.github.kohanyirobert.ebson;

import java.nio.ByteBuffer;

import javax.annotation.Nullable;

/**
 * Writes Java objects to {@linkplain ByteBuffer buffers}, serialized to bytes
 * as specified by the <a href="http://bsonspec.org/">BSON</a> specification.
 * <p>
 * <b>Note:</b> buffers supplied to {@linkplain #writeTo} must use little-endian
 * byte ordering.
 * </p>
 */
public interface BsonWriter {

  /**
   * Writes {@code reference} to {@code buffer}.
   * 
   * @param buffer the buffer {@code reference} will be written to
   * @param reference the reference to be written into {@code buffer}
   * @throws NullPointerException if {@code buffer} is null
   * @throws IllegalArgumentException if {@code buffer} is not using
   * little-endian byte ordering
   */
  void writeTo(ByteBuffer buffer, @Nullable Object reference);
  
  /**
   * Returns the size of the reference object
   * @param reference the object to return the size of
   * @return the computed size
   */
  int getSize(@Nullable Object reference);
}
