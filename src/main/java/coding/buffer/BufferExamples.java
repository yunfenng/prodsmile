package coding.buffer;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: Jaa
 * @Date: 2023/6/24 9:39
 * @Description:
 */
public class BufferExamples {

    @Test
    public void gen() throws IOException {
        Random r = new Random();
        var fileName = "word";

        var bufferSize = 4 * 1024; // 4k和Linux的内存片大小一致

        // var fout = new FileOutputStream(fileName); // 13040 ms
        var fout = new BufferedOutputStream(new FileOutputStream(fileName), bufferSize); // 65 ms 使用缓存区60ms左右

        var start = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++) {
            for (int j = 0; j < 5; j++) {
                fout.write(97 + r.nextInt(5));
            }
        }

        fout.close();
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test_read() throws IOException {
        var fileName = "word";
        var in = new FileInputStream(fileName);

        long start = System.currentTimeMillis();

        int b;
        // var sb = new  StringBuilder();
        while ((b = in.read()) != -1) {

        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms"); // 7466ms
        in.close();
    }

    @Test
    public void test_read_withBuffer() throws IOException {
        var fileName = "word";
        // DEFAULT_BUFFER_SIZE = 8192 8k
        // var bufferSize = 1024 * 1024 * 4; // 通常不需要设置
        // var in = new BufferedInputStream(new FileInputStream(fileName), bufferSize);
        var in = new BufferedInputStream(new FileInputStream(fileName));

        long start = System.currentTimeMillis();

        int b;
        // var sb = new  StringBuilder();
        var bytes = new byte[1024 * 8]; // 2ms
        while ((b = in.read(bytes)) != -1) {

        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms"); // 50ms
        in.close();
    }

    @Test
    public void test_read_nio() throws IOException {

        // New I/O
        var fileName = "word";
        var channel = new FileInputStream(fileName).getChannel();
        var buff = ByteBuffer.allocate(1024 * 8);
        var start = System.currentTimeMillis();

        while (channel.read(buff) != -1) {
            buff.flip();
            // 读取数据
            // System.out.println(new String(buff.array()));
            buff.clear();
        }

        System.out.format("%dms\n", System.currentTimeMillis() - start); // 8ms
    }

    /**
     * 阿里面试题：中文乱码处理
     */
    @Test
    public void test_chinese() {
        var raw = "长坂桥头杀气生，横枪立马眼圆睁。一声好似轰雷震，独退曹家百万兵。";
        var charset = StandardCharsets.UTF_8;
        var bytes = charset.encode(raw).array();
        var bytes2 = Arrays.copyOfRange(bytes, 0, 11);

        var bbuf = ByteBuffer.allocate(12);
        var cbuf = CharBuffer.allocate(12);

        bbuf.put(bytes2);
        bbuf.flip();

        // 将bbuf的内容copy到cbuf
        charset.newDecoder().decode(bbuf, cbuf, true);
        cbuf.flip();

        var tmp = new char[cbuf.length()];
        if (cbuf.hasRemaining()) {
            cbuf.get();
            System.out.println("here:" + new String(tmp));
        }

        // capacity 容量大小
        // position 当前的位置
        // limit    写入或者读取的数据最大上限
        System.out.format("limit - pos = %d\n", bbuf.limit() - bbuf.position());

        Arrays.copyOfRange(bbuf.array(), bbuf.position(), bbuf.limit());
    }

    @Test
    public void test_async_read() throws IOException, ExecutionException, InterruptedException {
        var fileName = "word";
        var channel = AsynchronousFileChannel.open(Path.of(fileName), StandardOpenOption.READ);
        var buf = ByteBuffer.allocate(1024 * 8);
        Future<Integer> operation = channel.read(buf, 0);
        var numReads  = operation.get();
        buf.flip();
        var chars = new String(buf.array());
        buf.clear();
    }


}
