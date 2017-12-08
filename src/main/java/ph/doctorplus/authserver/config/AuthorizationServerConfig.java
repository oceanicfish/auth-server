package ph.doctorplus.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()")              //who can request token
                .checkTokenAccess("isAuthenticated()");     //how can validate the token
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        /**
         * authorization code grant type.
         */

        clients.inMemory()
                .withClient("my_client")
                .secret("secret")
                //Grant type mean what kind of authorization will be given to the client App by Resource Owner
                .authorizedGrantTypes("authorization_code", "implicit", "password", "client_credentials", "refresh_token")
                //can be customized
                .scopes("read", "write", "trust")
                //access token validation should be shorter
                .accessTokenValiditySeconds(30)
                //refresh token validation should be longer than access token.
                .refreshTokenValiditySeconds(2592000)
                .autoApprove(true);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}
