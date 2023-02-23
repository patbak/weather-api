package pl.patbak.weather_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patbak.weather_api.model.dao.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}
