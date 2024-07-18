/**
 * 
 */
package sauce.agua.rest.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author daniel
 *
 */
@Configuration
@EnableJpaAuditing
@EnableDiscoveryClient
@PropertySource("classpath:config/sauce.properties")
public class AguaConfiguration {

}
