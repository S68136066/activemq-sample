package queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Mr.Dxs on 2018/11/13.
 * 消息消费者
 * 点对点通信模型 （消息队列）
 */
public class Consumer {

    // 队列名称
    public static final String QUEUE_NAME = "my-queue";

    // ActiveMQ 服务器地址
    public static final String ACTIVE_MQ_URL = "tcp://localhost:61616";


    /**
     * 此处将抛出异常 方便演示测试
     * @param args
     * @throws JMSException
     */


    public static void main(String[] args) throws JMSException {

        // 1、创建 ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVE_MQ_URL);

        // 2、创建连接 Connection
        Connection connection = connectionFactory.createConnection();

        // 3、启动连接
        connection.start();

        /**
         * 4、创建会话
         * @param: 是否启用 事务性会话   true/false
         * @param: 消息确认方式
         * 事务性会话，只有在 session.commit() 之后,消息才会进入 JMS提供者(ActiveMQ) 的 消息队列中。并且事务性会话中,所设置的消息确认方式将会忽略，不再生效。
         * 非事务性会话，并不需要 session.commit(),关闭连接即可。
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 5、创建目的地
        Destination destination = session.createQueue(QUEUE_NAME);

        // 6、创建 消息消费者
        MessageConsumer consumer = session.createConsumer(destination);

        // 7、创建 消息监听器
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });


    }



}
