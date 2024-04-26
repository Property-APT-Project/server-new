package com.home;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BeanCreationTest {
	
	@Autowired
	DataSource ds;
	@Test
	void test() {
		assertNotNull(ds);
		
	}

}
