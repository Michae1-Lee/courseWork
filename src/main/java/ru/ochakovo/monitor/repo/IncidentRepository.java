package ru.ochakovo.monitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ochakovo.monitor.domain.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Long> {}
