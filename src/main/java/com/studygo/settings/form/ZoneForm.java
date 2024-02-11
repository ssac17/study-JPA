package com.studygo.settings.form;

import com.studygo.domain.Zone;
import lombok.Data;

@Data
public class ZoneForm {

    private String zoneName;


    public String getLocalName() {
        return zoneName.substring(0, zoneName.indexOf("("));
    }

    public String getCityName() {
        return zoneName.substring(zoneName.indexOf("(") + 1, zoneName.indexOf(")"));
    }


    public Zone getZone() {
        return Zone.builder()
                .city(this.getCityName())
                .localName(this.getLocalName())
                .build();
    }

}
