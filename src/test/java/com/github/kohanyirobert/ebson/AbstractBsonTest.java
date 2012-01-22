package com.github.kohanyirobert.ebson;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class AbstractBsonTest {

  protected static final int MAX_BSON_SIZE = 16 * 1024 * 1024;

  protected static final ThreadLocal<ByteBuffer> BUFFER = new ThreadLocal<ByteBuffer>() {

    @Override
    protected ByteBuffer initialValue() {
      return ByteBuffer.allocate(MAX_BSON_SIZE).order(ByteOrder.LITTLE_ENDIAN);
    }
  };
}
