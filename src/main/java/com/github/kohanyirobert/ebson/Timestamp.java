package com.github.kohanyirobert.ebson;

import java.nio.ByteBuffer;

// @checkstyle:off .
public interface Timestamp {

  ByteBuffer getTimestamp();

  ByteBuffer getTime();

  ByteBuffer getIncrement();
}
