package com.dana.soa2back.model.DTO;

import com.dana.soa2back.model.Position;
import com.dana.soa2back.model.Status;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@XmlRootElement
public class EditWorkerWorkDTO {
    Long organizationId;
    Position position;
    Status status;
    Date startDate;
    Date endDate;
}
