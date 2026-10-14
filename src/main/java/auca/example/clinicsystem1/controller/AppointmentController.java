package auca.example.clinicsystem1.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.example.clinicsystem1.model.domain.Appointment;
import auca.example.clinicsystem1.model.domain.AppointmentStatus;
import auca.example.clinicsystem1.service.AppointmentService;

@RestController
@RequestMapping({"/api/appointments", "/api/appointment"})
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveAppointment(@RequestBody Appointment appointment) {
        String msg = appointmentServ.saveAppointment(appointment);
        if (msg.equals("Appointment saved successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        } else if (msg.equals("Appointment date cannot be in the past")) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAppointments() {
        return new ResponseEntity<>(appointmentServ.getAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointment(@PathVariable UUID id) {
        Appointment appointment = appointmentServ.getAppointmentById(id);
        if (appointment == null) {
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable UUID id, @RequestBody Appointment appointment) {
        String msg = appointmentServ.updateAppointment(id, appointment);
        if (msg.equals("Appointment updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable UUID id) {
        String msg = appointmentServ.deleteAppointment(id);
        if (msg.equals("Appointment deleted successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else if (msg.equals("That appointment has prescriptions, delete them first")) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam AppointmentStatus status) {
        String msg = appointmentServ.updateStatus(id, status);
        if (msg.equals("Appointment status updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else if (msg.equals("The appointment with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.startsWith("Cannot change status from")) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<?> rescheduleAppointment(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newDate) {
        String msg = appointmentServ.rescheduleAppointment(id, newDate);
        if (msg.equals("Appointment rescheduled successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else if (msg.equals("The appointment with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.endsWith("cannot be rescheduled") || msg.equals("Appointment date cannot be in the past")) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        } else if (msg.equals("Doctor is already booked on that date")) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
}

