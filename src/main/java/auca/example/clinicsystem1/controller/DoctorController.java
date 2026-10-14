package auca.example.clinicsystem1.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.example.clinicsystem1.model.domain.Doctor;
import auca.example.clinicsystem1.service.DoctorService;

@RestController
@RequestMapping({"/api/doctors", "/api/doctor"})
public class DoctorController {

    @Autowired
    private DoctorService doctorServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveDoctor(@RequestBody Doctor doctor) {
        String msg = doctorServ.saveDoctor(doctor);
        if (msg.equals("Doctor saved successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDoctors() {
        return new ResponseEntity<>(doctorServ.getAllDoctors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctor(@PathVariable UUID id) {
        Doctor doctor = doctorServ.getDoctorById(id);
        if (doctor == null) {
            return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable UUID id, @RequestBody Doctor doctor) {
        String msg = doctorServ.updateDoctor(id, doctor);
        if (msg.equals("Doctor updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable UUID id) {
        String msg = doctorServ.deleteDoctor(id);
        if (msg.equals("Doctor deleted successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<?> addSpecializationToDoctor(@PathVariable UUID doctorId, @PathVariable UUID specializationId) {
        String msg = doctorServ.addSpecializationToDoctor(doctorId, specializationId);
        if (msg.equals("Specialization added to the doctor")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else if (msg.equals("The doctor with that id does not exist") || msg.equals("That specialization does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.equals("The doctor already has that specialization")) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<?> removeSpecializationFromDoctor(@PathVariable UUID doctorId, @PathVariable UUID specializationId) {
        String msg = doctorServ.removeSpecializationFromDoctor(doctorId, specializationId);
        if (msg.equals("Specialization removed from the doctor")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else if (msg.equals("The doctor with that id does not exist") || msg.equals("That specialization does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.equals("The doctor does not have that specialization")) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{doctorId}/office")
    public ResponseEntity<?> moveDoctorToOffice(@PathVariable UUID doctorId, @RequestParam int officeNumber) {
        String msg = doctorServ.moveDoctorToOffice(doctorId, officeNumber);
        if (msg.equals("Office changed successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else if (msg.equals("The doctor with that id does not exist") || msg.equals("The office with that office number does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.equals("That office is taken by other doctor")) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
}

