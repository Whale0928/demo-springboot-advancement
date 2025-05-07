package app;

public record Triple<L, M, R>(L first, M second, R third) {
  public static <L, M, R> Triple<L, M, R> of(L first, M second, R third) {
    return new Triple<>(first, second, third);
  }
}
