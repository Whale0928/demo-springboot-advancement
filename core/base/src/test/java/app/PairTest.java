package app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

  @Test
  @DisplayName("자바 순수 코드로 코틀린의 페어 구현하기")
  void testPair() {
    Pair<String, Integer> pair = new Pair<>("hello", 123);
    assertEquals("hello", pair.first());
    assertEquals(123, pair.second());
  }
}
