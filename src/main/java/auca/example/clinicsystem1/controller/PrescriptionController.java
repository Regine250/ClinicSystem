package auca.example.clinicsystem1.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.example.clinicsystem1.model.domain.Prescription;
import auca.example.clinicsystem1.service.PrescriptionService;

@RestController
@RequestMapping({"/api/prescriptions", "/api/prescription"})
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionServ;

    // C1: Write a prescription
    @PostMapping("/save")
    public ResponseEntity<?> savePrescription(
            @RequestParam UUID appointmentId,
            @RequestBody Prescription prescription) {
        String msg = prescriptionServ.savePrescription(appointmentId, prescription);
        if (msg.equals("Prescription saved successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        } else if (msg.equals("The appointment with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.equals("Prescriptions are only for COMPLETED appointments")) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    // C2: Prescriptions of an appointment
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> getPrescriptionsByAppointment(@PathVariable UUID appointmentId) {
        List<Prescription> list = prescriptionServ.getPrescriptionsByAppointmentId(appointmentId);
        if (list == null) {
            return new ResponseEntity<>("The appointment with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // C3: Prescription history of a patient
    @GetMapping("/patient/{ssNumber}")
    public ResponseEntity<?> getPrescriptionHistory(@PathVariable String ssNumber) {
        return new ResponseEntity<>(prescriptionServ.getPrescriptionsByPatientSsNumber(ssNumber), HttpStatus.OK);
    }

    // C4: Update prescription
    @PutMapping({"/update/{id}", "/{id}"})
    public ResponseEntity<?> updatePrescription(@PathVariable UUID id, @RequestBody Prescription prescription) {
        String msg = prescriptionServ.updatePrescription(id, prescription);
        if (msg.equals("Prescription updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else if (msg.equals("The prescription with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    // C4: Delete prescription
    @DeleteMapping({"/delete/{id}", "/{id}"})
    public ResponseEntity<?> deletePrescription(@PathVariable UUID id) {
        String msg = prescriptionServ.deletePrescription(id);
        if (msg.equals("Prescription deleted successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else if (msg.equals("The prescription with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    // Bonus 1: Most prescribed medicines
    @GetMapping("/stats/top-medicines")
    public ResponseEntity<?> getTopMedicines() {
        return new ResponseEntity<>(prescriptionServ.getTopMedicines(), HttpStatus.OK);
    }
}
