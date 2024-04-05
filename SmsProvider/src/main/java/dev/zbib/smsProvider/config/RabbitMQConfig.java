package dev.zbib.smsProvider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration

public class RabbitMQConfig {

    public static final String CRON_QUEUE = "sms_cron_queue";
    public static final String LISTENER_QUEUE = "sms_listener_queue";

    // Exchange Name
    public static final String SMS_EXCHANGE = "sms_exchange";

    // Routing Keys
    public static final String ROUTING_KEY_SMS_CRON = "sms.cron";
    public static final String ROUTING_KEY_SMS_LISTENER = "sms.listener";


    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;


    @Bean
    public Queue smsListenerQueue() {
        return new Queue(LISTENER_QUEUE, true);
    }


    @Bean
    public Queue smsCronQueue() {
        return new Queue(CRON_QUEUE, true);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange smsExchange() {
        return new TopicExchange(SMS_EXCHANGE);
    }

    @Bean
    public Binding bindingListenerQueue() {
        return BindingBuilder
                .bind(smsListenerQueue())
                .to(smsExchange())
                .with(ROUTING_KEY_SMS_LISTENER);
    }

    @Bean
    public Binding bindingCronQueue() {
        return BindingBuilder
                .bind(smsCronQueue())
                .to(smsExchange())
                .with(ROUTING_KEY_SMS_CRON);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
