package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.WorksiteDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.repository.WorksiteRepository;
import org.springframework.stereotype.Service;

@Service
public class WorksiteService {

    WorksiteRepository worksiteRepository;

    public WorksiteService(WorksiteRepository worksiteRepository) {
        this.worksiteRepository = worksiteRepository;
    }

    public void createWorksite(WorksiteDto worksiteDto, User user) {
//        Worksite worksite = Worksite.builder()
//                .name(worksiteDto.getName())
//                .address(worksiteDto.getAddress())
//                .locationX(worksiteDto.getLocationX())
//                .locationY(worksiteDto.getLocationY())
//                .build();
//        worksite=worksiteRepository.save(worksite);
    }

    public String getWorksites(User user) {
        return null;
    }

    public void deleteWorksite(Long id, User user) {
        worksiteRepository.deleteById(id);
    }

    public void updateWorksite(Long worksiteDto, WorksiteDto user) {

    }

    public String getWorksite(Long id) {
        return null;
    }
}
