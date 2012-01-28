package com.github.kohanyirobert.ebson;

import java.nio.ByteBuffer;

// @checkstyle:off .
public interface BsonTimestamp {

  ByteBuffer timestamp();

  ByteBuffer time();

  ByteBuffer increment();
}
