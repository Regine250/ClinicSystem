package auca.example.clinicsystem1.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.example.clinicsystem1.model.domain.Appointment;
import auca.example.clinicsystem1.model.domain.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    // C2: DERIVED (findBy entity)
    List<Prescription> findByAppointment(Appointment appointment);

    // C3: JPQL (path navigation p.appointment.patient)
    @Query("SELECT p FROM Prescription p WHERE p.appointment.patient.ssNumber = :ssNumber ORDER BY p.issuedDate DESC")
    List<Prescription> findByPatientSsNumber(@Param("ssNumber") String ssNumber);

    // C4: DERIVED (existsBy)
    boolean existsByAppointment(Appointment appointment);

    // Bonus 1: JPQL (GROUP BY, COUNT, ORDER BY)
    @Query("SELECT p.medicineName, COUNT(p) FROM Prescription p GROUP BY p.medicineName ORDER BY COUNT(p) DESC")
    List<Object[]> findTopMedicines();
}
