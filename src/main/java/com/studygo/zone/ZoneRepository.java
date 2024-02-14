package com.studygo.zone;

import com.studygo.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Zone findByCityAndLocalName(String zone, String localName);

}
