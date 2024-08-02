package com.zaprent.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig {

    @Value("${elasticsearch.host}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port}")
    private int elasticsearchPort;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // Create the low-level RestClient
        RestClient lowLevelClient = RestClient.builder(
                new HttpHost(elasticsearchHost, elasticsearchPort, "http")
        ).build();

        // Create the transport with the low-level client
        ElasticsearchTransport transport = new RestClientTransport(lowLevelClient, new JacksonJsonpMapper());

        // Create the high-level Elasticsearch client
        return new ElasticsearchClient(transport);
    }
}

