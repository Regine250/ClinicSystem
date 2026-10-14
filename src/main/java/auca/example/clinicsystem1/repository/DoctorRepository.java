package auca.example.clinicsystem1.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.example.clinicsystem1.model.domain.Office;
import auca.example.clinicsystem1.model.domain.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    boolean existsByOffice(Office office);
}

