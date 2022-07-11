package com.shyn.monkeysoft;

import static org.assertj.core.api.Assertions.assertThat;

import com.shyn.monkeysoft.common.CommonController;
import com.shyn.monkeysoft.department.DepartmentController;
import com.shyn.monkeysoft.user.MonkeyUserController;
import com.shyn.monkeysoft.user.MonkeyUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MonkeysoftApplicationTests {

	@Autowired
	CommonController commonController;
	@Autowired
	MonkeyUserController monkeyUserController;
	@Autowired
	DepartmentController departmentController;

	@Autowired
	MonkeyUserService monkeyUserService;


	@Test
	void controllersLoads() {
		assertThat(commonController).isNotNull();
		assertThat(monkeyUserController).isNotNull();
		assertThat(departmentController).isNotNull();
	}

	@Test
	void adminUserExists() {
		assertThat(monkeyUserService.loadUserByUsername("admin")).isNotNull();
	}



}
