package com.github.kohanyirobert.ebson;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.nio.ByteBuffer;

import javax.annotation.Nullable;

public final class DefaultBinaryReaderWriterTest extends AbstractReaderWriterTest {

  private static final int BINARY_LENGTH = 1024;

  private static final ThreadLocal<byte[]> RANDOM_BINARY = new ThreadLocal<byte[]>() {

    @Override
    protected byte[] initialValue() {
      byte[] bytes = new byte[BINARY_LENGTH];
      BsonRandom.nextBytes(bytes);
      return bytes;
    }
  };

  public DefaultBinaryReaderWriterTest() {
    super(DefaultReader.BINARY, DefaultWriter.BINARY);
  }

  @Test
  public void randomByteArray() {
    assertArrayEquals((byte[]) writeTo(RANDOM_BINARY.get()), (byte[]) readFrom());
  }

  @Test
  public void customUserObject() {
    BsonBinary.USER.predicate(new UserPredicate());
    BsonBinary.USER.reader(new UserReader());
    BsonBinary.USER.writer(new UserWriter());

    assertEquals(writeTo(new User("Ford", 42)), readFrom());
  }

  private static final class UserPredicate implements Predicate<Class<?>> {

    public UserPredicate() {}

    @Override
    public boolean apply(@Nullable Class<?> input) {
      return input == null
          ? false
          : User.class.isAssignableFrom(input);
    }
  }

  private static final class UserReader implements BsonReader {

    public UserReader() {}

    @Override
    @Nullable
    public Object readFrom(ByteBuffer buffer) {
      Object name = BsonObject.STRING.reader().readFrom(buffer);
      Object age = BsonObject.INT32.reader().readFrom(buffer);
      return new User((String) name, ((Integer) age).intValue());
    }
  }

  private static final class UserWriter implements BsonWriter {

    public UserWriter() {}

    @Override
    public void writeTo(ByteBuffer buffer, Object reference) {
      User user = (User) reference;
      BsonObject.STRING.writer().writeTo(buffer, user.getName());
      BsonObject.INT32.writer().writeTo(buffer, Integer.valueOf(user.getAge()));
    }

    @Override
    public int getSize(@Nullable Object reference) {
      User user = (User) reference;
      return BsonObject.STRING.writer().getSize(user.getName())
             + BsonObject.INT32.writer().getSize(Integer.valueOf(user.getAge()));
    }
  }

  private static final class User {

    private final String name;
    private final int age;

    public User(String name, int age) {
      this.name = Preconditions.checkNotNull(name);
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }

    @Override
    public int hashCode() {
      int hashCode = 17;
      return hashCode;
    }

    @Override
    public boolean equals(Object object) {
      if (object instanceof User) {
        User other = (User) object;
        return getName().equals(other.getName())
            && getAge() == other.getAge();
      }
      return false;
    }
  }
}
