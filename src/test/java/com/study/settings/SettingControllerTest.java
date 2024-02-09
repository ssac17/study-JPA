package com.study.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.WithAccount;
import com.study.account.AccountRepository;
import com.study.account.AccountService;
import com.study.domain.Account;
import com.study.domain.Tag;
import com.study.settings.form.TagForm;
import com.study.tag.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class SettingControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    AccountService accountService;

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
                .andExpect(redirectedUrl(SettingController.SETTINGS_PROFILE_URL))
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

    @Test
    @DisplayName("계정의 태그 수정 폼")
    @WithAccount("sky")
    void updateTagsForm() throws Exception {
        mockMvc.perform(get(SettingController.SETTINGS_TAGS_URL))
                .andExpect(view().name(SettingController.SETTINGS_TAGS_VIEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("whitelist"))
                .andExpect(model().attributeExists("tags"));
    }

    @Test
    @DisplayName("계정의 태그 추가")
    @WithAccount("sky")
    void addTags() throws Exception {

        TagForm tagForm = new TagForm();
        tagForm.setTagTitle("Spring");

        mockMvc.perform(post(SettingController.SETTINGS_TAGS_URL + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagForm)))
                .andDo(print())
                .andExpect(status().isOk());

        Tag tag = tagRepository.findByTitle("Spring");
        assertNotNull(tag);
        assertTrue(accountRepository.findByNickname("sky").getTags().contains(tag));
    }

    @Test
    @DisplayName("계정의 태그 삭제")
    @WithAccount("sky")
    void removeTags() throws Exception {

        Account account = accountRepository.findByNickname("sky");
        Tag newTag = tagRepository.save(Tag.builder().title("Spring").build());

        accountService.addTag(account, newTag);

        assertTrue(account.getTags().contains(newTag));

        TagForm tagForm = new TagForm();
        tagForm.setTagTitle("Spring");

        mockMvc.perform(post(SettingController.SETTINGS_TAGS_URL + "/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagForm)))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(account.getTags().contains(newTag));
    }
}