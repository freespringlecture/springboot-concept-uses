package me.freelife.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    /**
     * JSON 타입 테스트
     * @throws Exception
     */
    @Test
    public void createUser_JSON() throws Exception {
        String userJson = "{\"username\":\"freelife\", \"password\":\"123\"}";
        //요청을 만드는 단계
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson))
                //응답 확인단계
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username", is(equalTo("freelife"))))
                    .andExpect(jsonPath("$.password", is(equalTo("123"))));

    }

    /**
     * XML 타입 테스트
     * @throws Exception
     */
    @Test
    public void createUser_XML() throws Exception {
        String userJson = "{\"username\":\"freelife\", \"password\":\"123\"}";
        //요청을 만드는 단계
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_XML)
                .content(userJson))
                //응답 확인단계
                    .andExpect(status().isOk())
                    .andExpect(xpath("/User/username")
                            .string("freelife"))
                    .andExpect(xpath("/User/password")
                            .string("123"));

    }
}
