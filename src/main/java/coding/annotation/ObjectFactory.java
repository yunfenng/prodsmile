package coding.annotation;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

/**
 * @Author: Jaa
 * @Date: 2023/7/1 10:11
 */
public class ObjectFactory {

    public static <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var annotations = clazz.getAnnotations();
        var aspects = new LinkedList<IAspect>();
        for (var annnotation : annotations) {
            if (annnotation instanceof Aspect) {
                var type = ((Aspect) annnotation).type();
                var aspect = (IAspect) type.getConstructor().newInstance();
                aspects.push(aspect);
            }
        }

        var inst = clazz.getConstructor().newInstance();
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                clazz.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        aspects.forEach(aspect -> aspect.before());
                        var result = method.invoke(inst);
                        aspects.forEach(aspect -> aspect.after());
                        return result;
                    }
                }
        );
    }


    @Test
    public void test() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        IOrder order = ObjectFactory.newInstance(Order.class);
        order.pay();
        order.show();
    }
}
