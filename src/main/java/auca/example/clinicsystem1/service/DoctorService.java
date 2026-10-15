package auca.example.clinicsystem1.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.example.clinicsystem1.model.domain.Appointment;
import auca.example.clinicsystem1.model.domain.AppointmentStatus;
import auca.example.clinicsystem1.model.domain.Doctor;
import auca.example.clinicsystem1.model.domain.Office;
import auca.example.clinicsystem1.model.domain.Specialization;
import auca.example.clinicsystem1.repository.AppointmentRepository;
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

    @Autowired
    private AppointmentRepository appointmentRepo;

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

    public String addSpecializationToDoctor(UUID doctorId, UUID specializationId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
        if (doctor == null) {
            return "The doctor with that id does not exist";
        }
        Specialization spec = specializationRepo.findById(specializationId).orElse(null);
        if (spec == null) {
            return "That specialization does not exist";
        }
        if (doctor.getSpecializations().contains(spec)) {
            return "The doctor already has that specialization";
        }
        doctor.getSpecializations().add(spec);
        doctorRepo.save(doctor);
        return "Specialization added to the doctor";
    }

    public String removeSpecializationFromDoctor(UUID doctorId, UUID specializationId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
        if (doctor == null) {
            return "The doctor with that id does not exist";
        }
        Specialization spec = specializationRepo.findById(specializationId).orElse(null);
        if (spec == null) {
            return "That specialization does not exist";
        }
        if (!doctor.getSpecializations().contains(spec)) {
            return "The doctor does not have that specialization";
        }
        doctor.getSpecializations().remove(spec);
        doctorRepo.save(doctor);
        return "Specialization removed from the doctor";
    }

    public String moveDoctorToOffice(UUID doctorId, int officeNumber) {
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
        if (doctor == null) {
            return "The doctor with that id does not exist";
        }
        Office office = officeRepo.findByOfficeNumber(officeNumber);
        if (office == null) {
            return "The office with that office number does not exist";
        }
        if (doctorRepo.existsByOffice(office)) {
            if (doctor.getOffice() == null || !doctor.getOffice().getId().equals(office.getId())) {
                return "That office is taken by other doctor";
            }
        }
        doctor.setOffice(office);
        doctorRepo.save(doctor);
        return "Office changed successfully";
    }

    // D1: Doctor's agenda for a day (without CANCELLED ones)
    public List<Appointment> getDoctorAgenda(UUID doctorId, LocalDate date) {
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
        if (doctor == null) {
            return null;
        }
        return appointmentRepo.findByDoctorAndAppointmentDateAndStatusNot(doctor, date, AppointmentStatus.CANCELLED);
    }

    // D3: Search doctors by name
    public List<Doctor> searchDoctorsByName(String name) {
        return doctorRepo.searchByName(name);
    }

    // Bonus 2: Doctor workload
    public List<Object[]> getDoctorWorkload(LocalDate start, LocalDate end) {
        return doctorRepo.findDoctorWorkload(start, end);
    }
}

