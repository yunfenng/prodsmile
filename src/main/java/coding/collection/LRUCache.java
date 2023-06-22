package coding.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @Author: Jaa
 * @Date: 2023/6/22 10:14
 * @Description: LRU Cache
 */
public class LRUCache<K, V> implements Iterable<K> {

    int MAX = 4;

    LinkedHashMap<K, V> cache = new LinkedHashMap<>();

    public void cache(K key, V value) {
        if (cache.containsKey(key)) {
            // 如果包含key，就移除并put新的K-V
            cache.remove(key);
        } else if (cache.size() >= MAX) {
            // 如果缓存大于max，一定会有next，找到第一个元素并移除
            var it = cache.keySet().iterator();
            var first = it.next();
            cache.remove(first);
        }
        cache.put(key, value);
    }

    @Override
    public Iterator<K> iterator() {
        var it = cache.entrySet().iterator();

        return new Iterator<K>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public K next() {
                return it.next().getKey();
            }
        };
    }


    public static void main(String[] args) {
        var lru = new LRUCache<String, Integer>();
        lru.cache("A", 1);
        lru.cache("B", 2);
        lru.cache("C", 3);
        lru.cache("D", 4);

        lru.cache("C", 10);

        // leave: A <- B <- D <- C
        System.out.println(
                "leave: " +
                        StreamSupport.stream(lru.spliterator(), false)
                                .map(x -> x.toString())
                                .collect(Collectors.joining(" <- ")));

    }
}
