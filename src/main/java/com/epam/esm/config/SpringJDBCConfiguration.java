package com.epam.esm.config;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.Impl.GiftCertificateDAOImpl;
import com.epam.esm.dao.Impl.GiftCertificateTagDAOImpl;
import com.epam.esm.dao.Impl.TagDAOImpl;
import com.epam.esm.dao.TagDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SpringJDBCConfiguration {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/gift_card");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");
        return driverManagerDataSource;
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
