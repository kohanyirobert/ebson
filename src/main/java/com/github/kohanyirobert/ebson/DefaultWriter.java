package com.github.kohanyirobert.ebson;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

enum DefaultWriter implements BsonWriter {

  DOCUMENT {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      int markedPosition = buffer.position();
      buffer.position(markedPosition + Ints.BYTES);
      BsonWriter fieldWriter = BsonToken.FIELD.writer();
      for (Entry<?, ?> entry : ((Map<?, ?>) reference).entrySet()) {
        fieldWriter.writeTo(buffer, entry);
      }
      buffer.put(BsonBytes.EOO);
      buffer.putInt(markedPosition, buffer.position() - markedPosition);
    }

    @Override
    public int getSize(Object reference) {
      int constSize = 1 + 5;
      int variableSize = 0;
      BsonWriter fieldWriter = BsonToken.FIELD.writer();
      for (Entry<?, ?> entry : ((Map<?, ?>) reference).entrySet()) {
        variableSize += fieldWriter.getSize(entry);
      }
      return constSize + variableSize;
    }
  },

  FIELD {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      Entry<?, ?> entry = (Entry<?, ?>) reference;
      BsonObject bsonObject = BsonObject.find(entry.getValue() == null
          ? null
          : entry.getValue().getClass());
      buffer.put(bsonObject.terminal());
      BsonToken.KEY.writer().writeTo(buffer, entry.getKey());
      bsonObject.writer().writeTo(buffer, entry.getValue());
    }

    @Override
    public int getSize(@Nullable Object reference) {
      Entry<?, ?> entry = (Entry<?, ?>) reference;
      BsonObject bsonObject = BsonObject.find(entry.getValue() == null
          ? null
          : entry.getValue().getClass());

      int constSize = 1;
      int variableSize = BsonToken.KEY.writer().getSize(entry.getKey()) +
                         bsonObject.writer().getSize(entry.getValue());
      return constSize + variableSize;
    }
  },

  KEY {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      buffer.put(((String) reference).getBytes(Charsets.UTF_8)).put(BsonBytes.EOO);
    }

    @Override
    public int getSize(@Nullable Object reference) {
      int constSize = 1;
      int variableSize = ((String) reference).getBytes(Charsets.UTF_8).length;
      return constSize + variableSize;
    }
  },

  DOUBLE {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      buffer.putDouble(((Double) reference).doubleValue());
    }

    @Override
    public int getSize(@Nullable Object reference) {
      return 8;
    }
  },

  STRING {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      byte[] bytes = ((String) reference).getBytes(Charsets.UTF_8);
      buffer.putInt(bytes.length + 1).put(bytes).put(BsonBytes.EOO);
    }

    @Override
    public int getSize(@Nullable Object reference) {
      int constSize = 5;
      byte[] bytes = ((String) reference).getBytes(Charsets.UTF_8);
      int variableSize = bytes.length;
      return constSize + variableSize;
    }
  },

  ARRAY {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      Object array = reference instanceof Collection
          ? ((Collection<?>) reference).toArray()
          : reference;
      Map<Object, Object> document = Maps.newLinkedHashMap();
      for (int i = 0; i < Array.getLength(array); i++) {
        document.put(String.valueOf(i), Array.get(array, i));
      }
      BsonToken.DOCUMENT.writer().writeTo(buffer, document);
    }

    @Override
    public int getSize(@Nullable Object reference) {
      Object array = reference instanceof Collection
          ? ((Collection<?>) reference).toArray()
          : reference;
      Map<Object, Object> document = Maps.newLinkedHashMap();
      for (int i = 0; i < Array.getLength(array); i++) {
        document.put(String.valueOf(i), Array.get(array, i));
      }
      return BsonToken.DOCUMENT.writer().getSize(document);
    }
  },

  BINARY {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      int markedPosition = buffer.position();
      buffer.position(markedPosition + Ints.BYTES);
      BsonBinary bsonBinary = BsonBinary.find(reference.getClass());
      buffer.put(bsonBinary.terminal());
      bsonBinary.writer().writeTo(buffer, reference);
      buffer.putInt(markedPosition, buffer.position() - markedPosition - Ints.BYTES - 1);
    }

    @Override
    public int getSize(@Nullable Object reference) {
      BsonBinary bsonBinary = BsonBinary.find(reference.getClass());
      int constSize = 5;
      int variableSize = bsonBinary.writer().getSize(reference);
      return constSize + variableSize;
    }
  },

  GENERIC {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      buffer.put((byte[]) reference);
    }

    @Override
    public int getSize(@Nullable Object reference) {
      return ((byte[]) reference).length;
    }
  },

  OBJECT_ID {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      buffer.put(((BsonObjectId) reference).objectId());
    }

    @Override
    public int getSize(@Nullable Object reference) {
      return ((BsonObjectId) reference).objectId().capacity();
    }
  },

  BOOLEAN {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      buffer.put(((Boolean) reference).booleanValue()
          ? BsonBytes.TRUE
          : BsonBytes.FALSE);
    }

    @Override
    public int getSize(@Nullable Object reference) {
      return 1;
    }
  },

  UTC_DATE_TIME {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      buffer.putLong(((Date) reference).getTime());
    }

    @Override
    public int getSize(@Nullable Object reference) {
      return 8;
    }
  },

  NULL {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {}

    @Override
    public int getSize(@Nullable Object reference) {
      return 0;
    }
  },

  REGULAR_EXPRESSION {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      Pattern regularExpression = (Pattern) reference;
      BsonWriter keyWriter = BsonToken.KEY.writer();
      keyWriter.writeTo(buffer, regularExpression.pattern());
      keyWriter.writeTo(buffer, flagsToOptions(regularExpression.flags()));
    }

    @Override
    public int getSize(@Nullable Object reference) {
      Pattern regularExpression = (Pattern) reference;
      BsonWriter keyWriter = BsonToken.KEY.writer();
      return keyWriter.getSize(regularExpression.pattern()) + 
             keyWriter.getSize(flagsToOptions(regularExpression.flags()));
    }

    // @do-not-check-next-line CyclomaticComplexity
    private String flagsToOptions(int flags) {
      SortedSet<Character> options = Sets.newTreeSet();
      if (hasFlag(flags, Pattern.CASE_INSENSITIVE)) {
        options.add(Character.valueOf('i'));
      }

      if (hasFlag(flags, Pattern.COMMENTS)) {
        options.add(Character.valueOf('x'));
      }

      if (hasFlag(flags, Pattern.DOTALL)) {
        options.add(Character.valueOf('s'));
      }

      if (hasFlag(flags, Pattern.MULTILINE)) {
        options.add(Character.valueOf('m'));
      }

      return Joiner.on("").join(options);
    }

    private boolean hasFlag(int flags, int flag) {
      return (flags & flag) != 0;
    }
  },

  SYMBOL {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      ByteBuffer symbol = ((BsonSymbol) reference).symbol();
      buffer.putInt(symbol.capacity() + 1).put(symbol).put(BsonBytes.EOO);
    }

    @Override
    public int getSize(@Nullable Object reference) {
      ByteBuffer symbol = ((BsonSymbol) reference).symbol();
      int constSize = 5;
      int variableSize = symbol.capacity();
      return constSize + variableSize;
    }
  },

  INT32 {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      buffer.putInt(((Integer) reference).intValue());
    }

    @Override
    public int getSize(@Nullable Object reference) {
      return 4;
    }
  },

  TIMESTAMP {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      buffer.put(((BsonTimestamp) reference).timestamp());
    }

    @Override
    public int getSize(@Nullable Object reference) {
      return ((BsonTimestamp) reference).timestamp().capacity();
    }
  },

  INT64 {

    @Override
    public void checkedWriteTo(ByteBuffer buffer, Object reference) {
      buffer.putLong(((Long) reference).longValue());
    }

    @Override
    public int getSize(@Nullable Object reference) {
      return 8;
    }
  };

  @Override
  public final void writeTo(ByteBuffer buffer, Object reference) {
    Preconditions.checkNotNull(buffer, "null buffer");
    Preconditions.checkArgument(buffer.order() == ByteOrder.LITTLE_ENDIAN,
        "buffer has big-endian byte order; expected little-endian");
    checkedWriteTo(buffer, reference);
  }

  abstract void checkedWriteTo(ByteBuffer buffer, Object reference);
}
