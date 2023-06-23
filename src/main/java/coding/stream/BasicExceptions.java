package coding.stream;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: Jaa
 * @Date: 2023/6/23 9:40
 * @Description:
 */
public class BasicExceptions {

    @Test
    public void testMapFilter() {
        Stream.of(1, 2, 3, 4, 5, 6)
                .map(x -> x.toString())
                .map(x -> x + x)
                .map(x -> x + x + x)
                // .map(x -> Integer.parseInt(x))
                .map(Integer::parseInt)
                // .forEach(x -> {
                //     System.out.println(x);
                // });
                .forEach(System.out::println);
        // ::
        // function reference operator
        // lambda expression
        // forEach 终结操作符
    }

    @Test
    public void testMapFilterReduce() {
        // Monad
        // .reduce(Math::max) return Optional<T>, if Stream is null return Optional[]
        // .reduce(0, Math::min) return T
        /*var result = Stream.of(1,2,3,4,5,6)
                .map(x -> x * x)
                .filter(x -> x < 20)
                .reduce(Math::max);
                // .reduce(0, Math::min); // add param 0 is safer
        System.out.println(result.isPresent());
        System.out.println(result.get());*/

        var result = IntStream.of()
                .map(x -> x * x)
                .filter(x -> x < 20)
                .reduce(Math::max);
                // .orElse(0);
                // .reduce(0, Integer::min);
                // .reduce(0, Math::min); // add param 0 is safer
        System.out.println(result.isPresent());
        // Supplier<T>
        System.out.println(result.orElseGet(() -> 0)); // Supplier<T>
    }


    @Test
    public void test_mutation() {
        // stateful：sorted, skip, limit
        // stateless: map, reduce
        var stream = Stream.of(1,6,7,3,9,2,5).sorted();
        stream.forEach(System.out::println);
    }

    // 有副作用的函数叫非纯函数，没有副作用的函数叫纯函数（pure function）
    int c = 0;
    int add(int a, int b) {
        // Side effect 副作用
        c ++;
        System.out.println("xxx");
        return a + b;
    }

    @Test
    public void test_flatMap() {
        var set = Stream.of("My", "Mine")
                .flatMap(str -> str.chars().mapToObj(i -> (char) i))
                .collect(Collectors.toSet());
        System.out.println(set.stream().collect(Collectors.toList()));
    }

    @Test
    public void test_parallel() {
        var r = new Random();
        var list = IntStream.range(0, 1_000_000)
                .map(x -> r.nextInt(1_000_000))
                // int -> Integer, collect only encapsulation Object
                .boxed()
                .collect(Collectors.toList());
        long t0 = System.currentTimeMillis();
        System.out.println(list.stream().max((a, b) -> a - b));
        System.out.println("Serial time: " + (System.currentTimeMillis() - t0));

        var pool = new ForkJoinPool(2);
        long t1 = System.currentTimeMillis();
        // System.out.println(list.stream().parallel().max((a, b) -> a - b));
        var max = pool.submit(() -> list.parallelStream().max((a, b) -> a - b).get());
        System.out.println("Parallel time: " + (System.currentTimeMillis() - t1));

        // 11 (核数-1)
        // Spliter 1024 -> Thread0 1024 -> Thread1
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
