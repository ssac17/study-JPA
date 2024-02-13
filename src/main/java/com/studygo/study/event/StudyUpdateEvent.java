package com.studygo.study.event;

import com.studygo.domain.Study;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StudyUpdateEvent {

    private final Study study;

    private final String message;
}
