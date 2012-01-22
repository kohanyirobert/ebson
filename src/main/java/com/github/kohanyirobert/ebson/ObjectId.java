package com.github.kohanyirobert.ebson;

import java.nio.ByteBuffer;

// @checkstyle:off .
public interface ObjectId {

  ByteBuffer getObjectId();

  ByteBuffer getTime();

  ByteBuffer getMachineId();

  ByteBuffer getProcessId();

  ByteBuffer getIncrement();
}
