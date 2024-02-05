package com.study.settings;

import com.study.WithAccount;
import com.study.account.AccountRepository;
import com.study.domain.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SettingControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("프로필 수정폼 보여주는 테스트")
    @WithAccount("sky")
    void updateProfile_show() throws Exception {
        String bio = "수정합니다.";
        mockMvc.perform(get(SettingController.SETTINGS_PROFILE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name((SettingController.SETTINGS_PROFILE_VIEW_NAME)))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"));




    }

    @Test
    @DisplayName("프로필 수정 - 입력값이 정상")
    @WithAccount("sky")
    void updateProfile() throws Exception {
        mockMvc.perform(post(SettingController.SETTINGS_PROFILE_URL)
                .param("bio", "수정수정")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SettingController.SETTINGS_PROFILE_VIEW_NAME))
                .andExpect(flash().attributeExists("message"));

        Account byNickname = accountRepository.findByNickname("sky");
        assertEquals("수정수정", byNickname.getBio());
    }

    @Test
    @DisplayName("프로필 수정 - 입력값이 에러")
    @WithAccount("sky")
    void updateProfile_error() throws Exception {
        String bio = "수정수정더욱더 길게 길게 수정수정더욱더 길게 길게 수정수정더욱더 길게 길게 수정수정더욱더 길게 길게 수정수정더욱더 길게 길게 수정수정더욱더 수정수정";
        mockMvc.perform(post(SettingController.SETTINGS_PROFILE_URL)
                        .param("bio", bio)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name((SettingController.SETTINGS_PROFILE_VIEW_NAME)))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().hasErrors());

        Account byNickname = accountRepository.findByNickname("sky");
        assertNull(byNickname.getBio());
    }

}