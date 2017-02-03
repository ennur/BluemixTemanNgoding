
package com.dicoding.bluemixlinebot;

import com.dicoding.bluemixlinebot.dao.UserDao;
import com.dicoding.bluemixlinebot.dao.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class Config
{
    @Autowired
    Environment mEnv;

    @Bean
    public DataSource getDataSource()
    {
        String dbUrl="postgres://ub887a0362f824c83b6c5d81950f648aa:p4c5a3c5922124a718e3d7563398b898e@50.23.230.142:5433/df9a09ffc352d4a83b2a130ffa24ec686";
        String username="ub887a0362f824c83b6c5d81950f648aa";
        String password="p4c5a3c5922124a718e3d7563398b898e";
//        String[] serviceInfo = new String[0];
//        try {
//           serviceInfo = getServiceInfo();
//        } catch (Exception e) {
//
//
//        }

        DriverManagerDataSource ds=new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(dbUrl);
        ds.setUsername(username);
        ds.setPassword(password);

        return ds;
    }

//    @Bean
//    public String[] getServiceInfo() throws Exception {
//        CloudEnvironment environment = new CloudEnvironment();
//        if ( environment.getServiceDataByLabels("postgresql").size() == 0 ) {
//            throw new Exception( "No PostgreSQL service is bund to this app!!" );
//        }
//
//        String[] info = new String[3];
//        Map credential = (Map)((Map)environment.getServiceDataByLabels("postgresql").get(0)).get( "credentials" );
//
//        String host = (String)credential.get( "host" );
//        Integer port = (Integer)credential.get( "port" );
//        String db = (String)credential.get( "name" );
//        String username = (String)credential.get( "username" );
//        String password = (String)credential.get( "password" );
//
//        info[0] = "jdbc:postgresql://" + host + ":" + port + "/" + db;
//        info[1] = username;
//        info[2] = password;
//
//        return info;
//    }

    @Bean(name="com.linecorp.channel_secret")
    public String getChannelSecret()
    {
        return mEnv.getProperty("com.linecorp.channel_secret");
    }

    @Bean(name="com.linecorp.channel_access_token")
    public String getChannelAccessToken()
    {
        return mEnv.getProperty("com.linecorp.channel_access_token");
    }

    @Bean
    public UserDao getPersonDao()
    {
        return new UserDaoImpl(getDataSource());
    }
};
