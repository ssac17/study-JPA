package com.studygo.study;


import com.studygo.account.CurrentUser;
import com.studygo.domain.Account;
import com.studygo.domain.Study;
import com.studygo.study.form.StudyDescriptionForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study/{path}/settings")
@Log4j2
public class StudySettingController {

    private final StudyService studyService;
    private final ModelMapper modelMapper;


    @GetMapping("/description")
    public String viewStudySetting(@CurrentUser Account account, @PathVariable String path, Model model, HttpServletRequest request) {
        log.info("-- request --");
        log.info(request.getRequestURL());
        Study study = studyService.getStudyToUpdate(account, path);
        model.addAttribute(account);
        model.addAttribute(study);
        model.addAttribute(modelMapper.map(study, StudyDescriptionForm.class));
        return "study/settings/description";
    }

    @PostMapping("/description")
    public String updateStudyInfo(@CurrentUser Account account, @PathVariable String path,
                                  @Valid StudyDescriptionForm studyDescriptionForm, Errors errors,
                                  Model model, RedirectAttributes attributes) {
        Study study = studyService.getStudyToUpdate(account, path);

        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(study);
            return "study/settings/description";
        }

        studyService.updateStudyDescription(study, studyDescriptionForm);
        attributes.addFlashAttribute("message", "스터디 소개를 수정했습니다.");
        return "redirect:/study/" + study.getEncodedPath() + "/settings/description";
    }


    @GetMapping("/banner")
    public String studyImageForm(@CurrentUser Account account, @PathVariable String path, Model model) {
        Study study = studyService.getStudyToUpdate(account, path);
        model.addAttribute(account);
        model.addAttribute(study);
        return "study/settings/banner";
    }

    @PostMapping("/banner")
    public String studyImageSubmit(@CurrentUser Account account, @PathVariable String path,
                                   String image, RedirectAttributes attributes) {
        Study study = studyService.getStudyToUpdate(account, path);
        studyService.updateStudyImage(study, image);
        attributes.addFlashAttribute("message", "스터디 이미지를 수정했습니다.");
        return "redirect:/study/" + study.getEncodedPath() + "/settings/banner";
    }

    @PostMapping("/banner/enable")
    public String enableStudyBanner(@CurrentUser Account account, @PathVariable String path) {
        Study study = studyService.getStudyToUpdate(account, path);
        studyService.enableStudyBanner(study);
        return "redirect:/study/" + study.getEncodedPath() + "/settings/banner";
    }

    @PostMapping("/banner/disable")
    public String disableStudyBanner(@CurrentUser Account account, @PathVariable String path) {
        Study study = studyService.getStudyToUpdate(account, path);
        studyService.disableStudyBanner(study);
        return "redirect:/study/" + study.getEncodedPath() + "/settings/banner";
    }

}
