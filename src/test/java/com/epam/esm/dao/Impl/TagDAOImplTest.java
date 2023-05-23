package com.epam.esm.dao.Impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagDAOImplTest {


    private final ConfigurableEnvironment devEnv = new StandardEnvironment();


    @BeforeAll
    public void init(){
        devEnv.setActiveProfiles("dev");
    }


    @Test
    void setNamedParameterJDBCTemplate() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void findAllByGiftCertificateId() {
    }

    @Test
    void existByName() {
    }

    @Test
    void findByGiftCertificateId() {
    }

    @Test
    void findByName() {
    }
}