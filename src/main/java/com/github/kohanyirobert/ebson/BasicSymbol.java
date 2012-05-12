package com.github.kohanyirobert.ebson;

import com.google.common.base.Objects;
import com.google.common.primitives.Ints;

import java.nio.ByteBuffer;

import javax.xml.bind.DatatypeConverter;

final class BasicSymbol implements BsonSymbol {

  private final ByteBuffer symbol;

  BasicSymbol(ByteBuffer buffer) {
    int oldPosition = buffer.position();

    int symbolLength = buffer.getInt();
    symbol = ByteBuffer.allocate(symbolLength - 1);

    int oldLimit = buffer.limit();
    buffer.limit(buffer.position() + symbolLength - 1);
    symbol.put(buffer).flip();
    buffer.limit(buffer.limit() + 1);
    buffer.get();
    buffer.limit(oldLimit);

    assert buffer.position() == Ints.BYTES + oldPosition + symbolLength;
  }

  @Override
  public ByteBuffer symbol() {
    return symbol.asReadOnlyBuffer();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(symbol);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof BasicSymbol) {
      BasicSymbol other = (BasicSymbol) object;
      return symbol.equals(other.symbol());
    }
    return false;
  }

  @Override
  public String toString() {
    return DatatypeConverter.printHexBinary(symbol.array()).toLowerCase();
  }
}
