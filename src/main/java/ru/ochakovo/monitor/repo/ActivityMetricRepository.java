package ru.ochakovo.monitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ochakovo.monitor.domain.ActivityMetric;

public interface ActivityMetricRepository extends JpaRepository<ActivityMetric, Long> {}
