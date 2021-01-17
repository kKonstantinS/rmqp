package com.example.rmqp;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class RmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmqpApplication.class, args);
    }

    static final String topicExchangeName = "amq.topic";
    static final String fanExchangeName = "amq.fanout";

    static final String queueName = "queue";
    static final String otherQueue = "otherQueue";

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    Queue otherQueue() {
        return new Queue(otherQueue, true);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanExchangeName);
    }

    @Bean
    Binding bindingTopic(TopicExchange exchange) {
        return BindingBuilder.bind(queue()).to(exchange).with("route.#");
    }

    @Bean
    Binding bindingOtherTopic(TopicExchange exchange) {
        return BindingBuilder.bind(otherQueue()).to(exchange).with("route.route1");
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }
}
