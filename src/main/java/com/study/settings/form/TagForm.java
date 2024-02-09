package com.study.settings.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Data
public class TagForm {

    private String tagTitle;
}
