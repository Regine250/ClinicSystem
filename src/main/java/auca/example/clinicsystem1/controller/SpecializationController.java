package auca.example.clinicsystem1.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.example.clinicsystem1.model.domain.Specialization;
import auca.example.clinicsystem1.service.SpecializationService;

@RestController
@RequestMapping("/api/specialization")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveSpecialization(@RequestBody Specialization specialization) {
        String msg = specializationServ.saveSpecialization(specialization);
        if (msg.equals("Specialization saved successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSpecializations() {
        return new ResponseEntity<>(specializationServ.getAllSpecializations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSpecialization(@PathVariable UUID id) {
        Specialization spec = specializationServ.getSpecializationById(id);
        if (spec == null) {
            return new ResponseEntity<>("Specialization not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(spec, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSpecialization(@PathVariable UUID id, @RequestBody Specialization specialization) {
        String msg = specializationServ.updateSpecialization(id, specialization);
        if (msg.equals("Specialization updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSpecialization(@PathVariable UUID id) {
        String msg = specializationServ.deleteSpecialization(id);
        if (msg.equals("Specialization deleted successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }
}
