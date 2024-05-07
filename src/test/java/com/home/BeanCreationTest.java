package com.home;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.home.mapper.DongCodeMapper;
import com.home.service.DongCodeService;


@SpringBootTest
class BeanCreationTest {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	DongCodeMapper mapper;
	
	@Autowired
	DongCodeService service;
	
	
	@Test
	void test() {
		assertNotNull(ds);
		assertNotNull(mapper);
		assertNotNull(service);
		
	}

}
