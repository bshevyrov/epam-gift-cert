package com.epam.esm.dao.Impl;

import com.epam.esm.config.DBConfig;
import com.epam.esm.config.MainConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {MainConfiguration.class,DBConfig.class}, loader = AnnotationConfigWebContextLoader.class )
 class TagDAOImplTest {


    private final ConfigurableEnvironment devEnv = new StandardEnvironment();
    @InjectMocks
    private TagDAOImpl tagDAO;

    @InjectMocks
    private GiftCertificateTagDAOImpl giftCertificateTagDAO;
    @InjectMocks
    private GiftCertificateDAOImpl giftCertificateDAO;

    private Tag tag;
    private Tag tagAnother;
    private Tag tagThird;


    @Autowired
    DBConfig config ;

    public void createH2DB(DataSource dataSource) throws FileNotFoundException, SQLException {
        ScriptRunner sr = new ScriptRunner(dataSource.getConnection());
        Reader reader = new BufferedReader(new FileReader("src/main/resources/data.sql"));
        sr.runScript(reader);
    }

//    public DataSource dataSource() {
//        DataSource ds = new DataSource();
//        ds.setDriverClassName("org.h2.Driver");
//        ds.setUrl("jdbc:h2:mem:testdb;MODE=MySQL");
//        ds.setUsername("sa");
//        ds.setPassword("password");
//        return ds;
//    }

    @BeforeEach
    public void init() throws SQLException, FileNotFoundException {

        devEnv.setActiveProfiles("dev");
AnnotationConfigWebContextLoader annotationConfigWebContextLoader = new AnnotationConfigWebContextLoader();
//annotationConfigWebContextLoader.processContextConfiguration(config);

//config =  rootContext.getBean("DBConfig",DBConfig.class);

        createH2DB( config.dataSource());
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(giftCertificateTagDAO, "namedParameterJdbcTemplate", new NamedParameterJdbcTemplate( config.dataSource()));

        ReflectionTestUtils.setField(tagDAO, "namedParameterJdbcTemplate", new NamedParameterJdbcTemplate( config.dataSource()));
        ReflectionTestUtils.setField(tagDAO, "keyHolder", new GeneratedKeyHolder());

        ReflectionTestUtils.setField(giftCertificateDAO, "namedParameterJdbcTemplate", new NamedParameterJdbcTemplate( config.dataSource()));
        ReflectionTestUtils.setField(giftCertificateDAO, "keyHolder", new GeneratedKeyHolder());

        tag = new Tag("tag");
        tagAnother = new Tag("tagAnother");
        tagThird = new Tag("tagThird");
    }

    @Test
    void createAndFindById() {
        tag.setId(tagDAO.create(tag));
        assertEquals(tag, tagDAO.findById(tag.getId()));
    }

    @Test
    void findAll() {
        List<Tag> expected = new ArrayList<>();
        tag.setId(tagDAO.create(tag));
        expected.add(tag);
        tagAnother.setId(tagDAO.create(tagAnother));
        expected.add(tagAnother);
        tagThird.setId(tagDAO.create(tagThird));
        expected.add(tagThird);
        List<Tag> actual = tagDAO.findAll();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);

    }

    @Test
    void deleteById() {
        List<Tag> expected = new ArrayList<>();
        tag.setId(tagDAO.create(tag));
        expected.add(tag);
        tagAnother.setId(tagDAO.create(tagAnother));
        tagThird.setId(tagDAO.create(tagThird));
        expected.add(tagThird);
        tagDAO.deleteById(tagAnother.getId());
        List<Tag> actual = tagDAO.findAll();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

//TODO
  /*  @Test
    void update() {
        assertThrowsExactly(UnsupportedOperationException.class, ()->tagDAO.update(new HashMap<>()));
    }
*/
    @Test
    void findAllByGiftCertificateId() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("gsName");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));

        tag.setId(tagDAO.create(tag));
        tagAnother.setId(tagDAO.create(tagAnother));
        tagThird.setId(tagDAO.create(tagThird));

        List<Tag> expected = new ArrayList<Tag>(){{add(tag);add(tagAnother);add(tagThird);}};


        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(),tag.getId()));
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(),tagAnother.getId()));
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(),tagThird.getId()));

        List<Tag> actual = tagDAO.findAllByGiftCertificateId(giftCertificate.getId());

        assertEquals(expected.size(),actual.size());
        assertEquals(expected,actual);
    }


    @Test
    void existByName() {
        tag.setId(tagDAO.create(tag));
        assertEquals(true, tagDAO.existByName(tag.getName()));
        assertEquals(false,tagDAO.existByName(tag.getName() + "Another"));
    }


    @Test
    void findByName() {
        tag.setId(tagDAO.create(tag));
        assertEquals(tag, tagDAO.findByName(tag.getName()));
    }
}