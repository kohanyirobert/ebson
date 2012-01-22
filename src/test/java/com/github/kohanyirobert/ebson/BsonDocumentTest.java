package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class BsonDocumentTest {

  private static final String STRING_KEY = "string-key";
  private static final String NULL_KEY = "null-key";
  private static final String MISSING_KEY = "string-key-not-present";

  private static final Object STRING_VALUE = "string-value";

  private final BsonDocument document;

  public BsonDocumentTest() {
    document = BsonDocuments.of(
        STRING_KEY, STRING_VALUE,
        NULL_KEY, null);
  }

  @Test
  public void containsKey_withStringKey() {
    assertTrue(document.containsKey(STRING_KEY));
  }

  @Test(expected = ClassCastException.class)
  public void containsKey_withNotStringKey() {
    document.containsKey(new Object());
  }

  @Test
  public void get_keyPresent_withStringKey() {
    assertEquals(STRING_VALUE, document.get(STRING_KEY));
  }

  @Test(expected = IllegalArgumentException.class)
  public void get_keyNotPresent_withStringKey() {
    document.get(MISSING_KEY);
  }

  @Test(expected = ClassCastException.class)
  public void get_keyPresent_withNotStringKey() {
    document.get(new Object());
  }
}
