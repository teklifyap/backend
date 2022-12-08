package edu.eskisehir.teklifyap.mapper;

import edu.eskisehir.teklifyap.domain.dto.DetailedWorksiteDto;
import edu.eskisehir.teklifyap.domain.dto.EmployeeDto;
import edu.eskisehir.teklifyap.domain.dto.WorksiteDto;
import edu.eskisehir.teklifyap.domain.model.Worksite;
import edu.eskisehir.teklifyap.domain.model.WorksiteEmployee;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WorksiteMapper {
    default WorksiteDto toWorksiteDto(Worksite worksite) {
        WorksiteDto worksiteDto = new WorksiteDto();
        worksiteDto.setId(worksite.getId());
        worksiteDto.setName(worksite.getName());
        worksiteDto.setAddress(worksite.getAddress());
        worksiteDto.setUserName(worksite.getUserName());
        System.out.println(worksite.getDate().toString());
        worksiteDto.setDate(worksite.getDate());
        worksiteDto.setLocationX(worksite.getLocationX());
        worksiteDto.setLocationY(worksite.getLocationY());
        worksiteDto.setOfferName(worksite.getOffer().getTitle());
        return worksiteDto;
    }

    default DetailedWorksiteDto toDetailedWorksiteDto(Worksite worksite) {
        DetailedWorksiteDto detailedWorksiteDto = new DetailedWorksiteDto();
        detailedWorksiteDto.setId(worksite.getId());
        detailedWorksiteDto.setName(worksite.getName());
        detailedWorksiteDto.setAddress(worksite.getAddress());
        detailedWorksiteDto.setUserName(worksite.getUserName());
        detailedWorksiteDto.setDate(worksite.getDate());
        detailedWorksiteDto.setLocationX(worksite.getLocationX());
        detailedWorksiteDto.setLocationY(worksite.getLocationY());
        detailedWorksiteDto.setOfferName(worksite.getOffer().getTitle());
        detailedWorksiteDto.setEmployees(new ArrayList<>());
        for (WorksiteEmployee employee : worksite.getWorksiteEmployees()) {
            detailedWorksiteDto.getEmployees().add(new EmployeeDto(
                    employee.getEmployee().getId(),
                    employee.getEmployee().getName(),
                    employee.getEmployee().getSurname(),
                    employee.getEmployee().getSalary()
            ));
        }
        return detailedWorksiteDto;
    }

    Worksite toWorksite(WorksiteDto worksiteDto);

    default List<WorksiteDto> toWorksiteDtoList(List<Worksite> all){
        ArrayList<WorksiteDto> result = new ArrayList<>();
        for (Worksite worksite : all) {
            result.add(toWorksiteDto(worksite));
        }
        return result;
    }
}
