package com.study.settings;

import com.study.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notifications {

    private boolean studyCreatedByEmail;

    private boolean studyCreatedByWeb;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdateByEmail;

    private boolean studyUpdateByWeb;

    public Notifications(Account account) {
        this.studyCreatedByEmail = account.isStudyCreateByEmail();
        this.studyCreatedByWeb = account.isStudyCreateByWeb();
        this.studyEnrollmentResultByEmail = account.isStudyEnrollmentResultByEmail();
        this.studyEnrollmentResultByWeb = account.isStudyEnrollmentResultByWeb();
        this.studyUpdateByEmail = account.isStudyUpdateByEmail();
        this.studyUpdateByWeb = account.isStudyUpdateByWeb();
    }
}
