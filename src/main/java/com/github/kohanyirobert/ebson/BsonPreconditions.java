package com.github.kohanyirobert.ebson;

import javax.annotation.Nullable;

final class BsonPreconditions {

  private BsonPreconditions() {}

  static <T> T checkIsInstance(Object reference, Class<T> clazz) {
    if (!clazz.isInstance(reference))
      throw new ClassCastException();
    return clazz.cast(reference);
  }

  static <T> T checkIsInstance(Object reference, Class<T> clazz,
      @Nullable Object errorMessage) {
    if (!clazz.isInstance(reference))
      throw new ClassCastException(String.valueOf(errorMessage));
    return clazz.cast(reference);
  }

  static <T> T checkIsInstance(Object reference, Class<T> clazz,
      @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
    if (!clazz.isInstance(reference))
      throw new ClassCastException(String.format(errorMessageTemplate, errorMessageArgs));
    return clazz.cast(reference);
  }
}
