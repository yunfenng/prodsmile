package coding.annotation;

/**
 * @Author: Jaa
 * @Date: 2023/7/1 10:06
 */
public class TimeUsageAspect implements IAspect {

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
