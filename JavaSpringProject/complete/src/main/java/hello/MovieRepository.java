package hello;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    public Movie findByTitle(String title);
    public Movie findByDescription(String description);
    public Movie findByDurationInMinutes(int durationInMinutes);
    public Movie findByPosterLink(String posterLink);
}
