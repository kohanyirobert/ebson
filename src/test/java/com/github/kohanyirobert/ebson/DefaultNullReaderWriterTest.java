package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class DefaultNullReaderWriterTest extends AbstractReaderWriterTest {

  public DefaultNullReaderWriterTest() {
    super(DefaultReader.NULL, DefaultWriter.NULL);
  }

  @Test
  public void nullValue() {
    assertEquals(writeTo(null), readFrom());
  }
}
