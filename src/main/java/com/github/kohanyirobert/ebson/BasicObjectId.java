package com.github.kohanyirobert.ebson;

import com.google.common.base.Objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.xml.bind.DatatypeConverter;

final class BasicObjectId implements BsonObjectId {

  private static final int TIME_LENGTH = 4;
  private static final int MACHINE_ID_LENGTH = 3;
  private static final int PROCESS_ID_LENGTH = 2;
  private static final int INCREMENT_LENGTH = 3;
  private static final int OBJECT_ID_LENGTH =
      TIME_LENGTH + MACHINE_ID_LENGTH + PROCESS_ID_LENGTH + INCREMENT_LENGTH;

  private final ByteBuffer time;
  private final ByteBuffer machineId;
  private final ByteBuffer processId;
  private final ByteBuffer increment;

  private final ByteBuffer objectId = ByteBuffer.allocate(OBJECT_ID_LENGTH).order(ByteOrder.BIG_ENDIAN);

  BasicObjectId(ByteBuffer buffer) {
    int oldPosition = buffer.position();

    int oldLimit = buffer.limit();
    buffer.limit(buffer.position() + OBJECT_ID_LENGTH);
    objectId.put(buffer).flip();
    buffer.limit(oldLimit);

    assert buffer.position() == oldPosition + OBJECT_ID_LENGTH;

    time = ByteBuffer.wrap(objectId.array(), 0, TIME_LENGTH).order(ByteOrder.BIG_ENDIAN);
    machineId = ByteBuffer.wrap(objectId.array(), TIME_LENGTH, MACHINE_ID_LENGTH).order(ByteOrder.LITTLE_ENDIAN);
    processId = ByteBuffer.wrap(objectId.array(), MACHINE_ID_LENGTH, PROCESS_ID_LENGTH).order(ByteOrder.LITTLE_ENDIAN);
    increment = ByteBuffer.wrap(objectId.array(), PROCESS_ID_LENGTH, INCREMENT_LENGTH).order(ByteOrder.BIG_ENDIAN);
  }

  @Override
  public ByteBuffer objectId() {
    return objectId.asReadOnlyBuffer();
  }

  @Override
  public ByteBuffer time() {
    return time.asReadOnlyBuffer();
  }

  @Override
  public ByteBuffer machineId() {
    return machineId.asReadOnlyBuffer();
  }

  @Override
  public ByteBuffer processId() {
    return processId.asReadOnlyBuffer();
  }

  @Override
  public ByteBuffer increment() {
    return increment.asReadOnlyBuffer();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(objectId);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof BsonObjectId) {
      BsonObjectId other = (BsonObjectId) object;
      return objectId.equals(other.objectId());
    }
    return false;
  }

  @Override
  public String toString() {
    return DatatypeConverter.printHexBinary(objectId.array()).toLowerCase();
  }
}
