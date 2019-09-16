package hello;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
    public Room findByRoomNumber(int roomNumber);
}
