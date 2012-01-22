package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class DefaultKeyReaderWriterTest extends AbstractReaderWriterTest {

  private static final String ASCII = "arvizturotukorfurogep";
  private static final String NON_ASCII = "árvíztűrőtükörfúrógép";

  public DefaultKeyReaderWriterTest() {
    super(DefaultReader.KEY, DefaultWriter.KEY);
  }

  @Test
  public void asciiString() {
    assertEquals(writeTo(ASCII), readFrom());
  }

  @Test
  public void nonAsciiString() {
    assertEquals(writeTo(NON_ASCII), readFrom());
  }
}
