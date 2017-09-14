package fr.xebia.MowItNow.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "fr.xebia.MowItNow.service" })
public class ServiceConfig {

}
