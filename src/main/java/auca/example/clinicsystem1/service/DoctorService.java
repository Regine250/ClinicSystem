package auca.example.clinicsystem1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.example.clinicsystem1.model.domain.Doctor;
import auca.example.clinicsystem1.model.domain.Office;
import auca.example.clinicsystem1.model.domain.Specialization;
import auca.example.clinicsystem1.repository.DoctorRepository;
import auca.example.clinicsystem1.repository.OfficeRepository;
import auca.example.clinicsystem1.repository.SpecializationRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private OfficeRepository officeRepo;

    @Autowired
    private SpecializationRepository specializationRepo;

    public String saveDoctor(Doctor doctor) {
        if (doctor.getOffice() != null && doctor.getOffice().getId() != null) {
            Office office = officeRepo.findById(doctor.getOffice().getId()).orElse(null);
            if (office == null) {
                return "Office not found";
            }
            doctor.setOffice(office);
        }

        if (doctor.getSpecializations() != null && !doctor.getSpecializations().isEmpty()) {
            List<Specialization> managedSpecializations = new ArrayList<>();
            for (Specialization spec : doctor.getSpecializations()) {
                if (spec.getId() != null) {
                    Specialization existing = specializationRepo.findById(spec.getId()).orElse(null);
                    if (existing != null) {
                        managedSpecializations.add(existing);
                    }
                }
            }
            doctor.setSpecializations(managedSpecializations);
        }

        doctorRepo.save(doctor);
        return "Doctor saved successfully";
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor getDoctorById(UUID id) {
        return doctorRepo.findById(id).orElse(null);
    }

    public String updateDoctor(UUID id, Doctor newData) {
        Doctor doctor = doctorRepo.findById(id).orElse(null);
        if (doctor == null) {
            return "Doctor not found";
        }

        doctor.setFirstName(newData.getFirstName());
        doctor.setLastName(newData.getLastName());
        doctor.setDateOfBirth(newData.getDateOfBirth());

        if (newData.getOffice() != null && newData.getOffice().getId() != null) {
            Office office = officeRepo.findById(newData.getOffice().getId()).orElse(null);
            if (office == null) {
                return "Office not found";
            }
            doctor.setOffice(office);
        } else if (newData.getOffice() == null) {
            doctor.setOffice(null);
        }

        if (newData.getSpecializations() != null) {
            List<Specialization> managedSpecializations = new ArrayList<>();
            for (Specialization spec : newData.getSpecializations()) {
                if (spec.getId() != null) {
                    Specialization existing = specializationRepo.findById(spec.getId()).orElse(null);
                    if (existing != null) {
                        managedSpecializations.add(existing);
                    }
                }
            }
            doctor.setSpecializations(managedSpecializations);
        }

        doctorRepo.save(doctor);
        return "Doctor updated successfully";
    }

    public String deleteDoctor(UUID id) {
        if (!doctorRepo.existsById(id)) {
            return "Doctor not found";
        }
        doctorRepo.deleteById(id);
        return "Doctor deleted successfully";
    }
}
