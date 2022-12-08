package edu.eskisehir.teklifyap.domain.embeded;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class WorksiteEmployeeId implements Serializable {

        private Long worksiteId;
        private Long employeeId;

}
