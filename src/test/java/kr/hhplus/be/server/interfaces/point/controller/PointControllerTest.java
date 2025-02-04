package kr.hhplus.be.server.interfaces.point.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.interfaces.point.dto.PointRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    1. 일단 레디스 사용한 곳 임시로 주석처리하기
//2. 레디스까지 통째로 세팅해서 다시 테스트 해보기
    @Test
    @DisplayName("포인트 충전")
    void 포인트_충전() throws Exception {
        //given
        int userId = 1;
        int point = 10000;

        PointRequest pointRequest = new PointRequest(userId, point);

        //when&then
        mockMvc.perform(post("/api/v1/point/charge")
                        .content(objectMapper.writeValueAsString(pointRequest))
                        //.content(validRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true));
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.point").value(10000));
    }

    @Test
    @DisplayName("포인트 충전: 10000단위가 아닐시")
    void 포인트_충전2() throws Exception {
        //given
//        String validRequest = """
//            {
//                "userId": 1,
//                "point": 10001
//            }
//        """;
        int userId = 1;
        int point = 10000;

        PointRequest pointRequest = new PointRequest(userId, point);

        //when&then
        mockMvc.perform(post("/api/v1/point/charge")
                        .content(objectMapper.writeValueAsString(pointRequest))
                        //.content(validRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true));
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.point").value(10001));
    }
}