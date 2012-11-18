package com.github.kohanyirobert.ebson;

import java.nio.ByteBuffer;

import javax.annotation.Nullable;

/**
 * Reads Java objects from {@linkplain ByteBuffer buffers}, deserialized from
 * bytes as specified by the <a href="http://bsonspec.org/">BSON</a>
 * specification.
 * <p>
 * <b>Note:</b> buffers supplied to {@linkplain #readFrom} must use
 * little-endian byte ordering.
 * </p>
 */
public interface BsonReader {

  /**
   * Reads an arbitrary amount of bytes from {@code buffer} and
   * constructs a new object from what was read.
   * 
   * @param buffer the buffer to read from
   * @return a new object from the bytes read from {@code buffer}
   * @throws NullPointerException if {@code buffer} is null
   * @throws IllegalArgumentException if {@code buffer} is not using
   * little-endian byte ordering
   */
  @Nullable
  Object readFrom(ByteBuffer buffer);
}
