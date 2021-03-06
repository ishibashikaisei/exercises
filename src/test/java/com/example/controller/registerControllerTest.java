package com.example.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.utill.SessionUtil;
import com.example.utill.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class})
class registerControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("???????????????????????????????????????")
	void testIndex() throws Exception {
		mockMvc.perform(get("/register"))
				.andExpect(view().name("address-set")).andReturn();
	}

	@Test
	@DisplayName("URL??????????????????????????????????????????")
	@Transactional
	@ExpectedDatabase(value = "classpath:check.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseSetup("classpath:empty.xlsx")
	void testReceiveMail() throws Exception{
		mockMvc.perform(post("/register/receive")
				.param("mailAddress","tanaka@yahoo.co.jp"))
				.andExpect(view().name("redirect:/register/rereceive")).andReturn();
	}

	@Test
	@DisplayName("???????????????????????????")
	
	void testReceiveMail1() throws Exception{
		mockMvc.perform(post("/register/receive")
				.param("mail","a"))
				.andExpect(view().name("address-set")).andReturn();
	}
	
	@Test
	@DisplayName("???????????????????????????????????????????????????????????????")
	@Transactional
	@DatabaseSetup("classpath:base.xlsx")
	void testReceiveMail2() throws Exception{
		mockMvc.perform(post("/register/receive")
				.param("mailAddress","tanaka@yahoo.co.jp"))
				.andExpect(view().name("send-error")).andReturn();
	}
	@Test
	@DisplayName("????????????????????????????????????????????????????????????????????????")
	@Transactional
	@ExpectedDatabase(value = "classpath:check2.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseSetup("classpath:base2.xlsx")
	void testReceiveMail3() throws Exception{
		mockMvc.perform(post("/register/receive")
				.param("mailAddress","tanaka@yahoo.co.jp"))
				.andExpect(view().name("redirect:/register/rereceive")).andReturn();
	}
	
	@Test
	@DisplayName("????????????????????????????????????????????????")
	@Transactional
	@DatabaseSetup("classpath:base3.xlsx")
	void testReceiveMail4() throws Exception{
		mockMvc.perform(post("/register/receive")
				.param("mailAddress","tanaka@yahoo.co.jp"))
				.andExpect(view().name("send-comp")).andReturn();
	}

	
	@Test
	@DisplayName("????????????????????????send-comp??????????????????")
	void testRereceive() throws Exception{
		mockMvc.perform(post("/register/rereceive"))
		.andExpect(view().name("send-comp")).andReturn();
	}

	@Test
	@DisplayName("URL????????????key???????????????????????????")
	@Transactional
	@DatabaseSetup("classpath:base4.xlsx")
	void testIndex2() throws Exception {
		mockMvc.perform(post("/register/regist")
				.param("key","12345"))
				.andExpect(view().name("register-user")).andReturn();
	}
	@Test
	@DisplayName("URL???????????????key???????????????????????????")
	@Transactional
	@DatabaseSetup("classpath:base5.xlsx")
	void testIndex22() throws Exception {
		mockMvc.perform(post("/register/regist")
				.param("key","12345"))
				.andExpect(view().name("url-error")).andReturn();
	}
	@Test
	@DisplayName("URL???????????????key?????????")
	@Transactional
	@DatabaseSetup("classpath:base5.xlsx")
	void testIndex23() throws Exception {
		mockMvc.perform(post("/register/regist")
				.param("key","1234"))
				.andExpect(view().name("url-error")).andReturn();
	}

	@Test
	@DisplayName("??????????????????????????????")
	void testRegisterFinish() throws Exception{
		mockMvc.perform(post("/register/complete")
				.param("pass","1234")
				.param("confirmpassword", "12345"))
				.andExpect(view().name("register-user")).andReturn();
	}
	
	@Test
	@DisplayName("pass??????????????????")
	void testRegisterFinish2() throws Exception{
		mockMvc.perform(post("/register/complete")
				.param("pass","")
				.param("confirmpassword", "111111"))
				.andExpect(view().name("register-user")).andReturn();
	}
	
	@Test
	@DisplayName("confirmpassword??????????????????")
	void testRegisterFinish3() throws Exception{
		mockMvc.perform(post("/register/complete")
				.param("pass","1111111")
				.param("confirmpassword", ""))
				.andExpect(view().name("register-user")).andReturn();
	}

	@Test
	@DisplayName("passOK users?????????????????????mail_url??????????????????")
	@Transactional
	@ExpectedDatabase(value = "classpath:check3.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseSetup("classpath:base6.xlsx")
	void testRegisterFinish4() throws Exception {
		MockHttpSession session = SessionUtil.createMockHttpSession1();
		mockMvc.perform(post("/register/complete").session(session)
				.param("pass","12345678")
				.param("confirmpassword","12345678")
				.param("name","??????")
				.param("kana","?????????")
				.param("zipcode","260-0001")
				.param("address","?????????")
				.param("tel","043-2222-3456")
				.param("delFlg","0"))
				.andExpect(view().name("redirect:/register/recomplete")).andReturn();
	}
	
	@Test
	void testRecomp()throws Exception{
		mockMvc.perform(post("/register/recomplete"))
		.andExpect(view().name("register-comp")).andReturn();
	}

}
