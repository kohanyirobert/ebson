package com.github.kohanyirobert.ebson;

import com.google.common.base.Objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.xml.bind.DatatypeConverter;

final class BasicTimestamp implements BsonTimestamp {

  private static final int TIME_LENGTH = 4;
  private static final int INCREMENT_LENGTH = 4;
  private static final int TIMESTAMP_LENGTH = TIME_LENGTH + INCREMENT_LENGTH;

  private final ByteBuffer time;
  private final ByteBuffer increment;

  private final ByteBuffer timestamp = ByteBuffer.allocate(TIMESTAMP_LENGTH).order(ByteOrder.LITTLE_ENDIAN);

  BasicTimestamp(ByteBuffer buffer) {
    int oldPosition = buffer.position();

    int oldLimit = buffer.limit();
    buffer.limit(buffer.position() + TIMESTAMP_LENGTH);
    timestamp.put(buffer).flip();
    buffer.limit(oldLimit);

    assert buffer.position() == oldPosition + TIMESTAMP_LENGTH;

    time = ByteBuffer.wrap(timestamp.array(), 0, TIME_LENGTH).order(ByteOrder.LITTLE_ENDIAN);
    increment = ByteBuffer.wrap(timestamp.array(), INCREMENT_LENGTH, TIME_LENGTH).order(ByteOrder.LITTLE_ENDIAN);
  }

  @Override
  public ByteBuffer timestamp() {
    return timestamp.asReadOnlyBuffer();
  }

  @Override
  public ByteBuffer time() {
    return time.asReadOnlyBuffer();
  }

  @Override
  public ByteBuffer increment() {
    return increment.asReadOnlyBuffer();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(timestamp);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof BsonTimestamp) {
      BsonTimestamp other = (BsonTimestamp) object;
      return timestamp.equals(other.timestamp());
    }
    return false;
  }

  @Override
  public String toString() {
    return DatatypeConverter.printHexBinary(timestamp.array()).toLowerCase();
  }
}
