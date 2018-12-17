package com.teachingplan.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@EnableIntegration
@IntegrationComponentScan
@Configuration
public class TcpSocketConfig {

    @MessagingGateway(defaultRequestChannel="toTcp")
    public interface Gateway {

        String viaTcp(String in);

    }

    @Bean
    @ServiceActivator(inputChannel="toTcp")
    public MessageHandler tcpOutGate(AbstractClientConnectionFactory connectionFactory) {
        TcpOutboundGateway gate = new TcpOutboundGateway();
        gate.setConnectionFactory(connectionFactory);
        gate.setOutputChannelName("resultToString");
        return gate;
    }

    @Bean
    public TcpInboundGateway tcpInGate(AbstractServerConnectionFactory connectionFactory)  {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(connectionFactory);
        inGate.setRequestChannel(fromTcp());
        return inGate;
    }

    @Bean
    public MessageChannel fromTcp() {
        return new DirectChannel();
    }

    @MessageEndpoint
    public static class Echo {

        @Transformer(inputChannel="application.fromTcp", outputChannel="toEcho")
        public String convert(byte[] bytes) {
            return new String(bytes);
        }

        @ServiceActivator(inputChannel="toEcho")
        public String upCase(String in) {
            return in.toUpperCase();
        }

        @Transformer(inputChannel="resultToString")
        public String convertResult(byte[] bytes) {
            return new String(bytes);
        }

    }

    @Bean
    public AbstractClientConnectionFactory clientCF() {
        return new TcpNetClientConnectionFactory("localhost", 8888);
    }

    @Bean
    public AbstractServerConnectionFactory serverCF() {
        return new TcpNetServerConnectionFactory(8888);
    }

}
