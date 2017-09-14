package fr.xebia.MowItNow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {
        ServiceConfig.class
} )
public class MainConfig {

}
