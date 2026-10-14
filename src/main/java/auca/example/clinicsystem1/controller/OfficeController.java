package auca.example.clinicsystem1.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.example.clinicsystem1.model.domain.Office;
import auca.example.clinicsystem1.service.OfficeService;

@RestController
@RequestMapping("/api/office")
public class OfficeController {

    @Autowired
    private OfficeService officeServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveOffice(@RequestBody Office office) {
        String msg = officeServ.saveOffice(office);
        if (msg.equals("Office saved successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOffices() {
        return new ResponseEntity<>(officeServ.getAllOffices(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOffice(@PathVariable UUID id) {
        Office office = officeServ.getOfficeById(id);
        if (office == null) {
            return new ResponseEntity<>("Office not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(office, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOffice(@PathVariable UUID id, @RequestBody Office office) {
        String msg = officeServ.updateOffice(id, office);
        if (msg.equals("Office updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOffice(@PathVariable UUID id) {
        String msg = officeServ.deleteOffice(id);
        if (msg.equals("Office deleted successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }
}
