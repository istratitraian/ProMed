/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 * @author I.T.W764
 */
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private IndexController indexController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    public IndexControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("html")))
                ;
    }

    @Test
    public void contexLoads() throws Exception {
        assertThat(indexController).isNotNull();
        assertThat(restTemplate).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void testIndex() {
        String object = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
        assertThat(object).contains("html");
    }

    @Test
    public void testRest() {

        List object = this.restTemplate.getForObject("http://localhost:" + port + "/rest", List.class);
        System.out.println("testRest() " + object);
        assertThat(object).isNotEmpty();
    }

}
