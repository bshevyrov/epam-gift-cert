package com.epam.esm.config;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.Impl.GiftCertificateDAOImpl;
import com.epam.esm.dao.Impl.GiftCertificateTagDAOImpl;
import com.epam.esm.dao.Impl.TagDAOImpl;
import com.epam.esm.dao.TagDAO;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;

@Configuration(value = "DBConfig")
@Profile("dev")
public class SpringH2Configuration {


    @Bean
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:testdb;MODE=MySQL");
        ds.setUsername("sa");
        ds.setPassword("password");
//        ds.setInitialSize(5);
//        ds.setMaxActive(10);
//        ds.setMaxIdle(5);
//        ds.setMinIdle(2);

        return ds;
    }
    @Bean
    public void createH2DB(DataSource dataSource) throws FileNotFoundException, SQLException {
        ScriptRunner sr = new ScriptRunner(dataSource.getConnection());
        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader("resources/data.sql"));
        //Running the script
        sr.runScript(reader);
    }
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public TagDAO tagDAO() {
        TagDAOImpl tagDAO = new TagDAOImpl();
        tagDAO.setNamedParameterJDBCTemplate(namedParameterJdbcTemplate());
        return tagDAO;
    }

    @Bean
    public GiftCertificateDAO giftCertificateDAO() {
        GiftCertificateDAOImpl giftCertificateDAO = new GiftCertificateDAOImpl();
        giftCertificateDAO.setNamedParameterJDBCTemplate(namedParameterJdbcTemplate());
        return giftCertificateDAO;
    }

    @Bean
    public GiftCertificateTagDAO giftCertificateTagDAO() {
        GiftCertificateTagDAOImpl giftCertificateTagDAO = new GiftCertificateTagDAOImpl();
        giftCertificateTagDAO.setNamedParameterJDBCTemplate(namedParameterJdbcTemplate());
        return giftCertificateTagDAO;
    }
}