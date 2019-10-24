package com.sprint.dash.auth.configure;

import com.sprint.dash.auth.properties.DashAuthProperties;
import com.sprint.dash.auth.properties.DashClientsProperties;
import com.sprint.dash.auth.service.DashUserDetailService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class DashAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private DashUserDetailService dashUserDetailService;

    @Autowired
    private DashAuthProperties dashAuthProperties;

    public DashAuthorizationServerConfigurer(){}

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        DashClientsProperties[] clientsArray = dashAuthProperties.getClients();
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();

        if(ArrayUtils.isNotEmpty(clientsArray)){
            //配置文件加载 客户端信息
            for(DashClientsProperties client : clientsArray){

                if (StringUtils.isBlank(client.getClient())) {
                    throw new Exception("client不能为空");
                }
                if (StringUtils.isBlank(client.getSecret())) {
                    throw new Exception("secret不能为空");
                }

                String[] grantTypes = StringUtils.splitByWholeSeparatorPreserveAllTokens(client.getGrantType(), ",");
                String[] scopes = StringUtils.splitByWholeSeparatorPreserveAllTokens(client.getScope(), ",");
                String[] redirectUris = StringUtils.splitByWholeSeparatorPreserveAllTokens(client.getRedirectUri(), ",");

                builder.withClient(client.getClient())
                        .secret(passwordEncoder.encode(client.getSecret()))
                        .authorizedGrantTypes(grantTypes)
                        .scopes(scopes)
                        .redirectUris(redirectUris)
                        .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
                        .refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());

            }
        }

////客户端模式
//        clients.inMemory()
//                .withClient("client_1")
//                .authorizedGrantTypes("client_credentials", "refresh_token")
//                .scopes("all","read", "write")
//                .authorities("client_credentials")
//                .accessTokenValiditySeconds(7200)
//                .secret(passwordEncoder.encode("123456"))
////密码模式
//                .and().withClient("client_2")
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("all","read", "write")
//                .accessTokenValiditySeconds(7200)
//                .refreshTokenValiditySeconds(10000)
//                .authorities("password")
//                .secret(passwordEncoder.encode("123456"))
////授权码模式
//                .and().withClient("client_3")//.authorities("authorization_code","refresh_token")
//                .secret(passwordEncoder.encode("123456"))
//                .authorizedGrantTypes("authorization_code", "refresh_token")
//                .scopes("all","read", "write", "read_user_info")
//                .accessTokenValiditySeconds(7200)
//                .refreshTokenValiditySeconds(10000)
//                .redirectUris("http://localhost:8080/callback","http://localhost:8080/signin")
////简化模式
//                .and().withClient("client_4")//.authorities("authorization_code","refresh_token")
//                .secret(passwordEncoder.encode("123456"))
//                .authorizedGrantTypes("implicit", "refresh_token")
//                .scopes("all","read", "write", "read_user_info")
//                .accessTokenValiditySeconds(7200)
//                .refreshTokenValiditySeconds(10000)
//                .redirectUris("http://localhost:8080/callback","http://localhost:8080/signin")
////全模式
//                .and().withClient("client_test")
//                .secret(passwordEncoder.encode("123456"))
//                .authorizedGrantTypes("all flow")
//                .authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token","password", "implicit")
//                .redirectUris("http://localhost:8080/callback","http://localhost:8080/signin")
//                .scopes("all","read", "write")
//                .accessTokenValiditySeconds(7200)
//                .refreshTokenValiditySeconds(10000);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .userDetailsService(dashUserDetailService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /* 配置token获取合验证时的策略 */
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }



}
