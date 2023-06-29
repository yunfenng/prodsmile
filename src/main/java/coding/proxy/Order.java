package coding.proxy;

/**
 * @Author: Jaa
 * @Date: 2023/6/29 22:54
 */
public class Order implements IOrder {

    int state = 0;

    @Override
    public void pay() throws InterruptedException {
        Thread.sleep(50);
        this.state = 1;
    }

    @Override
    public void show() {
        System.out.println("order status:" + this.state);
    }
}
