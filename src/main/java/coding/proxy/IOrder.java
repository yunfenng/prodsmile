package coding.proxy;

/**
 * @Author: Jaa
 * @Date: 2023/6/29 22:54
 */
public interface IOrder {

    void pay() throws InterruptedException;

    void show();
}
