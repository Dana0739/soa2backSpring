package com.dana.soa2back.model.DTO;

import com.dana.soa2back.model.Position;
import com.dana.soa2back.model.Status;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HRHireFireWorkerDTO {
    @XmlElement
    Long organizationId;
    @XmlElement
    Position position; // todo check null
    @XmlElement
    Status status; // todo check null
    @XmlElement
    Date startDate;
    @XmlElement
    Date endDate;
}
