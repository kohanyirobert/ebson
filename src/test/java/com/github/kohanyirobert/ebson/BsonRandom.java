package com.github.kohanyirobert.ebson;

import java.util.Random;

public final class BsonRandom {

  private static final Random RANDOM = new Random();

  private BsonRandom() {}

  public static void nextBytes(byte[] bytes) {
    RANDOM.nextBytes(bytes);
  }

  public static int nextInt() {
    return RANDOM.nextInt();
  }

  public static int nextInt(int n) {
    return RANDOM.nextInt(n);
  }

  public static long nextLong() {
    return RANDOM.nextLong();
  }

  public static boolean nextBoolean() {
    return RANDOM.nextBoolean();
  }

  public static float nextFloat() {
    return RANDOM.nextFloat();
  }

  public static double nextDouble() {
    return RANDOM.nextDouble();
  }

  public static double nextGaussian() {
    return RANDOM.nextGaussian();
  }
}
