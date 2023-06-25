package coding.buffer;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author: Jaa
 * @Date: 2023/6/24 21:57
 * @Description:
 */
public class WordCount {

    final ForkJoinPool pool = ForkJoinPool.commonPool();

    @Test
    public void conpare_with_single() throws IOException {
        var in = new BufferedInputStream(new FileInputStream("word"));
        var buf = new byte[4 * 1024];
        var len = 0;
        var total = new HashMap<String, Integer>();
        var startTime = System.currentTimeMillis();
        while ((len = in.read(buf)) != -1) {
            var bytes = Arrays.copyOfRange(buf, 0, len);
            var str = new String(bytes);
            // var str = new String(buf);
            var hashMap = countByString(str);
            for (var entry : hashMap.entrySet()) {
                var key = entry.getKey();
                incKey(key, total, entry.getValue());
            }
        }
        // 阿姆达定律
        // 120s -> 16core -> 120/16 = ?
        //  P    NP
        System.out.println("time:" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println(total.get("ababb"));
        System.out.println(total.size());
    }

    private static HashMap<String, Integer> countByString(String str) {
        var map = new HashMap<String, Integer>();
        StringTokenizer tokenizer = new StringTokenizer(str);
        while (tokenizer.hasMoreTokens()) {
            var word = tokenizer.nextToken();
            incKey(word, map, 1);
        }
        return map;
    }

    private static void incKey(String key, HashMap<String, Integer> map, Integer n) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + n);
        } else {
            map.put(key, n);
        }
    }
}
