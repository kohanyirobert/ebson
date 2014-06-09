package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class BsonDocumentTest {

  private static final String STRING_KEY = "string-key";
  private static final String BINARY_KEY = "binary-key";
  private static final String NULL_KEY = "null-key";
  private static final String MISSING_KEY = "string-key-not-present";

  private static final Object STRING_VALUE = "string-value";
  private static final Object BINARY_VALUE1 = new byte[] {42};
  private static final Object BINARY_VALUE2 = new byte[] {42};

  private final BsonDocument document1;
  private final BsonDocument document2;

  public BsonDocumentTest() {
    document1 = BsonDocuments.of(
        STRING_KEY, STRING_VALUE,
        BINARY_KEY, BINARY_VALUE1,
        NULL_KEY, null);

    document2 = BsonDocuments.of(
        STRING_KEY, STRING_VALUE,
        BINARY_KEY, BINARY_VALUE2,
        NULL_KEY, null);
  }

  @Test
  public void containsKey_withStringKey() {
    assertTrue(document1.containsKey(STRING_KEY));
  }

  @Test(expected = ClassCastException.class)
  public void containsKey_withNotStringKey() {
    document1.containsKey(new Object());
  }

  @Test
  public void get_keyPresent_withStringKey() {
    assertEquals(STRING_VALUE, document1.get(STRING_KEY));
  }

  @Test(expected = IllegalArgumentException.class)
  public void get_keyNotPresent_withStringKey() {
    document1.get(MISSING_KEY);
  }

  @Test(expected = ClassCastException.class)
  public void get_keyPresent_withNotStringKey() {
    document1.get(new Object());
  }
}
