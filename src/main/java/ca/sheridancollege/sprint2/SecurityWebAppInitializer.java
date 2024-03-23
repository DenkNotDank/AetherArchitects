package ca.sheridancollege.sprint2;

import ca.sheridancollege.sprint2.security.SecurityConfiguration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebAppInitializer(){
        super(SecurityConfiguration.class);
    }
}
