package app.structure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

  @Nested
  @DisplayName("Pair 클래스의 기본적인 사용법")
  class BasicUse {

    @Test
    @DisplayName("Pair는 두 개의 서로 다른 타입 값을 저장할 수 있다")
    void testPair() {
      Pair<String, Integer> pair = new Pair<>("hello", 123);
      assertEquals("hello", pair.first());
      assertEquals(123, pair.second());

      kotlin.Pair<String, Integer> kotlinPair = new kotlin.Pair<>("hello", 123);
      assertEquals("hello", kotlinPair.getFirst());
    }

    @Test
    @DisplayName("정적 팩토리 메서드를 사용하여 Pair 인스턴스를 생성할 수 있다")
    void testFactoryMethod() {
      Pair<String, Integer> pair = Pair.of("hello", 123);
      assertEquals("hello", pair.first());
      assertEquals(123, pair.second());
    }

    @Test
    @DisplayName("first 객체와 second 객체의 위치를 서로 바꿀 수 있다")
    void testSwap() {
      Pair<String, Integer> pair = new Pair<>("hello", 123);
      Pair<Integer, String> swapped = pair.swap();

      assertEquals(123, swapped.first());
      assertEquals("hello", swapped.second());
    }

    @Test
    @DisplayName("첫 번째 또는 두 번째 값에 함수를 적용하여 변환할 수 있다")
    void testMapping() {
      Pair<String, Integer> pair = new Pair<>("hello", 123);

      // 첫 번째 값 변환 (대문자로)
      Pair<String, Integer> upperFirst = pair.mapFirst(String::toUpperCase);
      assertEquals("HELLO", upperFirst.first());
      assertEquals(123, upperFirst.second());

      // 두 번째 값 변환 (제곱)
      Pair<String, Integer> squaredSecond = pair.mapSecond(n -> n * n);
      assertEquals("hello", squaredSecond.first());
      assertEquals(15129, squaredSecond.second());
    }

    @Test
    @DisplayName("두 값을 함께 사용하여 새로운 결과를 생성할 수 있다")
    void testBiMap() {
      Pair<String, Integer> pair = new Pair<>("hello", 3);

      // 두 값을 조합하여 새 값 생성
      String result = pair.map(String::repeat);
      assertEquals("hellohellohello", result);
    }

    @Test
    @DisplayName("Pair를 List 컬렉션으로 변환할 수 있다")
    void testToList() {
      Pair<String, Integer> pair = new Pair<>("hello", 123);
      List<Object> list = pair.toList();

      assertEquals(2, list.size());
      assertEquals("hello", list.get(0));
      assertEquals(123, list.get(1));
    }

    @Test
    @DisplayName("Pair를 배열로 변환할 수 있다")
    void testToArray() {
      Pair<String, Integer> pair = new Pair<>("hello", 123);
      Object[] array = pair.toArray();

      assertEquals(2, array.length);
      assertEquals("hello", array[0]);
      assertEquals(123, array[1]);
    }

    @Test
    @DisplayName("같은 값을 가진 Pair는 동등하게 취급된다")
    void testEqualsAndHashCode() {
      Pair<String, Integer> pair1 = new Pair<>("hello", 123);
      Pair<String, Integer> pair2 = new Pair<>("hello", 123);
      Pair<String, Integer> pair3 = new Pair<>("world", 123);

      // equals 테스트
      assertEquals(pair1, pair2);
      assertNotEquals(pair1, pair3);

      // hashCode 테스트
      assertEquals(pair1.hashCode(), pair2.hashCode());
      assertNotEquals(pair1.hashCode(), pair3.hashCode());
    }

    @Test
    @DisplayName("Pair는 내용을 읽기 쉬운 형식으로 표현할 수 있다")
    void testToString() {
      Pair<String, Integer> pair = new Pair<>("hello", 123);
      String expected = "Pair[first=hello, second=123]";

      assertEquals(expected, pair.toString());
    }
  }

  @Nested
  @DisplayName("Pair 클래스의 실제 활용 예시")
  class PracticalExamples {

    @Test
    @DisplayName("함수에서 최소값과 최대값을 동시에 반환할 수 있다")
    void testMinMaxFunction() {
      // 리스트에서 최소값과 최대값을 반환하는 함수
      List<Integer> numbers = List.of(5, 2, 8, 1, 9, 3);

      Pair<Integer, Integer> minMax = getMinMax(numbers);

      assertEquals(1, minMax.first(), "최소값은 1이어야 합니다");
      assertEquals(9, minMax.second(), "최대값은 9이어야 합니다");

      // 구조 분해 할당처럼 사용
      Integer min = minMax.first();
      Integer max = minMax.second();

      assertEquals(1, min);
      assertEquals(9, max);
    }

    private Pair<Integer, Integer> getMinMax(List<Integer> numbers) {
      if (numbers == null || numbers.isEmpty()) {
        return Pair.of(null, null);
      }

      Integer min = numbers.stream().min(Integer::compareTo).orElse(null);
      Integer max = numbers.stream().max(Integer::compareTo).orElse(null);

      return Pair.of(min, max);
    }

    @Test
    @DisplayName("API 응답에서 상태 코드와 본문을 함께 처리할 수 있다")
    void testApiResponseHandling() {
      // API 응답 시뮬레이션
      Pair<Integer, String> successResponse = Pair.of(200, "{\"message\": \"Success\"}");
      Pair<Integer, String> errorResponse = Pair.of(404, "{\"error\": \"Not Found\"}");

      // 응답 처리
      ApiResponse<String> success = processApiResponse(successResponse);
      ApiResponse<String> error = processApiResponse(errorResponse);

      assertTrue(success.isSuccess());
      assertEquals(200, success.statusCode());
      assertEquals("{\"message\": \"Success\"}", success.body());

      assertFalse(error.isSuccess());
      assertEquals(404, error.statusCode());
      assertEquals("{\"error\": \"Not Found\"}", error.body());
    }

    @Test
    @DisplayName("위치 정보와 고도 정보를 함께 처리할 수 있다")
    void testLocationProcessing() {
      // 위도/경도 좌표 목록 (고도는 나중에 추가)
      List<Pair<Double, Double>> coordinates =
          List.of(
              Pair.of(37.5665, 126.9780), // 서울
              Pair.of(35.6804, 139.7690), // 도쿄
              Pair.of(40.7128, -74.0060) // 뉴욕
              );

      // 고도 데이터 (미터 단위)
      List<Double> altitudes = List.of(38.0, 40.0, 10.0);

      // 위치 객체 생성
      List<Location> locations = createLocations(coordinates, altitudes);

      assertEquals(3, locations.size());

      // 첫 번째 위치 확인 (서울)
      Location seoul = locations.getFirst();
      assertEquals(37.5665, seoul.latitude());
      assertEquals(126.9780, seoul.longitude());
      assertEquals(38.0, seoul.altitude());

      // 위치 필터링 - 고도가 20미터 이상인 위치만 선택
      List<Location> highLocations =
          locations.stream().filter(loc -> loc.altitude() >= 20.0).toList();

      assertEquals(2, highLocations.size());

      // 위치 변환 - 모든 고도를 50% 증가
      List<Location> elevatedLocations =
          locations.stream().map(loc -> loc.withAltitude(loc.altitude() * 1.5)).toList();

      assertEquals(57.0, elevatedLocations.get(0).altitude());
      assertEquals(60.0, elevatedLocations.get(1).altitude());
      assertEquals(15.0, elevatedLocations.get(2).altitude());
    }

    @Test
    @DisplayName("데이터 변환을 추적하고 분석할 수 있다")
    void testDataTransformation() {
      // A원본 데이터
      List<String> rawData = List.of("10", "20", "30", "40", "50");

      // 데이터 변환 및 추적 - 문자열 → 정수 변환
      List<Pair<String, Integer>> transformedWithTracking =
          rawData.stream().map(raw -> Pair.of(raw, Integer.parseInt(raw))).toList();

      // 변환 결과 확인
      assertEquals(5, transformedWithTracking.size());
      assertEquals("30", transformedWithTracking.get(2).first());
      assertEquals(30, transformedWithTracking.get(2).second());

      // 변환 통계 계산 - 정수 값의 합계
      int sum = transformedWithTracking.stream().map(Pair::second).reduce(0, Integer::sum);

      assertEquals(150, sum);

      // 원본 값과 변환 값의 차이 계산
      List<Integer> differences =
          transformedWithTracking.stream()
              .map(pair -> pair.map((raw, parsed) -> parsed - Integer.parseInt(raw)))
              .toList();

      assertTrue(differences.stream().allMatch(diff -> diff == 0));
    }

    @Test
    @DisplayName("캐시 시스템에서 값과 만료 시간을 함께 관리할 수 있다")
    void testCacheWithExpiration() {
      // 간단한 캐시 시스템 시뮬레이션
      Map<String, Pair<String, Long>> cache = new HashMap<>();

      // 현재 시간 (밀리초)
      long now = System.currentTimeMillis();

      // 캐시에 항목 추가 (값, 만료 시간)
      cache.put("user:1", Pair.of("John Doe", now + 10000)); // 10초 후 만료
      cache.put("user:2", Pair.of("Jane Smith", now + 20000)); // 20초 후 만료
      cache.put("user:3", Pair.of("Bob Brown", now - 5000)); // 이미 만료됨

      // 캐시에서 값 조회 (만료 확인)
      String user1 = getCacheValue(cache, "user:1", now);
      String user2 = getCacheValue(cache, "user:2", now);
      String user3 = getCacheValue(cache, "user:3", now);
      String user4 = getCacheValue(cache, "user:4", now);

      assertEquals("John Doe", user1);
      assertEquals("Jane Smith", user2);
      assertNull(user3); // 만료됨
      assertNull(user4); // 존재하지 않음

      // 만료되지 않은 항목만 필터링
      List<String> validUsers =
          cache.values().stream()
              .filter(stringLongPair -> stringLongPair.second() > now)
              .map(Pair::first)
              .toList();

      assertEquals(2, validUsers.size());
      assertTrue(validUsers.contains("John Doe"));
      assertTrue(validUsers.contains("Jane Smith"));
    }

    private <T> T getCacheValue(Map<String, Pair<T, Long>> cache, String key, long currentTime) {
      Pair<T, Long> entry = cache.get(key);
      if (entry == null || entry.second() <= currentTime) {
        return null; // 캐시 미스 또는 만료됨
      }
      return entry.first();
    }
  }

  private List<Location> createLocations(
      List<Pair<Double, Double>> coordinates, List<Double> altitudes) {
    List<Location> result = new ArrayList<>();

    for (int i = 0; i < Math.min(coordinates.size(), altitudes.size()); i++) {
      Pair<Double, Double> coordinate = coordinates.get(i);
      Double altitude = altitudes.get(i);

      result.add(Location.of(coordinate.first(), coordinate.second(), altitude));
    }

    return result;
  }

  public record Location(double latitude, double longitude, double altitude) {
    public static Location of(double latitude, double longitude, double altitude) {
      return new Location(latitude, longitude, altitude);
    }

    public Location withAltitude(double newAltitude) {
      return new Location(latitude, longitude, newAltitude);
    }
  }

  public record ApiResponse<T>(int statusCode, T body) {

    public boolean isSuccess() {
      return statusCode >= 200 && statusCode < 300;
    }
  }

  private <T> ApiResponse<T> processApiResponse(Pair<Integer, T> response) {
    return new ApiResponse<>(response.first(), response.second());
  }
}
