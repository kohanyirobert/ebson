package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Date;

public final class DefaultUtcDateTimeReaderWriterTest extends AbstractReaderWriterTest {

  public DefaultUtcDateTimeReaderWriterTest() {
    super(DefaultReader.UTC_DATE_TIME, DefaultWriter.UTC_DATE_TIME);
  }

  @Test
  public void currentDate() {
    assertEquals(writeTo(new Date()), readFrom());
  }
}
