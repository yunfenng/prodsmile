package coding.stream;

import org.junit.Test;

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


}
