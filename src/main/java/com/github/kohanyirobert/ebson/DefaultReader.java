package com.github.kohanyirobert.ebson;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

enum DefaultReader implements BsonReader {

  DOCUMENT {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      int documentLength = buffer.getInt();
      BsonReader fieldReader = BsonToken.FIELD.reader();
      Map<Object, Object> document = Maps.newLinkedHashMap();
      if (documentLength > Ints.BYTES + 1)
        do {
          Entry<?, ?> entry = (Entry<?, ?>) fieldReader.readFrom(buffer);
          document.put(entry.getKey(), entry.getValue());
        } while (buffer.get(buffer.position()) != BsonBytes.EOO);
      buffer.get();
      return document;
    }
  },

  FIELD {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      BsonObject bsonObject = BsonObject.find(buffer.get());
      BsonReader keyReader = BsonToken.KEY.reader();
      BsonReader valueReader = bsonObject.reader();
      return Maps.immutableEntry(keyReader.readFrom(buffer),
          valueReader.readFrom(buffer));
    }
  },

  KEY {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      byte[] bytes = new byte[] {};
      byte[] read = new byte[] {BsonBytes.EOF};
      while ((read[0] = buffer.get()) != BsonBytes.EOO)
        bytes = Bytes.concat(bytes, read);
      return new String(bytes, Charsets.UTF_8);
    }
  },

  DOUBLE {

    @Override
    public Double checkedReadFrom(ByteBuffer buffer) {
      return Double.valueOf(buffer.getDouble());
    }
  },

  STRING {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      int stringLength = buffer.getInt();
      byte[] bytes = new byte[stringLength - 1];
      buffer.get(bytes).get();
      return new String(bytes, Charsets.UTF_8);
    }
  },

  ARRAY {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      return BsonToken.DOCUMENT.reader().readFrom(buffer);
    }
  },

  BINARY {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      int binaryLength = buffer.getInt();
      byte terminal = buffer.get();
      BsonBinary bsonBinary = BsonBinary.find(terminal);
      int oldLimit = buffer.limit();
      buffer.limit(buffer.position() + binaryLength);
      byte[] binary = (byte[]) bsonBinary.reader().readFrom(buffer);
      buffer.limit(oldLimit);
      return binary;
    }
  },

  GENERIC {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      byte[] binary = new byte[buffer.remaining()];
      buffer.get(binary);
      return binary;
    }
  },

  OBJECT_ID {

    @Override
    Object checkedReadFrom(ByteBuffer buffer) {
      return new BasicObjectId(buffer);
    }
  },

  BOOLEAN {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      return Boolean.valueOf(buffer.get() == BsonBytes.TRUE);
    }
  },

  UTC_DATE_TIME {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      return new Date(buffer.getLong());
    }
  },

  NULL {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      return null;
    }
  },

  REGULAR_EXPRESSION {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      BsonReader keyReader = BsonToken.KEY.reader();
      String pattern = (String) keyReader.readFrom(buffer);
      String options = (String) keyReader.readFrom(buffer);
      return Pattern.compile(pattern, optionsToFlags(options));
    }

    private int optionsToFlags(String options) {
      int flags = 0;
      for (char option : options.toCharArray())
        flags |= flags + optionToFlag(option);
      return flags;
    }

    // @do-not-check CyclomaticComplexity
    private int optionToFlag(char option) {
      int flag = 0;
      switch (option) {
        case 'i':
          flag = Pattern.CASE_INSENSITIVE;
          break;
        case 'm':
          flag = Pattern.MULTILINE;
          break;
        case 's':
          flag = Pattern.DOTALL;
          break;
        case 'x':
          flag = Pattern.COMMENTS;
          break;
        default:
          break;
      }
      return flag;
    }
  },

  INT32 {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      return Integer.valueOf(buffer.getInt());
    }
  },

  TIMESTAMP {

    @Override
    Object checkedReadFrom(ByteBuffer buffer) {
      return new BasicTimestamp(buffer);
    }
  },

  INT64 {

    @Override
    public Object checkedReadFrom(ByteBuffer buffer) {
      return Long.valueOf(buffer.getLong());
    }
  };

  @Override
  public final Object readFrom(ByteBuffer buffer) {
    Preconditions.checkNotNull(buffer, "null buffer");
    Preconditions.checkArgument(buffer.order() == ByteOrder.LITTLE_ENDIAN,
        "buffer has big-endian byte order; expected little-endian");
    return checkedReadFrom(buffer);
  }

  abstract Object checkedReadFrom(ByteBuffer buffer);
}
