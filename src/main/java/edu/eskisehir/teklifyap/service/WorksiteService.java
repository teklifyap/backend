package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.CreateWorksiteDto;
import edu.eskisehir.teklifyap.domain.dto.WorksiteDto;
import edu.eskisehir.teklifyap.domain.model.Offer;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.domain.model.Worksite;
import edu.eskisehir.teklifyap.mapper.WorksiteMapper;
import edu.eskisehir.teklifyap.repository.WorksiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorksiteService {

    @Autowired
    private WorksiteMapper worksiteMapper;
    private final WorksiteRepository worksiteRepository;

    private final EmployeeService employeeService;
    private final OfferService offerService;


    public WorksiteService(WorksiteRepository worksiteRepository, EmployeeService employeeService, OfferService offerService) {
        this.worksiteRepository = worksiteRepository;
        this.employeeService = employeeService;
        this.offerService = offerService;
    }

    public void createWorksite(CreateWorksiteDto createWorksiteDto, User user) throws Exception {
        Offer temp_offer = offerService.findById(createWorksiteDto.getOfferId());
        if (!temp_offer.isStatus()) {
            throw new Exception("Offer has not accepted!");
        }
        Worksite worksite = new Worksite();
        worksite.setName(createWorksiteDto.getName());
        worksite.setAddress(createWorksiteDto.getAddress());
        if (user.getUsername() != null) {
            worksite.setUserName(user.getUsername()); // TODO Belki name surname olabilir
        } else {
            worksite.setUserName(user.getName() + " " + user.getSurname());
        }
        worksite.setOffer(temp_offer);
        worksite.setOfferName(temp_offer.getTitle());
        worksite.setUser(user);
        worksite.setDate(LocalDateTime.now());
        worksite.setLocationX(createWorksiteDto.getLocationX());
        worksite.setLocationY(createWorksiteDto.getLocationY());
        temp_offer.setWorksite(worksite);
        worksiteRepository.save(worksite);
        // TODO offer update
    }

    public List<WorksiteDto> getWorksites(User user) {
        return worksiteMapper.toWorksiteDtoList(worksiteRepository.findAllByUser(user));
    }

    public void deleteWorksite(Long id, User user) {
        worksiteRepository.deleteById(id);
    }

    public void updateWorksite(Long worksiteDto, WorksiteDto user) {

    }

    public WorksiteDto getWorksite(Long id) {
        return null;
    }

    public void addEmployee(Long id, Long employeeId, User user) {
        worksiteRepository.findById(id).ifPresent(worksite -> {
            //worksite.getEmployees().add(employeeId);
            worksiteRepository.save(worksite);
        });
    }

    public void removeEmployee(Long id, Long employeeId, User user) {
        worksiteRepository.findById(id).ifPresent(worksite -> {
            //worksite.getEmployees().remove(employeeId);
            worksiteRepository.save(worksite);
        });
    }
}
