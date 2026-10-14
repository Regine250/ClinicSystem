package auca.example.clinicsystem1.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.example.clinicsystem1.model.domain.Specialization;
import auca.example.clinicsystem1.repository.SpecializationRepository;

@Service
public class SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepo;

    public String saveSpecialization(Specialization specialization) {
        specializationRepo.save(specialization);
        return "Specialization saved successfully";
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepo.findAll();
    }

    public Specialization getSpecializationById(UUID id) {
        return specializationRepo.findById(id).orElse(null);
    }

    public String updateSpecialization(UUID id, Specialization newData) {
        Specialization spec = specializationRepo.findById(id).orElse(null);
        if (spec == null) {
            return "Specialization not found";
        }
        spec.setName(newData.getName());
        specializationRepo.save(spec);
        return "Specialization updated successfully";
    }

    public String deleteSpecialization(UUID id) {
        if (!specializationRepo.existsById(id)) {
            return "Specialization not found";
        }
        specializationRepo.deleteById(id);
        return "Specialization deleted successfully";
    }
}
