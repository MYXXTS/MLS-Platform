package com.myxxts.mls.server.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class MLSApplicationConfig implements WebMvcConfigurer {

  @Bean
  Jackson2ObjectMapperBuilderCustomizer objectCustomizer () {
    return builder -> {
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      builder.deserializers(new LocalDateDeserializer(dateFormatter));
      builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
      builder.serializers(new LocalDateSerializer(dateFormatter));
      builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
    };
  }

}
