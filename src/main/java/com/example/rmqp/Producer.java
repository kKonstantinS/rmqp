package com.example.rmqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/producer")
public class Producer implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TopicExchange topicExchange;

    @PostMapping("/send")
    public void send(@RequestParam(name = "msg") String message) {
        rabbitTemplate.convertAndSend(RmqpApplication.topicExchangeName, "route.route1", message);
        //return (String) rabbitTemplate.convertSendAndReceive("route.route2",message + message);
    }
    @PostMapping("/send1")
    public void send1(@RequestParam(name = "msg") String message) {
        rabbitTemplate.convertAndSend(RmqpApplication.topicExchangeName, "route.route2", message);
    }
    @PostMapping("/send2")
    public void send2(@RequestParam(name = "msg") String message) {
        rabbitTemplate.convertAndSend(RmqpApplication.topicExchangeName, "route.#", "all");
    }

    @Override
    public void run(String... args) throws Exception {
        rabbitTemplate.convertAndSend(RmqpApplication.topicExchangeName, "route.#", "d1d1d1d1");
    }
}
