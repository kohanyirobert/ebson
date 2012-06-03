package com.github.kohanyirobert.ebson;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.regex.Pattern;

enum DefaultWriter implements BsonWriter {

  DOCUMENT {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      int markedPosition = buffer.position();
      buffer.position(markedPosition + Ints.BYTES);
      BsonWriter fieldWriter = BsonToken.FIELD.writer();
      for (Entry<?, ?> entry : ((Map<?, ?>) reference).entrySet())
        fieldWriter.writeTo(buffer, entry);
      buffer.put(BsonBytes.EOO);
      buffer.putInt(markedPosition, buffer.position() - markedPosition);
    }
  },

  FIELD {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      Entry<?, ?> entry = (Entry<?, ?>) reference;
      BsonObject bsonObject = BsonObject.find(entry.getValue() == null
          ? null
          : entry.getValue().getClass());
      buffer.put(bsonObject.terminal());
      BsonToken.KEY.writer().writeTo(buffer, entry.getKey());
      bsonObject.writer().writeTo(buffer, entry.getValue());
    }
  },

  KEY {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      buffer.put(((String) reference).getBytes(Charsets.UTF_8)).put(BsonBytes.EOO);
    }
  },

  DOUBLE {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      buffer.putDouble(((Double) reference).doubleValue());
    }
  },

  STRING {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      byte[] bytes = ((String) reference).getBytes(Charsets.UTF_8);
      buffer.putInt(bytes.length + 1).put(bytes).put(BsonBytes.EOO);
    }
  },

  ARRAY {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      Object array = reference instanceof Collection
          ? ((Collection<?>) reference).toArray()
          : reference;
      Map<Object, Object> document = Maps.newLinkedHashMap();
      for (int i = 0; i < Array.getLength(array); i++)
        document.put(String.valueOf(i), Array.get(array, i));
      BsonToken.DOCUMENT.writer().writeTo(buffer, document);
    }
  },

  BINARY {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      byte[] binary = (byte[]) reference;
      buffer.putInt(binary.length);
      BsonBinary bsonBinary = BsonBinary.find(binary.getClass());
      buffer.put(bsonBinary.terminal());
      bsonBinary.writer().writeTo(buffer, binary);
    }
  },

  GENERIC {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      buffer.put((byte[]) reference);
    }
  },

  OBJECT_ID {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      buffer.put(((BsonObjectId) reference).objectId());
    }
  },

  BOOLEAN {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      buffer.put(((Boolean) reference).booleanValue()
          ? BsonBytes.TRUE
          : BsonBytes.FALSE);
    }
  },

  UTC_DATE_TIME {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      buffer.putLong(((Date) reference).getTime());
    }
  },

  NULL {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {}
  },

  REGULAR_EXPRESSION {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      Pattern regularExpression = (Pattern) reference;
      BsonWriter keyWriter = BsonToken.KEY.writer();
      keyWriter.writeTo(buffer, regularExpression.pattern());
      keyWriter.writeTo(buffer, flagsToOptions(regularExpression.flags()));
    }

    // @do-not-check-next-line CyclomaticComplexity
    private String flagsToOptions(int flags) {
      SortedSet<Character> options = Sets.newTreeSet();
      if (hasFlag(flags, Pattern.CASE_INSENSITIVE))
        options.add(Character.valueOf('i'));

      if (hasFlag(flags, Pattern.COMMENTS))
        options.add(Character.valueOf('x'));

      if (hasFlag(flags, Pattern.DOTALL))
        options.add(Character.valueOf('s'));

      if (hasFlag(flags, Pattern.MULTILINE))
        options.add(Character.valueOf('m'));

      return Joiner.on("").join(options);
    }

    private boolean hasFlag(int flags, int flag) {
      return (flags & flag) != 0;
    }
  },

  SYMBOL {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      ByteBuffer symbol = ((BsonSymbol) reference).symbol();
      buffer.putInt(symbol.capacity() + 1).put(symbol).put(BsonBytes.EOO);
    }
  },

  INT32 {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      buffer.putInt(((Integer) reference).intValue());
    }
  },

  TIMESTAMP {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      buffer.put(((BsonTimestamp) reference).timestamp());
    }
  },

  INT64 {

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      buffer.putLong(((Long) reference).longValue());
    }
  };
}
