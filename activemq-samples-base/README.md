# activemq-samples-base :zzz:

> 小生不足之处，还望各位前辈不吝赐教。<br>

### JMS 

JMS 即 **Java 消息服务** 应用程序接口。是 Java 平台上有关面向 **消息中间件（MOM）**的技术规范。

JMS是一种与厂商无关的 API，用来访问收发系统消息，它类似于**JDBC**(Java Database Connectivity)。这里，JDBC 是可以用来访问许多不同关系数据库的 API，而 JMS 则提供同样与厂商无关的访问方法，以访问消息收发服务。 

### ActiveMQ

ActiveMQ 是 Apache 推出的，一款开源的，完全支持 **JMS 1.1** 和 **J2EE 1.4** 规范的 **JMS Provider** 实现的消息中间件（**MOM**）。



### MOM

**MOM ** 是 **Message Oriented Middleware** 的英文缩写，指的是利用高效可靠的消息传递机制进行平台无关的数据交流，并基于数据通信来进行**分布式系统**的集成。 即是 **面向消息的中间件**，就是将信息以消息的形式，从一个应用程序传送到另一个或多个应用程序。

![åºäº MOM çç³»ç»](http://c.hiphotos.baidu.com/baike/s%3D250/sign=d8839dbba40f4bfb88d09951334e788f/4d086e061d950a7b5889bce00ad162d9f2d3c952.jpg) 



### JMS 与 ActiveMQ

JDBC 与 数据库是什么关系？

JDBC 是一个 Java API 可以访问任何类型的数据库，并用来执行 SQL 语句，由此来操作数据库。

**JMS** 与 **ActiveMQ** 也类似 **JDBC** 与 **数据库**之间的关系，**ActiveMQ** 是基于 **JMS** **1.1**的规范实现，用来**存储或转发** **JMS 客户端**（**JMS客户端就相当于 使用 JDBC 的应用程序**）所生成的消息。



### JMS 与 Spring 关系

![spring overview](https://docs.spring.io/spring/docs/4.3.20.RELEASE/spring-framework-reference/htmlsingle/images/spring-overview.png) 



### JMS 架构组成

##### JMS提供者

连接面向消息中间件的，JMS接口的一个实现。提供者可以是Java平台的JMS实现，也可以是非Java平台的面向消息中间件的。比如 ActiveMQ。

##### JMS客户

生产或消费消息的基于Java的应用程序或对象。

##### JMS生产者

创建并发送消息的JMS客户。即创建和发送 JMS 消息的客户端应用。

##### JMS消费者

接收消息的 JMS客户。即接收和处理 JMS 消息的客户端应用。

##### JMS消息

包括可以在JMS客户之间传递的数据的对象

##### JMS 目的地

JMS 目的地包括了 JMS 队列 与 JMS 主题。

**队列**，一个容纳那些被发送的等待阅读的消息的区域。与队列名字所暗示的意思不同，消息的接受顺序并不一定要与消息的发送顺序相同。一旦一个消息被阅读，该消息将被从队列中移走。 

**主题**，一种支持发送消息给多个订阅者的机制。 



### JMS 消息传递模型 

**JMS domains, 即 消息传递域**，**JMS** 规范中定义了两种消息传递域：**点对点（Point-to-Point, 简写 PTP）**消息传递域 和 **发布/订阅**消息传递域**（Publish/Subscribe, 简写 Pub/Sub）**。



#### 一、点对点消息传递域

**在 点对点 消息传递域中，目的地被称为 队列（queue）。**

* 每个消息只能有一个消费者，即一个消息被一个消费者消费（确认）过之后就会弹出队列，不会再让别的消费者消费。
* 消息的生产者和消费者之间没有时间上的相关性。无论消费者在生产者发送消息的时候是否处于运行状态，它都可以提取消息。

![](https://images2015.cnblogs.com/blog/879896/201606/879896-20160604194640227-215496499.gif)





#### 二、发布/订阅消息传递域

**在 发布/订阅 消息传递域中，目的地被称为 主题（topic）。**

* 每个消息可以有多个消费者
* 生产者与消费者之间有时间上的相关性。订阅一个主题的消费者只能消费自它订阅之后发布的消息。JMS 规范允许客户创建持久订阅，这在一定程度上放松了时间上的相关性要求。持久订阅允许消费者消费他在未处于激活状态时发送的消息。



![img](https://images2015.cnblogs.com/blog/879896/201606/879896-20160604232610055-1944763982.gif) 



### JMS 接口概述

#### ConnectionFactory  接口（连接工厂）

用户用来创建到 **JMS提供者（ActiveMQ）** 的连接的被管对象。JMS客户通过可移植的接口访问连接，这样当下层的实现改变时，代码不需要进行修改。管理员在JNDI名字空间中配置连接工厂，这样，JMS客户才能够查找到它们。根据消息类型的不同，用户将使用队列连接工厂，或者主题连接工厂。 

#### Connection 接口 （连接）

**连接** 代表了 应用程序 和 消息服务器 之间的通信链路。在获得了连接工厂后，就可以创建一个与 **JMS提供者** 的连接。根据不同的连接类型，连接允许用户创建会话，以发送和接收队列和主题到目标。 

#### Session 接口 （会话）

**表示一个单线程的上下文**，用于发送和接收消息。由于会话是单线程的，所以消息是连续的，就是说消息是按照发送的顺序一个一个接收的。会话的好处是它支持 **事务**。如果用户选择了事务支持，会话上下文将保存一组消息，直到事务被提交才发送这些消息。在提交事务之前，用户可以使用回滚操作取消这些消息。一个会话允许用户创建消息，生产者来发送消息，消费者来接收消息。 

#### Destination 接口 （目标）

目标是一个包装了消息目标标识符的被管对象，消息目标是指消息发布和接收的地点，或者是队列，或者是主题。JMS管理员创建这些对象，然后用户通过JNDI发现它们。和连接工厂一样，管理员可以创建两种类型的目标，点对点模型的队列，以及发布者/订阅者模型的主题 

#### MessageProducer 接口 （消息生产者）

由会话创建的对象，用于发送消息到目标。用户可以创建某个目标的发送者，也可以创建一个通用的发送者，在发送消息时指定目标 

#### MessageConsumer 接口（消息消费者） 

由会话创建的对象，用于接收发送到目标的消息。消费者可以同步地（阻塞模式），或（非阻塞）接收队列和主题类型的消息。 

#### Message 接口（消息）

是在消费者和生产者之间传送的对象，也就是说从一个应用程序传送到另一个应用程序。 

#### 接口关系图

![img](https://images2015.cnblogs.com/blog/1053081/201612/1053081-20161225184356526-613434118.png) 

###### 注：此图是借鉴别处，来源：http://www.cnblogs.com/chenpi/p/5559349.html



### JMS 应用程序 案例

##### 消息生产者

```java
package queue;


import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * Created by Mr.Dxs on 2018/11/13.
 * 消息生产者
 * 点对点通信模型  （消息队列）
 */
public class Producer {

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


        // 6、创建 消息生产者
        MessageProducer producer = session.createProducer(destination);

        // 7、创建消息 并发送到 目的队列
        for (int i = 0; i < 10; i++) {
            TextMessage message = session.createTextMessage("发送消息："+i);
            producer.send(message);
            System.out.println(message.getText());
        }

        // 8、关闭连接
        session.close();
        connection.close();
    }
}
```

##### 消息消费者

```java
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
```





## 文档资源

#### JMS 2.x 中文文档

http://doc.yonyoucloud.com/doc/JMS2CN/index.html    

感谢 [kimmking](https://github.com/kimmking) 与其他参与翻译工作的朋友，让我们这些英文不好的同志能够有机会学习。

#### JMS 1.1 中文文档

**百度云链接：**https://pan.baidu.com/s/1DKfiCeMrdf4unho2s2mY5g 
**提取码：**rpc3 

#### 借鉴博客

http://www.cnblogs.com/chenpi/p/5559349.html

其它博文不在一一列举，在此统一感谢！



## 技术交流

🐾 <a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=dcdd3d66762ab211689194912f87f082e1416c4a95313d48caf179871150fdd8">Hello World</a> &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;   👈

