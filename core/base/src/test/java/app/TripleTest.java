package app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TripleTest {

  @Nested
  @DisplayName("Triple 클래스의 기본적인 사용법")
  class BasicUse {

    @Test
    @DisplayName("Triple은 세 개의 서로 다른 타입 값을 저장할 수 있다")
    void testTriple() {
      Triple<String, Integer, Boolean> triple = new Triple<>("hello", 123, true);
      assertEquals("hello", triple.first());
      assertEquals(123, triple.second());
      assertEquals(true, triple.third());
    }

    @Test
    @DisplayName("정적 팩토리 메서드를 사용하여 Triple 인스턴스를 생성할 수 있다")
    void testFactoryMethod() {
      Triple<String, Integer, Boolean> triple = Triple.of("hello", 123, true);
      assertEquals("hello", triple.first());
      assertEquals(123, triple.second());
      assertEquals(true, triple.third());
    }

    @Test
    @DisplayName("Triple의 값들을 시계 방향으로 회전할 수 있다")
    void testRotate() {
      Triple<String, Integer, Boolean> triple = Triple.of("hello", 123, true);
      Triple<Integer, Boolean, String> rotated = triple.rotate();

      assertEquals(123, rotated.first());
      assertEquals(true, rotated.second());
      assertEquals("hello", rotated.third());
    }

    @Test
    @DisplayName("Triple의 값들을 반시계 방향으로 회전할 수 있다")
    void testRotateBackward() {
      Triple<String, Integer, Boolean> triple = Triple.of("hello", 123, true);
      Triple<Boolean, String, Integer> rotatedBack = triple.rotateBackward();

      assertEquals(true, rotatedBack.first());
      assertEquals("hello", rotatedBack.second());
      assertEquals(123, rotatedBack.third());
    }

    @Test
    @DisplayName("첫 번째, 두 번째, 또는 세 번째 값에 함수를 적용하여 변환할 수 있다")
    void testMapping() {
      Triple<String, Integer, Double> triple = Triple.of("hello", 3, 2.5);

      // 첫 번째 값 변환 (대문자로)
      Triple<String, Integer, Double> upperFirst = triple.mapFirst(String::toUpperCase);
      assertEquals("HELLO", upperFirst.first());
      assertEquals(3, upperFirst.second());
      assertEquals(2.5, upperFirst.third());

      // 두 번째 값 변환 (제곱)
      Triple<String, Integer, Double> squaredSecond = triple.mapSecond(n -> n * n);
      assertEquals("hello", squaredSecond.first());
      assertEquals(9, squaredSecond.second());
      assertEquals(2.5, squaredSecond.third());

      // 세 번째 값 변환 (반올림)
      Triple<String, Integer, Long> roundedThird = triple.mapThird(Math::round);
      assertEquals("hello", roundedThird.first());
      assertEquals(3, roundedThird.second());
      assertEquals(3L, roundedThird.third());
    }

    @Test
    @DisplayName("Triple에서 두 값을 선택하여 Pair로 변환할 수 있다")
    void testToPairs() {
      Triple<String, Integer, Boolean> triple = Triple.of("hello", 123, true);

      // 첫 번째와 두 번째 값으로 Pair 생성
      Pair<String, Integer> firstSecondPair = triple.toFirstSecondPair();
      assertEquals("hello", firstSecondPair.first());
      assertEquals(123, firstSecondPair.second());

      // 첫 번째와 세 번째 값으로 Pair 생성
      Pair<String, Boolean> firstThirdPair = triple.toFirstThirdPair();
      assertEquals("hello", firstThirdPair.first());
      assertEquals(true, firstThirdPair.second());

      // 두 번째와 세 번째 값으로 Pair 생성
      Pair<Integer, Boolean> secondThirdPair = triple.toSecondThirdPair();
      assertEquals(123, secondThirdPair.first());
      assertEquals(true, secondThirdPair.second());
    }

    @Test
    @DisplayName("Triple을 List 컬렉션으로 변환할 수 있다")
    void testToList() {
      Triple<String, Integer, Boolean> triple = Triple.of("hello", 123, true);
      List<Object> list = triple.toList();

      assertEquals(3, list.size());
      assertEquals("hello", list.get(0));
      assertEquals(123, list.get(1));
      assertEquals(true, list.get(2));
    }

    @Test
    @DisplayName("Triple을 배열로 변환할 수 있다")
    void testToArray() {
      Triple<String, Integer, Boolean> triple = Triple.of("hello", 123, true);
      Object[] array = triple.toArray();

      assertEquals(3, array.length);
      assertEquals("hello", array[0]);
      assertEquals(123, array[1]);
      assertEquals(true, array[2]);
    }

    @Test
    @DisplayName("같은 값을 가진 Triple은 동등하게 취급된다")
    void testEqualsAndHashCode() {
      Triple<String, Integer, Boolean> triple1 = Triple.of("hello", 123, true);
      Triple<String, Integer, Boolean> triple2 = Triple.of("hello", 123, true);
      Triple<String, Integer, Boolean> triple3 = Triple.of("world", 123, true);

      // equals 테스트
      assertEquals(triple1, triple2);
      assertNotEquals(triple1, triple3);

      // hashCode 테스트
      assertEquals(triple1.hashCode(), triple2.hashCode());
      assertNotEquals(triple1.hashCode(), triple3.hashCode());
    }

    @Test
    @DisplayName("Triple은 내용을 읽기 쉬운 형식으로 표현할 수 있다")
    void testToString() {
      Triple<String, Integer, Boolean> triple = Triple.of("hello", 123, true);
      String expected = "Triple[first=hello, second=123, third=true]";

      assertEquals(expected, triple.toString());
    }
  }

  @Nested
  @DisplayName("Triple 클래스의 실제 활용 예시")
  class PracticalExamples {

    @Test
    @DisplayName("사용자 정보와 권한을 함께 관리할 수 있다")
    void testUserManagement() {
      // 사용자 정보와 권한 관리 (이름, 나이, 역할)
      List<Triple<String, Integer, UserRole>> users =
          List.of(
              Triple.of("Alice", 28, UserRole.ADMIN),
              Triple.of("Bob", 35, UserRole.EDITOR),
              Triple.of("Charlie", 42, UserRole.VIEWER),
              Triple.of("Diana", 31, UserRole.EDITOR));

      // 역할별 사용자 수 계산
      Map<UserRole, Long> roleCount =
          users.stream().collect(Collectors.groupingBy(Triple::third, Collectors.counting()));

      assertEquals(1, roleCount.get(UserRole.ADMIN));
      assertEquals(2, roleCount.get(UserRole.EDITOR));
      assertEquals(1, roleCount.get(UserRole.VIEWER));

      // 관리자 및 편집자 역할을 가진 사용자 이름 목록
      List<String> adminAndEditors =
          users.stream()
              .filter(user -> user.third() == UserRole.ADMIN || user.third() == UserRole.EDITOR)
              .map(Triple::first)
              .toList();

      assertEquals(3, adminAndEditors.size());
      assertTrue(adminAndEditors.contains("Alice"));
      assertTrue(adminAndEditors.contains("Bob"));
      assertTrue(adminAndEditors.contains("Diana"));

      // 역할과 나이로 필터링
      List<String> youngEditors =
          users.stream()
              .filter(user -> user.third() == UserRole.EDITOR && user.second() < 33)
              .map(Triple::first)
              .toList();

      assertEquals(1, youngEditors.size());
      assertEquals("Diana", youngEditors.getFirst());
    }

    @Test
    @DisplayName("3D 좌표 시스템에서 점과 색상 정보를 함께 처리할 수 있다")
    void testColoredPoints3D() {
      // 3D 좌표계의 점과 해당 점의 색상 (x, y, z, 색상)
      List<Triple<Double, Double, String>> coloredPoints =
          List.of(
              Triple.of(0.0, 0.0, "black"), // 원점
              Triple.of(1.0, 0.0, "red"), // x축 양의 방향
              Triple.of(0.0, 1.0, "green"), // y축 양의 방향
              Triple.of(-1.0, 0.0, "blue"), // x축 음의 방향
              Triple.of(0.0, -1.0, "yellow") // y축 음의 방향
              );

      // 색상별 점 개수 계산
      Map<String, Long> colorCount =
          coloredPoints.stream()
              .collect(Collectors.groupingBy(Triple::third, Collectors.counting()));

      assertEquals(1, colorCount.get("black"));
      assertEquals(1, colorCount.get("red"));
      assertEquals(1, colorCount.get("green"));

      // 원점에서 가장 가까운 점 찾기
      Triple<Double, Double, String> closestToOrigin =
          coloredPoints.stream()
              .filter(point -> !(point.first() == 0.0 && point.second() == 0.0)) // 원점 제외
              .min(
                  (p1, p2) -> {
                    double dist1 = Math.sqrt(p1.first() * p1.first() + p1.second() * p1.second());
                    double dist2 = Math.sqrt(p2.first() * p2.first() + p2.second() * p2.second());
                    return Double.compare(dist1, dist2);
                  })
              .orElse(null);

      assertNotNull(closestToOrigin);
      assertEquals(1.0, Math.abs(closestToOrigin.first()) + Math.abs(closestToOrigin.second()));

      // 좌표 변환 - 모든 점을 (1,1) 만큼 이동
      List<Triple<Double, Double, String>> translatedPoints =
          coloredPoints.stream()
              .map(point -> Triple.of(point.first() + 1.0, point.second() + 1.0, point.third()))
              .toList();

      assertEquals(5, translatedPoints.size());
      assertEquals(1.0, translatedPoints.getFirst().first()); // 원래 (0,0)가 (1,1)로 이동
      assertEquals(1.0, translatedPoints.getFirst().second());
    }

    @Test
    @DisplayName("HTTP 요청 정보를 추적하고 분석할 수 있다")
    void testHttpRequestTracking() {
      // HTTP 요청 추적 (URL, 응답 코드, 처리 시간)
      List<Triple<String, Integer, Long>> requests =
          List.of(
              Triple.of("/api/users", 200, 150L),
              Triple.of("/api/products", 200, 120L),
              Triple.of("/api/orders", 404, 50L),
              Triple.of("/api/users", 200, 130L),
              Triple.of("/api/users", 500, 200L));

      // 성공한 요청과 실패한 요청 분류
      List<Triple<String, Integer, Long>> successfulRequests =
          requests.stream().filter(req -> req.second() >= 200 && req.second() < 300).toList();

      List<Triple<String, Integer, Long>> failedRequests =
          requests.stream().filter(req -> req.second() >= 400).toList();

      assertEquals(3, successfulRequests.size());
      assertEquals(2, failedRequests.size());

      // 평균 응답 시간 계산
      double avgResponseTime = requests.stream().mapToLong(Triple::third).average().orElse(0);

      assertEquals(130.0, avgResponseTime);

      // 각 URL별 요청 수 계산
      Map<String, Long> requestCountByUrl =
          requests.stream().collect(Collectors.groupingBy(Triple::first, Collectors.counting()));

      assertEquals(3, requestCountByUrl.get("/api/users"));
      assertEquals(1, requestCountByUrl.get("/api/products"));
      assertEquals(1, requestCountByUrl.get("/api/orders"));
    }
  }

  public enum UserRole {
    ADMIN,
    EDITOR,
    VIEWER
  }
}
