package com.github.kohanyirobert.ebson;

import com.google.common.base.Predicate;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

enum DefaultPredicate implements Predicate<Class<?>> {

  DOUBLE {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : Double.class.isAssignableFrom(input);
    }
  },

  STRING {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : String.class.isAssignableFrom(input);
    }
  },

  EMBEDDED {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : Map.class.isAssignableFrom(input);
    }
  },

  ARRAY {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : byte[].class.isAssignableFrom(input)
              ? false
              : Collection.class.isAssignableFrom(input) || input.isArray();
    }
  },

  BINARY {

    @Override
    public boolean apply(Class<?> input) {
      return GENERIC.apply(input);
    }
  },

  GENERIC {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : byte[].class.isAssignableFrom(input);
    }
  },

  OBJECT_ID {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : BsonObjectId.class.isAssignableFrom(input);
    }
  },

  BOOLEAN {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : Boolean.class.isAssignableFrom(input);
    }
  },

  UTC_DATE_TIME {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : Date.class.isAssignableFrom(input);
    }
  },

  NULL {

    @Override
    public boolean apply(Class<?> input) {
      return input == null;
    }
  },

  REGULAR_EXPRESSION {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : Pattern.class.isAssignableFrom(input);
    }
  },

  INT32 {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : Integer.class.isAssignableFrom(input);
    }
  },

  TIMESTAMP {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : BsonTimestamp.class.isAssignableFrom(input);
    }
  },

  INT64 {

    @Override
    public boolean apply(Class<?> input) {
      return input == null
          ? false
          : Long.class.isAssignableFrom(input);
    }
  };

  @Override
  public abstract boolean apply(Class<?> input);
}
