package org.config;

import org.modelmapper.ModelMapper;
import org.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final CounterRepository counterRepository;

    public AppConfig(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Bean
    @Qualifier("standardModelMapper")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    @Qualifier("modelMapperNullSkip")
    public ModelMapper modelMapperNullSkip(){
        ModelMapper modelMapperNullSkip = new ModelMapper();
        modelMapperNullSkip.getConfiguration().setSkipNullEnabled(true);
        return modelMapperNullSkip;
    }
}
