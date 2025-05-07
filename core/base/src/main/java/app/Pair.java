package app;

public record Pair<L, R>(L first, R second) {
  public static <L, R> Pair<L, R> of(L first, R second) {
    return new Pair<>(first, second);
  }
}
//
