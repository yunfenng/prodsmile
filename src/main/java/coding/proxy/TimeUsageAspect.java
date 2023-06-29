package coding.proxy;

/**
 * @Author: Jaa
 * @Date: 2023/6/29 22:58
 */
public class TimeUsageAspect implements Aspect {

    long start;

    @Override
    public void before() {
        start = System.currentTimeMillis();
    }

    @Override
    public void after() {
        var usage = System.currentTimeMillis() - start;
        System.out.format("time usage : %dms\n", usage);
    }
}
