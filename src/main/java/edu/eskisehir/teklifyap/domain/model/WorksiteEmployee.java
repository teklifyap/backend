package edu.eskisehir.teklifyap.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.eskisehir.teklifyap.domain.embeded.WorksiteEmployeeId;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "worksite_employee")
public class WorksiteEmployee {

    @EmbeddedId
    private WorksiteEmployeeId worksiteEmployeeId = new WorksiteEmployeeId();

    @ManyToOne
    @MapsId("worksiteId")
    @JoinColumn(name = "worksite_id")
    @JsonIgnore
    private Worksite worksite;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

}
