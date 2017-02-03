
package com.dicoding.bluemixlinebot;

import com.dicoding.bluemixlinebot.dao.UserDao;
import com.dicoding.bluemixlinebot.dao.UserDaoImpl;
import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class Config
{
    @Autowired
    Environment mEnv;

    @Bean
    public DataSource getDataSource()
    {
        String dbUrl="postgres://u67fe2e06394b4484b5f3c8a4b7cd1264:pf27ac364f78e47f4aadede05cb434ae6@50.23.230.142:5433/d5ceddac87cfb4133944798eae75c85fc";
        String username="u67fe2e06394b4484b5f3c8a4b7cd1264";
        String password="pf27ac364f78e47f4aadede05cb434ae6";
        String[] serviceInfo = new String[0];
        try {
           serviceInfo = getServiceInfo();
        } catch (Exception e) {


        }

        DriverManagerDataSource ds=new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(serviceInfo[0]);
        ds.setUsername(serviceInfo[1]);
        ds.setPassword(serviceInfo[2]);

        return ds;
    }

    @Bean
    public String[] getServiceInfo() throws Exception {
        CloudEnvironment environment = new CloudEnvironment();
        if ( environment.getServiceDataByLabels("postgresql").size() == 0 ) {
            throw new Exception( "No PostgreSQL service is bund to this app!!" );
        }

        String[] info = new String[3];
        Map credential = (Map)((Map)environment.getServiceDataByLabels("postgresql").get(0)).get( "credentials" );

        String host = (String)credential.get( "host" );
        Integer port = (Integer)credential.get( "port" );
        String db = (String)credential.get( "name" );
        String username = (String)credential.get( "username" );
        String password = (String)credential.get( "password" );

        info[0] = "jdbc:postgresql://" + host + ":" + port + "/" + db;
        info[1] = username;
        info[2] = password;

        return info;
    }

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
