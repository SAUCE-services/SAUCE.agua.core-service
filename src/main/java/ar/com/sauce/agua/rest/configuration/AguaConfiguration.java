/**
 * 
 */
package ar.com.sauce.agua.rest.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author daniel
 *
 */
@Configuration
@EnableJpaAuditing
@PropertySource("classpath:config/sauce.properties")
public class AguaConfiguration {

}
