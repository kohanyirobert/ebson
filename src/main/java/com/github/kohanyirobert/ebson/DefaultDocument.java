package com.github.kohanyirobert.ebson;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

final class DefaultDocument extends ForwardingMap<String, Object> implements BsonDocument {

  private final Map<String, Object> delegate;

  DefaultDocument(Map<String, Object> map) {
    delegate = Collections.unmodifiableMap(Maps.newLinkedHashMap(map));
  }

  @Override
  public <T> T get(Object key, Class<T> type) {
    Object value = get(key);
    Preconditions.checkArgument(
        type == null
            ? value == null
            : type.isInstance(value),
        "expected '%s' instead of '%s'",
        value == null
            ? null
            : value.getClass(), type);
    return type == null ? null : type.cast(value);
  }

  @Override
  public Object get(Object key) {
    Preconditions.checkArgument(containsKey(key), "key: '%s' is missing", key);
    return super.get(key);
  }

  @Override
  public boolean containsKey(Object key) {
    Preconditions.checkNotNull(key, "null key");
    BsonPreconditions.checkIsInstance(key, String.class, "key: '%s' is not a string", key);
    Preconditions.checkArgument(!((String) key).startsWith("$"), "key: '%s' starts with '$'", key);
    Preconditions.checkArgument(!((String) key).contains("."), "key: '%s' contains '.'", key);
    return super.containsKey(key);
  }

  @Override
  public String toString() {
    return new StringBuilder("{")
        .append(Joiner.on(", ")
            .withKeyValueSeparator(": ")
            .useForNull("null")
            .join(this))
        .append("}")
        .toString();
  }

  @Override
  protected Map<String, Object> delegate() {
    return delegate;
  }
}
