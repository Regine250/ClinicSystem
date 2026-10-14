package auca.example.clinicsystem1.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.example.clinicsystem1.model.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
}
