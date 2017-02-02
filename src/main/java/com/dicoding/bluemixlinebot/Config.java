
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
        String dbUrl="postgres://u67fe2e06394b4484b5f3c8a4b7cd1264:pf27ac364f78e47f4aadede05cb434ae6@50.23.230.142:5433/d5ceddac87cfb4133944798eae75c85fc";
        String username="u67fe2e06394b4484b5f3c8a4b7cd1264";
        String password="pf27ac364f78e47f4aadede05cb434ae6";

        DriverManagerDataSource ds=new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(dbUrl);
        ds.setUsername(username);
        ds.setPassword(password);

        return ds;
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
