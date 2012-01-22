package com.github.kohanyirobert.ebson;

import com.google.common.collect.ImmutableMap;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import org.bson.BSON;
import org.bson.BasicBSONObject;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

public final class BsonEncodingTest extends AbstractBsonTest {

  private static final String KEY = "key";

  private final BsonWriter writer;

  public BsonEncodingTest() {
    writer = BsonToken.DOCUMENT.writer();
  }

  @Test
  public void emptyDocument() {
    assertEncoding(ImmutableMap.of());
  }

  @Test
  public void simpleDocument() {
    assertEncoding(ImmutableMap.of(KEY, ""));
  }

  @Test
  public void simpleEmbeddedDocument() {
    assertEncoding(ImmutableMap.of(KEY, ImmutableMap.of()));
  }

  @Test
  public void complexDocument() {
    assertEncoding(ImmutableMap.builder()
        .put("string", "value1")
        .put("double", Double.valueOf(-1))
        .put("int32", Integer.valueOf(0))
        .put("int64", Long.valueOf(1))
        .put("binary", new byte[] {0, 1, 2})
        .put("embedded", ImmutableMap.of())
        .put("utc_date_time", new Date())
        .put("regular_expression", Pattern.compile("^fourty-two$", Pattern.CASE_INSENSITIVE))
        .put("array", new String[] {"one", "two", "...", "fourty-two"})
        .put("collection", Arrays.asList(
            "Ford Prefect",
            "Arthur Dent",
            "Zaphod Beeblebrox",
            "Trillian",
            "Marvin")).build());
  }

  private void assertEncoding(Object actualWrite) {
    ByteBuffer buffer = BUFFER.get();
    buffer.clear();
    writer.writeTo(buffer, actualWrite);
    buffer.flip();
    byte[] actualBytes = new byte[buffer.remaining()];
    buffer.get(actualBytes);
    byte[] expectedBytes = BSON.encode(new BasicBSONObject((Map<?, ?>) actualWrite));
    assertArrayEquals(expectedBytes, actualBytes);
  }
}
