package com.studygo.tag;

import com.studygo.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByTitle(String title);
}
