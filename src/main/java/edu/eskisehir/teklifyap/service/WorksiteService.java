package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.CreateWorksiteDto;
import edu.eskisehir.teklifyap.domain.dto.DetailedWorksiteDto;
import edu.eskisehir.teklifyap.domain.dto.UpdateWorksiteDto;
import edu.eskisehir.teklifyap.domain.dto.WorksiteDto;
import edu.eskisehir.teklifyap.domain.model.*;
import edu.eskisehir.teklifyap.mapper.EmployeeMapper;
import edu.eskisehir.teklifyap.mapper.OfferMapper;
import edu.eskisehir.teklifyap.mapper.WorksiteMapper;
import edu.eskisehir.teklifyap.repository.WorksiteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorksiteService {

    private final WorksiteMapper worksiteMapper;
    private final WorksiteRepository worksiteRepository;
    private final OfferMapper offerMapper;
    private final EmployeeService employeeService;
    private final OfferService offerService;
    private final EmployeeMapper employeeMapper;


    public WorksiteService(WorksiteMapper worksiteMapper, WorksiteRepository worksiteRepository, OfferMapper offerMapper, EmployeeService employeeService, OfferService offerService, EmployeeMapper employeeMapper) {
        this.worksiteMapper = worksiteMapper;
        this.worksiteRepository = worksiteRepository;
        this.offerMapper = offerMapper;
        this.employeeService = employeeService;
        this.offerService = offerService;
        this.employeeMapper = employeeMapper;
    }

    public void createWorksite(CreateWorksiteDto createWorksiteDto, User user) throws Exception {
        Offer temp_offer = offerService.findById(createWorksiteDto.getOfferId());
        if (!temp_offer.isStatus()) {
            throw new Exception("Offer has not accepted!");
        }
        Worksite worksite = new Worksite();
        worksite.setName(createWorksiteDto.getName());
        worksite.setAddress(createWorksiteDto.getAddress());
        worksite.setUserName(user.getName() + " " + user.getSurname());
        worksite.setOffer(temp_offer);
        worksite.setUser(user);
        worksite.setDate(LocalDateTime.now());
        worksite.setLocationX(createWorksiteDto.getLocationX());
        worksite.setLocationY(createWorksiteDto.getLocationY());
        temp_offer.setWorksite(worksite);
        worksiteRepository.save(worksite);
        offerService.updateOffer(temp_offer.getId(), offerMapper.toUpdateOfferDto(temp_offer));
    }

    public List<WorksiteDto> getWorksites(User user) {
        List<Worksite> worksites = worksiteRepository.findAllByUser(user);
        if (worksites.isEmpty()) {
            return new ArrayList<>();
        }
        return worksiteMapper.toWorksiteDtoList(worksiteRepository.findAllByUser(user));
    }

    public void deleteWorksite(Long id) throws Exception {
        Worksite worksite = worksiteRepository.findById(id).orElseThrow(() -> new Exception("Worksite not found!"));
        offerService.findById(worksite.getOffer().getId()).setWorksite(null);
        offerService.updateOffer(worksite.getOffer().getId(), offerMapper.toUpdateOfferDto(worksite.getOffer()));
        worksiteRepository.deleteById(id);
    }

    public void updateWorksite(Long id, UpdateWorksiteDto updateWorksite) {
        worksiteRepository.findById(id).ifPresent(worksite1 -> {
            if (updateWorksite.getName() != null) {
                worksite1.setName(updateWorksite.getName());
            }
            if (updateWorksite.getAddress() != null) {
                worksite1.setAddress(updateWorksite.getAddress());
            }
            if (updateWorksite.getLocationX() != null) {
                worksite1.setLocationX(updateWorksite.getLocationX());
            }
            if (updateWorksite.getLocationY() != null) {
                worksite1.setLocationY(updateWorksite.getLocationY());
            }
            worksiteRepository.save(worksite1);
        });
    }

    public DetailedWorksiteDto getWorksite(Long id) throws Exception {
        return worksiteMapper.toDetailedWorksiteDto(worksiteRepository.findById(id).orElseThrow(() -> new Exception("Worksite not found!")));
    }

    public void addEmployee(Long id, Long employeeId, User user) throws Exception {
        Worksite worksite = worksiteRepository.findById(id).orElseThrow(() -> new Exception("Worksite not found!"));
        if (!worksite.getUser().getId().equals(user.getId()))
            throw new Exception("You are not owner of this worksite!");

        Employee employee = employeeMapper.toEmployee(employeeService.getEmployee(employeeId));
        if (employee.isDeleted())
            throw new Exception("Employee is deleted!");

        for (WorksiteEmployee employee1 : worksite.getWorksiteEmployees()) {
            if (employee1.getEmployee().getId().equals(employee.getId()))
                throw new Exception("Employee is already added!");
        }

        WorksiteEmployee worksiteEmployee = new WorksiteEmployee();
        worksiteEmployee.setEmployee(employee);
        worksiteEmployee.setWorksite(worksite);

        worksite.getWorksiteEmployees().add(worksiteEmployee);
        worksiteRepository.save(worksite);
    }

    public void removeEmployee(Long id, Long employeeId, User user) throws Exception {
        Worksite worksite = worksiteRepository.findById(id).orElseThrow(() -> new Exception("Worksite not found!"));
        if (!worksite.getUser().getId().equals(user.getId())) {
            throw new Exception("You are not owner of this worksite!");
        }

        worksiteRepository.deleteEmployeeFromWorksite(id, employeeId);
    }
}
