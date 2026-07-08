/**
 * 
 */
package sauce.agua.rest.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author daniel
 *
 */
@Configuration
@EnableJpaAuditing
@EnableFeignClients(basePackages = "sauce.agua.rest.extern.client")
@PropertySource("classpath:config/sauce.properties")
public class AguaConfiguration {

}
