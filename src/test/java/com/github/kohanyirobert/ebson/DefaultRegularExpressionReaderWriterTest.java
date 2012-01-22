package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import static java.util.regex.Pattern.CANON_EQ;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.COMMENTS;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.LITERAL;
import static java.util.regex.Pattern.MULTILINE;
import static java.util.regex.Pattern.UNICODE_CASE;
import static java.util.regex.Pattern.UNIX_LINES;
import static java.util.regex.Pattern.compile;

import java.util.regex.Pattern;

public final class DefaultRegularExpressionReaderWriterTest extends AbstractReaderWriterTest {

  public DefaultRegularExpressionReaderWriterTest() {
    super(DefaultReader.REGULAR_EXPRESSION, DefaultWriter.REGULAR_EXPRESSION);
  }

  @Test
  public void emptyPattern() {
    assertPatternEquals(writeTo(compile("")), readFrom());
  }

  @Test
  public void simplePattern() {
    assertPatternEquals(writeTo(compile("^42$")), readFrom());
  }

  @Ignore("the 'CANON_EQ' flag isn't supported")
  @Test
  public void canonEqFlag() {
    assertPatternEquals(writeTo(compile("", CANON_EQ)), readFrom());
  }

  @Test
  public void caseInsensitiveFlag() {
    assertPatternEquals(writeTo(compile("", CASE_INSENSITIVE)), readFrom());
  }

  @Test
  public void commentsFlag() {
    assertPatternEquals(writeTo(compile("", COMMENTS)), readFrom());
  }

  @Test
  public void dotallFlag() {
    assertPatternEquals(writeTo(compile("", DOTALL)), readFrom());
  }

  @Ignore("the 'LITERAL' flag isn't supported")
  @Test
  public void literalFlag() {
    assertPatternEquals(writeTo(compile("", LITERAL)), readFrom());
  }

  @Test
  public void multilineFlag() {
    assertPatternEquals(writeTo(compile("", MULTILINE)), readFrom());
  }

  @Ignore("the 'UNICODE_CASE' flag isn't supported")
  @Test
  public void unicodeCaseFlag() {
    assertPatternEquals(writeTo(compile("", UNICODE_CASE)), readFrom());
  }

  // @formatter:off
  // @Ignore("the 'UNICODE_CHARACTER_CLASS' flag isn't supported")
  // @Test
  // public void unicodeCharacterClassFlag() {
  //  assertPatternEquals(writeTo(compile("", UNICODE_CHARACTER_CLASS)), readFrom());
  // }
  // @formatter:on

  @Ignore("the 'UNIX_LINES' flag isn't supported")
  @Test
  public void unixLinesFlag() {
    assertPatternEquals(writeTo(compile("", UNIX_LINES)), readFrom());
  }

  @Test
  public void multipleFlags() {
    assertPatternEquals(writeTo(compile("", MULTILINE | CASE_INSENSITIVE | COMMENTS)), readFrom());
  }

  private static void assertPatternEquals(Object expected, Object actual) {
    Pattern expectedPattern = (Pattern) expected;
    Pattern actualPattern = (Pattern) actual;
    assertEquals(expectedPattern.pattern(), actualPattern.pattern());
    assertEquals(expectedPattern.flags(), actualPattern.flags());
  }
}
