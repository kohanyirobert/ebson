package com.github.kohanyirobert.ebson;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

final class DefaultDocumentBuilder implements BsonDocument.Builder {

  private final Map<String, Object> builder;

  DefaultDocumentBuilder() {
    builder = Maps.newLinkedHashMap();
  }

  @Override
  public BsonDocument.Builder putAll(Map<String, Object> map) {
    Preconditions.checkNotNull(map, "null map");
    for (Entry<String, Object> entry : map.entrySet())
      put(entry.getKey(), entry.getValue());
    return this;
  }

  @Override
  public BsonDocument.Builder put(String key, @Nullable Object value) {
    Preconditions.checkNotNull(key, "null key");
    Preconditions.checkArgument(!builder.containsKey(key), "key: '%s' is already present", key);
    builder.put(key, value);
    return this;
  }

  @Override
  public BsonDocument build() {
    return new DefaultDocument(builder.isEmpty()
        ? Collections.<String, Object>emptyMap()
        : builder);
  }
}
