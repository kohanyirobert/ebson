package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class DefaultBooleanReaderWriterTest extends AbstractReaderWriterTest {

  public DefaultBooleanReaderWriterTest() {
    super(DefaultReader.BOOLEAN, DefaultWriter.BOOLEAN);
  }

  @Test
  public void trueValue() {
    assertEquals(writeTo(Boolean.TRUE), readFrom());
  }

  @Test
  public void falseValue() {
    assertEquals(writeTo(Boolean.FALSE), readFrom());
  }
}
