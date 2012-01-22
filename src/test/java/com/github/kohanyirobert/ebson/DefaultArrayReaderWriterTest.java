package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class DefaultArrayReaderWriterTest extends AbstractReaderWriterTest {

  public DefaultArrayReaderWriterTest() {
    super(DefaultReader.ARRAY, DefaultWriter.ARRAY);
  }

  @Test
  public void emptyStringArray() {
    assertArray(writeTo(new String[] {}), readFrom());
  }

  @Test
  public void emptyDoubleArray() {
    assertArray(writeTo(new double[] {}), readFrom());
  }

  @Test
  public void simpleDoubleArray() {
    assertArray(writeTo(new double[] {Double.MIN_VALUE, Double.MAX_VALUE}), readFrom());
  }

  @Test
  public void emptyCollection() {
    assertArray(writeTo(Collections.emptyList()), readFrom());
  }

  private static void assertArray(Object expected, Object actual) {
    assertArrayEquals(toArray(expected), toArray(actual));
  }

  // @do-not-check CyclomaticComplexity
  private static Object[] toArray(Object object) {
    Object[] array;
    if (object instanceof Object[])
      array = (Object[]) object;

    else if (object instanceof Map)
      array = ((Map<?, ?>) object).values().toArray();

    else if (object instanceof Collection)
      array = ((Collection<?>) object).toArray();

    else {
      int lenght = Array.getLength(object);
      array = new Object[lenght];
      for (int i = 0; i < lenght; i++)
        array[i] = Array.get(object, i);
    }
    return array;
  }
}
