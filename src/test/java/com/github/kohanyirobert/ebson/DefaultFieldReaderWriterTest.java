package com.github.kohanyirobert.ebson;

import com.google.common.collect.Maps;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class DefaultFieldReaderWriterTest extends AbstractReaderWriterTest {

  public DefaultFieldReaderWriterTest() {
    super(DefaultReader.FIELD, DefaultWriter.FIELD);
  }

  @Test
  public void simpleField() {
    assertEquals(writeTo(Maps.immutableEntry("key", null)), readFrom());
  }
}
