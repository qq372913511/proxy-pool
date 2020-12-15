package com.linzeming.proxypool.crawler.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;


@Configuration
public class MongodbConfig {
    static {
//        Properties properties = new Properties();
//        InputStream resourceAsStream = MongodbConfig.class.getClassLoader().getResourceAsStream("mongodb.properties");
//        try {
//            properties.load(resourceAsStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoClientURI uri = new MongoClientURI("mongodb://10.0.0.88:27017/proxy_pool?maxPoolSize=5000&waitQueueMultiple=5000");
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(uri);
        return simpleMongoDbFactory;
    }

//    @Bean
//    public MongoClient mongo() {
//        ConnectionString connectionString = new ConnectionString("mongodb://10.0.0.88:27017/proxy_pool");
//        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .build();
//        return MongoClients.create(mongoClientSettings);
//    }
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        //remove _class
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.afterPropertiesSet();
        return new MongoTemplate(mongoDbFactory(), converter);
    }
}
