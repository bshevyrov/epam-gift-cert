package com.epam.esm.config;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.Impl.GiftCertificateDAOImpl;
import com.epam.esm.dao.Impl.GiftCertificateTagDAOImpl;
import com.epam.esm.dao.Impl.TagDAOImpl;
import com.epam.esm.dao.TagDAO;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
public class SpringJDBCConfiguration {

    @Bean
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/gift_card");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
        return ds;
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
