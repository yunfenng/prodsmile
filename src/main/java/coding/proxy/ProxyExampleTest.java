package coding.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author: Jaa
 * @Date: 2023/6/29 22:56
 */
public class ProxyExampleTest {

    @Test
    public void test_proxy() throws InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        IOrder order = Aspect.getProxy(Order.class, "coding.proxy.TimeUsageAspect");
        order.pay();
        order.show();
    }
}
