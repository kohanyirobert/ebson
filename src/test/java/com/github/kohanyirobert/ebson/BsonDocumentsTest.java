package com.github.kohanyirobert.ebson;

import com.google.common.collect.Maps;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("static-method")
public final class BsonDocumentsTest extends AbstractBsonTest {

  private static final String KEY1 = "key1";
  private static final String KEY2 = "key2";
  private static final String KEY3 = "key3";

  public BsonDocumentsTest() {}

  @Test
  public void of_nothing_shouldBeEmpty() {
    BsonDocument empty = BsonDocuments.of();
    assertTrue(empty.keySet().isEmpty());
    assertTrue(empty.values().isEmpty());
  }

  @Test
  public void of_nothing_shouldEqualOtherEmptyDocuments() {
    BsonDocument empty = BsonDocuments.of();
    assertEquals(empty, BsonDocuments.of());
    assertEquals(empty, BsonDocuments.copyOf(empty));
    assertEquals(empty, BsonDocuments.copyOf(Maps.<String, Object>newLinkedHashMap()));
  }

  @Test(expected = NullPointerException.class)
  public void copyOf_document_withNullDocument() {
    BsonDocuments.copyOf((BsonDocument) null);
  }

  @Test
  public void copyOf_map_withLegalKeys() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put(KEY1, null);
    map.put(KEY2, null);
    map.put(KEY3, null);
    BsonDocument document = BsonDocuments.copyOf(map);
    assertArrayEquals(map.keySet().toArray(), document.keySet().toArray());
    assertArrayEquals(map.values().toArray(), document.values().toArray());
  }

  @Test(expected = NullPointerException.class)
  public void copyOf_map_withNullMap() {
    BsonDocuments.copyOf((Map<String, Object>) null);
  }

  @Test(expected = NullPointerException.class)
  public void copyOf_map_withNullKey() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put(KEY1, null);
    map.put(KEY2, null);
    map.put(null, null);
    BsonDocuments.copyOf(map);
  }

  @Test
  public void of_singleKeyValuePair_withLegalKey() {
    BsonDocument document = BsonDocuments.of(KEY1, null);
    assertTrue(document.keySet().contains(KEY1));
  }

  @Test(expected = NullPointerException.class)
  public void of_singleKeyValuePair_withNullKey() {
    BsonDocuments.of(null, null);
  }

  @Test
  public void of_multipleKeyValuePairs_withLegalKeys() {
    BsonDocument document = BsonDocuments.of(KEY1, null, KEY2, null, KEY3, null);
    assertArrayEquals(document.keySet().toArray(), new Object[] {KEY1, KEY2, KEY3});
  }

  @Test(expected = NullPointerException.class)
  public void of_multipleKeyValuePairs_withNullKey() {
    BsonDocuments.of(KEY1, null, null, null, KEY3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void of_multipleKeyValuePairs_withDuplicateKeys() {
    BsonDocuments.of(KEY1, null, KEY1, null, KEY3, null);
  }
}
