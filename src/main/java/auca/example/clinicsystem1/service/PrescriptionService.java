package auca.example.clinicsystem1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.example.clinicsystem1.model.domain.Appointment;
import auca.example.clinicsystem1.model.domain.AppointmentStatus;
import auca.example.clinicsystem1.model.domain.Prescription;
import auca.example.clinicsystem1.repository.AppointmentRepository;
import auca.example.clinicsystem1.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    // C1: Write a prescription
    public String savePrescription(UUID appointmentId, Prescription prescription) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElse(null);
        if (appointment == null) {
            return "The appointment with that id does not exist";
        }
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            return "Prescriptions are only for COMPLETED appointments";
        }
        prescription.setIssuedDate(LocalDate.now());
        prescription.setAppointment(appointment);
        prescriptionRepo.save(prescription);
        return "Prescription saved successfully";
    }

    // C2: Prescriptions of an appointment
    public List<Prescription> getPrescriptionsByAppointmentId(UUID appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElse(null);
        if (appointment == null) {
            return null;
        }
        return prescriptionRepo.findByAppointment(appointment);
    }

    // C3: Prescription history of a patient
    public List<Prescription> getPrescriptionsByPatientSsNumber(String ssNumber) {
        return prescriptionRepo.findByPatientSsNumber(ssNumber);
    }

    // C4: Update prescription
    public String updatePrescription(UUID id, Prescription newData) {
        Prescription prescription = prescriptionRepo.findById(id).orElse(null);
        if (prescription == null) {
            return "The prescription with that id does not exist";
        }
        prescription.setMedicineName(newData.getMedicineName());
        prescription.setDosage(newData.getDosage());
        prescription.setDurationDays(newData.getDurationDays());
        prescriptionRepo.save(prescription);
        return "Prescription updated successfully";
    }

    // C4: Delete prescription
    public String deletePrescription(UUID id) {
        if (!prescriptionRepo.existsById(id)) {
            return "The prescription with that id does not exist";
        }
        prescriptionRepo.deleteById(id);
        return "Prescription deleted successfully";
    }

    // Bonus 1: Most prescribed medicines
    public List<Object[]> getTopMedicines() {
        return prescriptionRepo.findTopMedicines();
    }
}
