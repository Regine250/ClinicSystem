package auca.example.clinicsystem1.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.example.clinicsystem1.model.domain.Office;
import auca.example.clinicsystem1.repository.OfficeRepository;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepo;

    public String saveOffice(Office office) {
        if (officeRepo.findByOfficeNumber(office.getOfficeNumber()) != null) {
            return "Office already exists";
        }
        officeRepo.save(office);
        return "Office saved successfully";
    }

    public List<Office> getAllOffices() {
        return officeRepo.findAll();
    }

    public Office getOfficeById(UUID id) {
        return officeRepo.findById(id).orElse(null);
    }

    public String updateOffice(UUID id, Office newData) {
        Office office = officeRepo.findById(id).orElse(null);
        if (office == null) {
            return "Office not found";
        }
        office.setName(newData.getName());
        office.setOfficeNumber(newData.getOfficeNumber());
        officeRepo.save(office);
        return "Office updated successfully";
    }

    public String deleteOffice(UUID id) {
        if (!officeRepo.existsById(id)) {
            return "Office not found";
        }
        officeRepo.deleteById(id);
        return "Office deleted successfully";
    }
}
