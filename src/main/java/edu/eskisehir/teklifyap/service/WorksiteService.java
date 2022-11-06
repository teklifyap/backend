package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.WorksiteDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.domain.model.Worksite;
import edu.eskisehir.teklifyap.repository.WorksiteRepository;
import org.springframework.stereotype.Service;

@Service
public class WorksiteService {
    WorksiteRepository worksiteRepository;

    public WorksiteService(WorksiteRepository worksiteRepository) {
        this.worksiteRepository = worksiteRepository;
    }

    public void createWorksite(WorksiteDto worksiteDto, User user) {
        Worksite worksite = Worksite.builder()
                .name(worksiteDto.getName())
                .address(worksiteDto.getAddress())
                .locationX(worksiteDto.getLocationX())
                .locationY(worksiteDto.getLocationY())
                .build();
        worksite=worksiteRepository.save(worksite);
    }

    public String getWorksites(User user) {
        return worksiteRepository.findAll().stream().map(worksite -> WorksiteDto.builder()
                .id(worksite.getId())
                .name(worksite.getName())
                .address(worksite.getAddress())
                .locationX(worksite.getLocationX())
                .locationY(worksite.getLocationY())
                .build()).toList().toString();
    }

    public void deleteWorksite(Long id, User user) {
        worksiteRepository.deleteById(id);
    }

    public void updateWorksite(WorksiteDto worksiteDto, User user) {
        worksiteRepository.findById(worksiteDto.getId()).ifPresent(worksite -> {
            worksite.setName(worksiteDto.getName());
            worksite.setAddress(worksiteDto.getAddress());
            worksite.setLocationX(worksiteDto.getLocationX());
            worksite.setLocationY(worksiteDto.getLocationY());
            worksiteRepository.save(worksite);
        });
    }

    public String getWorksite(Long id, User user) {
        return worksiteRepository.findById(id).map(worksite -> WorksiteDto.builder()
                .id(worksite.getId())
                .name(worksite.getName())
                .address(worksite.getAddress())
                .locationX(worksite.getLocationX())
                .locationY(worksite.getLocationY())
                .build()).toString();
    }
}
