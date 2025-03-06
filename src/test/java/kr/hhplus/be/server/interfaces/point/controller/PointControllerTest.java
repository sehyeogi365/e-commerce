package kr.hhplus.be.server.interfaces.point.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.point.service.PointService;
import kr.hhplus.be.server.interfaces.api.point.dto.PointRequest;
import kr.hhplus.be.server.interfaces.api.point.dto.PointResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hibernate.Hibernate.get;
import static org.mockito.Mockito.when;
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

    @MockitoBean
    private PointService pointService;

//    1. 일단 레디스 사용한 곳 임시로 주석처리하기
//2. 레디스까지 통째로 세팅해서 다시 테스트 해보기

    @Test
    @DisplayName("포인트 잔액조회")
    void 포인트_잔액조회() throws Exception {
        // given
        long userId = 1L;
        int point = 10000;

        PointResponse pointResponse = new PointResponse(userId, point);

        when(pointService.getUserPoint(userId)).thenReturn(pointResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/point/{userId}", userId)
                        .content(objectMapper.writeValueAsString(pointResponse))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.point").value(10000));
    }



    @Test
    @DisplayName("포인트 충전")
    void 포인트_충전() throws Exception {
        // given
        long userId = 1L;
        int point = 100000;

        PointRequest pointRequest = new PointRequest(userId, point);

        when(pointService.chargePoint(userId,point)).thenReturn(pointRequest.getPoint());
        // when&then
        mockMvc.perform(post("/api/v1/point/charge")
                        .content(objectMapper.writeValueAsString(pointRequest))
                        //.content(validRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true));
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.point").value(100000));
    }

    @Test
    @DisplayName("포인트 충전: 10000단위가 아닐시")
    void 포인트_충전2() throws Exception {
        // given
        long userId = 1L;
        int point = 10001;

        PointRequest pointRequest = new PointRequest(userId, point);

        when(pointService.chargePoint(userId,point)).thenReturn(pointRequest.getPoint());

        // when&then
        mockMvc.perform(post("/api/v1/point/charge")
                        .content(objectMapper.writeValueAsString(pointRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.point").value(10001));
    }
}