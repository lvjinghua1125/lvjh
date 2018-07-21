package cn.itcast.rabbitmq.spring;

/**
 * 消费者
 */
public class Foo {

    // 具体执行业务的方法
    public void listen(String msg) {
        System.out.println("消费者： " + msg);
    }
}