package edu.eskisehir.teklifyap.mapper;

import edu.eskisehir.teklifyap.domain.dto.WorksiteDto;
import edu.eskisehir.teklifyap.domain.model.Worksite;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorksiteMapper {
    WorksiteDto toWorksiteDto(Worksite worksite);

    Worksite toWorksite(WorksiteDto worksiteDto);

    List<WorksiteDto> toWorksiteDtoList(List<Worksite> all);
}
