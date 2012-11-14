package com.github.kohanyirobert.ebson;

import com.google.common.base.Charsets;
import com.google.common.primitives.Ints;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DefaultSymbolReaderWriterTest extends AbstractReaderWriterTest {

  private static final String SYMBOL_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
  private static final int MAX_SYMBOL_LENGTH = 1024;
  private static final ThreadLocal<ByteBuffer> RANDOM_SYMBOL = new ThreadLocal<ByteBuffer>() {

    @Override
    protected ByteBuffer initialValue() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < BsonRandom.nextInt(MAX_SYMBOL_LENGTH); i++) {
        sb.append(SYMBOL_ALPHABET.charAt(BsonRandom.nextInt(SYMBOL_ALPHABET.length())));
      }
      ByteBuffer symbol = ByteBuffer.allocate(Ints.BYTES + sb.length() + 1)
          .order(ByteOrder.LITTLE_ENDIAN)
          .putInt(sb.length() + 1)
          .put(sb.toString().getBytes(Charsets.UTF_8))
          .put(BsonBytes.EOO);
      symbol.flip();
      return symbol;
    }
  };

  public DefaultSymbolReaderWriterTest() {
    super(DefaultReader.SYMBOL, DefaultWriter.SYMBOL);
  }

  @Test
  public void randomSymbol() {
    assertEquals(writeTo(new BasicSymbol(RANDOM_SYMBOL.get())), readFrom());
  }
}
