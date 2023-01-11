package com.example.alishevTask.Repository;

import com.example.alishevTask.Model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    List<Measurement> findAllByRaining(boolean x);
}
