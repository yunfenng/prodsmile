package coding.stream;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Jaa
 * @Date: 2023/6/23 16:01
 * @Description:
 */
public class MonadExample {

    @Test
    public void test_optional() {
        // 观察类型的变化
        var result = Optional.of(100)
                .map(x -> x * x)
                .map(x -> x / 100);
        // Optional.of(100) -- Integer -> Integer -- Integer -> Integer -- Optional.of(100)
    }

    @Test
    public void test_udef() {
        // 对空值的处理
        Optional<Integer> x = Optional.empty();
        var y = x.map(a -> a * a);
        System.out.println(y); // Optional.empty
    }

    @Test
    public void test_stream() {

        // Stream<String>
        var result = Stream.of("Hello", "World")
                // .map(x -> x.length());
                .map(String::length);
        System.out.println(result.collect(Collectors.toList()));
    }
}
