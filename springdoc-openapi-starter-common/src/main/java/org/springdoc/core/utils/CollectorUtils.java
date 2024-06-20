package org.springdoc.core.utils;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorUtils {
  private CollectorUtils() {
  }

  public static <T, K, U> Collector<T, ?, LinkedHashMap<K, U>> toLinkedHashMap(
    Function<? super T, ? extends K> keyMapper,
    Function<? super T, ? extends U> valueMapper) {
    return toLinkedHashMap(keyMapper, valueMapper, (u, v) -> {
      throw new IllegalStateException(String.format("Duplicate key %s", u));
    });
  }

  public static <T, K, U> Collector<T, ?, LinkedHashMap<K, U>> toLinkedHashMap(
    Function<? super T, ? extends K> keyMapper,
    Function<? super T, ? extends U> valueMapper,
    BinaryOperator<U> mergeFunction) {
    return Collectors.toMap(
      keyMapper,
      valueMapper,
      mergeFunction,
      LinkedHashMap::new
    );
  }

  public static <T> Collector<T, ?, LinkedHashSet<T>> toLinkedHashSet() {
    return Collectors.toCollection(LinkedHashSet::new);
  }

  public static <T> T keepExisting(T existing, T replacement) {
    return existing;
  }

  public static <T> T replaceExisting(T existing, T replacement) {
    return replacement;
  }
}