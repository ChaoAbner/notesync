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
public class NoteControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private RequestBuilder request = null;

    private String path;

    @Before
    public void setUp() {
        this.path = "/note";
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 根据id查找note
     * @throws Exception
     */
    @Test
    public void findNoteByNoteId() throws Exception {
        mockMvc.perform(get(path + "/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(0)))
                .andDo(print());
    }

    /**
     * 查找用户的note列表
     * @throws Exception
     */
    @Test
    public void findNotesByUsername() throws Exception {
        mockMvc.perform(get(path + "/user/用户2")
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(0)))
                .andDo(print());
    }

    /**
     * 插入note
     * @throws Exception
     */
    @Test
    public void insertNote() throws Exception {

        mockMvc.perform(post(path + "/detail/user/用户2")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", "标题111")
                .param("content", "内容1222")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(0)))
                .andDo(print());
    }

    /**
     * 更新note
     * @throws Exception
     */
    @Test
    public void updateNote() throws Exception {
        mockMvc.perform(put(path + "/15/user/用户2")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .param("title", "标题222")
                .param("content", "内容222")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(0)))
                .andDo(print());
    }

    /**
     * 删除note
     * @throws Exception
     */
    @Test
    public void deleteNote() throws Exception {
        mockMvc.perform(delete(path + "/15/user/用户2")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(0)))
                .andDo(print());
    }

}
