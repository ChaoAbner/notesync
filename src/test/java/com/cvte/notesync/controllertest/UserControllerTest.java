package com.cvte.notesync.controllertest;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private RequestBuilder request = null;

    private String path;

    @Before
    public void setUp() {
        this.path = "/user";
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 根据用户名获取用户
     * @throws Exception
     */
    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get(path + "/用户5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(0)))
                .andDo(print());
    }

    /**
     * 插入用户
     * @throws Exception
     */
    @Test
    public void insertUser() throws Exception {
        mockMvc.perform(post(path + "/用户5")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(0)))
                .andDo(print());
    }

    /**
     * 删除用户
     * @throws Exception
     */
    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(path + "/用户5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(0)))
                .andDo(print());
    }
}
