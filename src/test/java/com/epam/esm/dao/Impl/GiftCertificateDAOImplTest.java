package com.epam.esm.dao.Impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateDAOImplTest {

    private final ConfigurableEnvironment devEnv = new StandardEnvironment();

    @InjectMocks
    private GiftCertificateDAOImpl giftCertificateDAO;

    @InjectMocks
    private TagDAOImpl tagDAO;
    @InjectMocks
    private GiftCertificateTagDAOImpl giftCertificateTagDAO;

    private GiftCertificate giftCertificate;
    private Tag tagName;
    private Tag tag;

    public void createH2DB(DataSource dataSource) throws FileNotFoundException, SQLException {
        ScriptRunner sr = new ScriptRunner(dataSource.getConnection());
        Reader reader = new BufferedReader(new FileReader("src/main/resources/data.sql"));
        sr.runScript(reader);
    }

    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:testdb;MODE=MySQL");
        ds.setUsername("sa");
        ds.setPassword("password");
        return ds;
    }

    @BeforeAll

    public void init() throws SQLException, FileNotFoundException {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(giftCertificateTagDAO, "namedParameterJdbcTemplate", new NamedParameterJdbcTemplate(dataSource()));

        ReflectionTestUtils.setField(tagDAO, "namedParameterJdbcTemplate", new NamedParameterJdbcTemplate(dataSource()));
        ReflectionTestUtils.setField(tagDAO, "keyHolder", new GeneratedKeyHolder());

        ReflectionTestUtils.setField(giftCertificateDAO, "namedParameterJdbcTemplate", new NamedParameterJdbcTemplate(dataSource()));
        ReflectionTestUtils.setField(giftCertificateDAO, "keyHolder", new GeneratedKeyHolder());

//        devEnv.setActiveProfiles("dev");
        giftCertificate = new GiftCertificate();
        giftCertificate.setName("tes22");
        giftCertificate.setDescription("tes22");
        giftCertificate.setDuration(2);
        giftCertificate.setPrice(2);

        tagName = new Tag("tagName");
        tag = new Tag("tag");

//        createH2DB(dataSource());
    }
    @BeforeEach
    public void createDb() throws SQLException, FileNotFoundException {
        createH2DB(dataSource());

    }


    @Test
//    @Sql(scripts={"classpath:src/main/resources/data.sql"},
//            config=@SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
//            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findById() {

        long createdGiftCertificateId = giftCertificateDAO.create(giftCertificate);
        giftCertificate.setId(createdGiftCertificateId);
        GiftCertificate byId = giftCertificateDAO.findById(createdGiftCertificateId);
        assertEquals(giftCertificate, byId);
    }

    @Test
    void findAll() {

        List<GiftCertificate> actual = giftCertificateDAO.findAll();
        assertEquals(0, actual.size());

        giftCertificateDAO.create(giftCertificate);
        giftCertificateDAO.create(giftCertificate);
        giftCertificateDAO.create(giftCertificate);
        actual = giftCertificateDAO.findAll();

        assertEquals(3, actual.size());
    }

    @Test
    void testFindAll() {

    }

    @Test
    void deleteById() {
        GiftCertificate giftCertificate = new GiftCertificate();
        List<GiftCertificate> expected = new ArrayList<>();
        giftCertificate.setName("first");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        expected.add(SerializationUtils.clone(giftCertificate));

        giftCertificate.setName("second");
        long secondId = giftCertificateDAO.create(giftCertificate);

        giftCertificate.setName("third");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        expected.add(SerializationUtils.clone(giftCertificate));

        giftCertificateDAO.deleteById(secondId);
        assertEquals(expected, giftCertificateDAO.findAll());
        assertEquals(expected.size(), giftCertificateDAO.findAll().size());
    }


    @Test
    void findByTagName() {
        List<GiftCertificate> expected = new ArrayList<>();

        long tagId = tagDAO.create(tag);
        tag.setId(tagId);
        long tagNameId = tagDAO.create(tagName);
        tagName.setId(tagNameId);

        giftCertificate.setName("first");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        expected.add(SerializationUtils.clone(giftCertificate));
        giftCertificateDAO.create(giftCertificate);
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagNameId));

        giftCertificate.setName("second");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        expected.add(SerializationUtils.clone(giftCertificate));
        giftCertificateDAO.create(giftCertificate);
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagNameId));
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagId));

        giftCertificate.setName("third");
        giftCertificate.setTags(new ArrayList<Tag>() {{
            add(tag);
        }});
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        giftCertificateDAO.create(giftCertificate);
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagId));

        assertEquals(2, giftCertificateDAO.findByTagName("tagName").size());
        assertEquals(expected, giftCertificateDAO.findByTagName("tagName"));
    }

    @Test
    void existById() {
        giftCertificate.setName("first");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));

        assertEquals(true,giftCertificateDAO.existById(giftCertificate.getId()));
        assertEquals(false,giftCertificateDAO.existById(giftCertificate.getId()+1));
    }


    @Test
    void update() {
        giftCertificate.setName("first");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
//        giftCertificateDAO.update(new HashMap<String, Object>(){{put("id",giftCertificate.getId());put("name","second");}});
    //TODO
        giftCertificate.setName("second");
        assertEquals(giftCertificate,giftCertificateDAO.findById(giftCertificate.getId()));

    }
}