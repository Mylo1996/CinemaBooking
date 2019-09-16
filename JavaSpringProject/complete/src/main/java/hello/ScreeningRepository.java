package hello;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScreeningRepository  extends MongoRepository<Screening, String> {
    List<Screening> findByStartDay(String startDay);
}
