package coding.annotation;

/**
 * @Author: Jaa
 * @Date: 2023/7/1 10:03
 */
public interface IOrder {
    void pay() throws InterruptedException;

    void show();
}
