package com.github.kohanyirobert.ebson;

import java.nio.ByteBuffer;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Utility class for working with {@linkplain BsonDocument documents}.
 */
public final class BsonDocuments {

  private BsonDocuments() {}

  /**
   * Reads a new document from {@code buffer}.
   * 
   * @param buffer the buffer that contains a document's serialized data
   * @return a new document from {@code buffer}
   * @throws NullPointerException if {@code buffer} is null
   * @throws IllegalArgumentException if {@code buffer} is not using
   * little-endian byte ordering
   */
  public static BsonDocument readFrom(ByteBuffer buffer) {
    return (BsonDocument) BsonToken.DOCUMENT.reader().readFrom(buffer);
  }

  /**
   * Writes {@code document} to {@code buffer}.
   * 
   * @param buffer the buffer to write to
   * @param document the document to be written into {@code buffer}
   * @throws NullPointerException if {@code buffer} or {@code document} is null
   * @throws IllegalArgumentException if {@code buffer} is not using
   * little-endian byte ordering
   */
  public static void writeTo(ByteBuffer buffer, BsonDocument document) {
    BsonToken.DOCUMENT.writer().writeTo(buffer, document);
  }
  
  /**
   * Returns the binary size of the document. This is needed to allocate a
   * ByteBuffer that is exactly the correct size.
   * 
   * @param document the document to obtain the binary size of
   * @return the documents binary size
   */
  public static int binarySize(BsonDocument document){
    return BsonToken.DOCUMENT.writer().getSize(document);
  }

  /**
   * Returns a new document containing {@code map}'s key-value pairs.
   * 
   * @param map the map whose key-value pairs will be used to initialize the new
   * document
   * @return a new document containing {@code map}'s key-value pairs
   * @throws NullPointerException if {@code map} or any of its keys are
   * null
   */
  public static BsonDocument copyOf(Map<String, Object> map) {
    return builder().putAll(map).build();
  }

  // @checkstyle:off ParameterNumber|JavadocMethod

  /**
   * Returns a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2, etc.</em>
   * 
   * @return a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2 etc.</em>
   * @throws NullPointerException if any key-value pair's key is null
   * @throws IllegalArgumentException if there are duplicate keys
   */
  public static BsonDocument of(String k1, @Nullable Object v1,
      String k2, @Nullable Object v2, String k3, @Nullable Object v3,
      String k4, @Nullable Object v4, String k5, @Nullable Object v5,
      String k6, @Nullable Object v6, String k7, @Nullable Object v7,
      String k8, @Nullable Object v8, String k9, @Nullable Object v9,
      String k10, @Nullable Object v10) {
    return builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5)
        .put(k6, v6).put(k7, v7).put(k8, v8).put(k9, v9).put(k10, v10).build();
  }

  /**
   * Returns a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2, etc.</em>
   * 
   * @return a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2 etc.</em>
   * @throws NullPointerException if any key-value pair's key is null
   * @throws IllegalArgumentException if there are duplicate keys
   */
  public static BsonDocument of(String k1, @Nullable Object v1,
      String k2, @Nullable Object v2, String k3, @Nullable Object v3,
      String k4, @Nullable Object v4, String k5, @Nullable Object v5,
      String k6, @Nullable Object v6, String k7, @Nullable Object v7,
      String k8, @Nullable Object v8, String k9, @Nullable Object v9) {
    return builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5)
        .put(k6, v6).put(k7, v7).put(k8, v8).put(k9, v9).build();
  }

  /**
   * Returns a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2, etc.</em>
   * 
   * @return a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2 etc.</em>
   * @throws NullPointerException if any key-value pair's key is null
   * @throws IllegalArgumentException if there are duplicate keys
   */
  public static BsonDocument of(String k1, @Nullable Object v1,
      String k2, @Nullable Object v2, String k3, @Nullable Object v3,
      String k4, @Nullable Object v4, String k5, @Nullable Object v5,
      String k6, @Nullable Object v6, String k7, @Nullable Object v7,
      String k8, @Nullable Object v8) {
    return builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4)
        .put(k5, v5).put(k6, v6).put(k7, v7).put(k8, v8).build();
  }

  /**
   * Returns a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2, etc.</em>
   * 
   * @return a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2 etc.</em>
   * @throws NullPointerException if any key-value pair's key is null
   * @throws IllegalArgumentException if there are duplicate keys
   */
  public static BsonDocument of(String k1, @Nullable Object v1,
      String k2, @Nullable Object v2, String k3, @Nullable Object v3,
      String k4, @Nullable Object v4, String k5, @Nullable Object v5,
      String k6, @Nullable Object v6, String k7, @Nullable Object v7) {
    return builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4)
        .put(k5, v5).put(k6, v6).put(k7, v7).build();
  }

  /**
   * Returns a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2, etc.</em>
   * 
   * @return a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2 etc.</em>
   * @throws NullPointerException if any key-value pair's key is null
   * @throws IllegalArgumentException if there are duplicate keys
   */
  public static BsonDocument of(String k1, @Nullable Object v1,
      String k2, @Nullable Object v2, String k3, @Nullable Object v3,
      String k4, @Nullable Object v4, String k5, @Nullable Object v5,
      String k6, @Nullable Object v6) {
    return builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4)
        .put(k5, v5).put(k6, v6).build();
  }

  /**
   * Returns a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2, etc.</em>
   * 
   * @return a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2 etc.</em>
   * @throws NullPointerException if any key-value pair's key is null
   * @throws IllegalArgumentException if there are duplicate keys
   */
  public static BsonDocument of(String k1, @Nullable Object v1,
      String k2, @Nullable Object v2, String k3, @Nullable Object v3,
      String k4, @Nullable Object v4, String k5, @Nullable Object v5) {
    return builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).build();
  }

  /**
   * Returns a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2, etc.</em>
   * 
   * @return a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2 etc.</em>
   * @throws NullPointerException if any key-value pair's key is null
   * @throws IllegalArgumentException if there are duplicate keys
   */
  public static BsonDocument of(String k1, @Nullable Object v1,
      String k2, @Nullable Object v2, String k3, @Nullable Object v3,
      String k4, @Nullable Object v4) {
    return builder().put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).build();
  }

  /**
   * Returns a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2, etc.</em>
   * 
   * @return a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2 etc.</em>
   * @throws NullPointerException if any key-value pair's key is null
   * @throws IllegalArgumentException if there are duplicate keys
   */
  public static BsonDocument of(String k1, @Nullable Object v1,
      String k2, @Nullable Object v2, String k3, @Nullable Object v3) {
    return builder().put(k1, v1).put(k2, v2).put(k3, v3).build();
  }

  /**
   * Returns a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2</em>.
   * 
   * @return a new document containing the key-value pairs:
   * <em>k1: v1, k2: v2</em>
   * @throws NullPointerException if any key-value pair's key is null
   * @throws IllegalArgumentException if there are duplicate keys
   */
  public static BsonDocument of(String k1, @Nullable Object v1,
      String k2, @Nullable Object v2) {
    return builder().put(k1, v1).put(k2, v2).build();
  }

  // @checkstyle:on ParameterNumber|JavadocMethod

  /**
   * Returns a new document containing {@code key} and its associated
   * {@code value}.
   * 
   * @param key the key that will be used to initialize the new document
   * @param value the value associated with {@code key}
   * @return a new document containing {@code key} and its associated
   * {@code value}
   * @throws NullPointerException if {@code key} is null
   */
  public static BsonDocument of(String key, @Nullable Object value) {
    return builder().put(key, value).build();
  }

  /**
   * Returns the empty document.
   * 
   * @return the empty document
   */
  public static BsonDocument of() {
    return builder().build();
  }

  /**
   * Returns a new {@linkplain BsonDocument.Builder document builder}.
   * 
   * @return a new document builder
   */
  public static BsonDocument.Builder builder() {
    return new DefaultDocumentBuilder();
  }
}
