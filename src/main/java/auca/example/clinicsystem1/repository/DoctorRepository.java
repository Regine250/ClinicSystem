package auca.example.clinicsystem1.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.example.clinicsystem1.model.domain.Office;
import auca.example.clinicsystem1.model.domain.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    boolean existsByOffice(Office office);

    // D3: JPQL (LIKE, LOWER, OR)
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY d.lastName ASC")
    List<Doctor> searchByName(@Param("name") String name);

    // Bonus 2: JPQL (JOIN, WHERE, BETWEEN, GROUP BY)
    @Query("SELECT d.firstName, d.lastName, COUNT(a) FROM Appointment a JOIN a.doctor d WHERE a.status = auca.example.clinicsystem1.model.domain.AppointmentStatus.COMPLETED AND a.appointmentDate BETWEEN :start AND :end GROUP BY d.id, d.firstName, d.lastName ORDER BY COUNT(a) DESC")
    List<Object[]> findDoctorWorkload(@Param("start") LocalDate start, @Param("end") LocalDate end);
}

