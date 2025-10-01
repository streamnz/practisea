package com.streamnz.practisea;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 集成测试 - 验证Spring上下文加载
 * 展示TDD中的集成测试层面
 * 
 * @Author cheng hao
 * @Date 26/09/2025 21:08
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("应用集成测试")
class PractiseaApplicationTests {

    @Test
    @DisplayName("Given Spring Boot应用, When 启动应用, Then 上下文加载成功")
    void givenSpringBootApplication_whenStartApplication_thenContextLoadsSuccessfully() {
        // 这个测试验证Spring Boot应用能够正常启动
        // 所有Bean都能正确注入和配置
        // 如果上下文加载失败，测试会失败
    }

}
