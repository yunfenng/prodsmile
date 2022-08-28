package coding.collection;

import java.util.*;

/**
 * @Author Jaa
 * @Date 2022/8/28 16:47
 * @Description 随机产生字符串序列
 */
public class RandomStringGenerator<T> implements Iterable<T> {

    private final List<T> list;

    public RandomStringGenerator(List<T> list) {
        this.list = list;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return list.get((int) (list.size() * Math.random()));
            }
        };
    }

    public static void main(String[] args) {
        var list = Arrays.asList("List", "Tree", "Array");
        var gen = new RandomStringGenerator<String>(list);

        // for (var s : gen) {
        //     System.out.println(s);
        // }

        var it = gen.iterator();
        for (int i = 0; i < 100; i++) {
            System.out.println(it.next());
        }

        // ArrayList<String> arr = new ArrayList<>();
        // arr.add("123");
        // arr.add("abc");
        // var stringArr = arr.toArray(new String[10]);
        // System.out.println(stringArr[0] + ", " + stringArr[1]);

        ArrayList<String> arr = new ArrayList<>();
        arr.toArray(new String[0]);
        // Method Reference ::
        // Lazy evaluation
        arr.toArray(String[]::new);

        
    }
}
