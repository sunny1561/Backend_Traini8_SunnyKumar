package Traini.App.Traini8.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainiRepo extends JpaRepository<TrainingCenter,Long> {
    TrainingCenter findByContactEmail(String contactEmail);
    TrainingCenter findByContactPhone(String contactPhone);
}

