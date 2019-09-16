package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class CinemaModel  implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private  ReservationRepository reservationRepository;
    @Autowired
    private  ScreeningRepository screeningRepository;

    protected List<Movie> listAllMovies(){
        return new ArrayList<>(movieRepository.findAll());
    }

    protected List<Room> listAllRooms() { return new ArrayList<>(roomRepository.findAll());}

    protected List<Screening> listAllScreenings() { return new ArrayList<>(screeningRepository.findAll());}
    protected List<Screening> listScreeningsByDay(String startDay) { return new ArrayList<>(screeningRepository.findByStartDay(startDay));}

    protected List<Reservation> listAllReservations(){ return new ArrayList<>(reservationRepository.findAll()); }

    protected Optional<Room> findRoom(String id) { return roomRepository.findById(id);}

    protected Room findRoomByNumber(int roomNumber){ return roomRepository.findByRoomNumber(roomNumber);}

    protected Optional<Movie> findMovie(String id) { return movieRepository.findById(id);}

    protected Optional<Reservation> findReservation(String id) { return reservationRepository.findById(id);}

    protected Movie findMovieByTitle(String title) { return movieRepository.findByTitle(title);}

    protected Optional<Screening> findScreening(String id) { return  screeningRepository.findById(id); }

    protected void saveMovie(Movie movie){
        movieRepository.save(movie);
    }

    protected void saveReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }

    protected void saveRoom(Room room){
        roomRepository.save(room);
    }

    protected void saveScreening(Screening screening) { screeningRepository.save(screening); }

    protected void deleteRoomById(String id) { roomRepository.deleteById(id); }

    protected void deleteScreeningById(String id) { screeningRepository.deleteById(id); }

    protected void deleteReservationById(String id) { reservationRepository.deleteById(id); }

    @Override
    public void run(String... args) throws Exception {
        roomRepository.deleteAll();

        roomRepository.save(new Room(9,20,1));
        roomRepository.save(new Room(9,20,2));
        roomRepository.save(new Room(9,20,3));
        roomRepository.save(new Room(9,20,4));

        movieRepository.deleteAll();



        // save a couple of movies
        movieRepository.save(new Movie("MARVEL KAPITÁNY", "A Marvel Studios az 1990-es években játszódó filmje, a Marvel Kapitány egy teljesen új kaland a Marvel mozis univerzumának eddig nem ismert periódusából, amely Carol Danverst követi nyomon. Mikor egy két idegen faj közötti galaktikus háború eléri a Földet, Danvers néhány szövetségessel együtt az események forgatagában találja magát, s hamarosan a világegyetem egyik legerősebb hősévé válik.","https://kep.cdn.indexvas.hu/1/0/2686/26867/268676/26867657_0ad523d296a0f2b40576b3b60feef0bc_wm.jpg",93));
        movieRepository.save(new Movie("KÖLCSÖNLAKÁS", "Nem is lehetne két egymástól jobban különböző férj,mint Balázs (Haumann Máté) és  Henrik (Klem Viktor).Balázsnak a család fontos meg az otthona – amit éppen felújítanak. Henriknek viszont a csábítás fontos meg az otthon – amit a haverja éppen felújít, -hogy legyen hova meghívni legújabb hódítását.A két férj és feleségeik: a gyerekre vágyó Anikó (Balla Eszter) meg a férjén minden áron bosszút állni vágyó Linda (Martinovics Dorina), valamint a lakást felújító dizájner (Szabó Simon) és az őt rajongásával üldöző csaj (Kopek Janka) mind arra vágyik, hogy végre egyedül maradhasson a felújítás alatt álló lakásban, és belépjen a nagy Ő.De sajnos túl nagy az átmenő forgalom.Egyetlen, zűrzavaros napon egymásba botlik, akinek nem kéne,lebukik, aki ártatlan, és elcsábul, aki hűséges.Nagy szívű, ám még nagyobbakat füllentő hőseink vágyak, szerelmek, átverések hálójából próbálnak kikecmeregni, amibe egyre jobban belegabalyodnak.Néha, egy-egy pillanatra az őszinteséggel próbálkoznak – és abból lesz a legnagyobb bonyodalom...A miniszter félrelép írójának színdarabjából vérbő, tarka és nagyonmulatságos vígjáték született – kiváló színészek segítségével.","https://www.jatekszin.hu/media/images/kolcson_web_324-2.width-350.jpg",112));
        movieRepository.save(new Movie("Így neveld a sárkányodat 3.","When Hiccup discovers Toothless isn't the only Night Fury, he must seek \"The Hidden World\", a secret Dragon Utopia before a hired tyrant named Grimmel finds it first.","https://m.media-amazon.com/images/M/MV5BMjIwMDIwNjAyOF5BMl5BanBnXkFtZTgwNDE1MDc2NTM@._V1_UX182_CR0,0,182,268_AL_.jpg",104));
        movieRepository.save(new Movie("A Madea Family Funeral","A joyous family reunion becomes a hilarious nightmare as Madea and the crew travel to backwoods Georgia, where they find themselves unexpectedly planning a funeral that might unveil unsavory family secrets.","https://m.media-amazon.com/images/M/MV5BYTExYTc3YTMtMDY2YS00YTFhLTgwODEtNWQ2MmMzOGZhNTU5XkEyXkFqcGdeQXVyNDExODY2MjU@._V1_UX182_CR0,0,182,268_AL_.jpg",109));
        movieRepository.save(new Movie("Gloria Bell","A free-spirited woman in her 50s seeks out love at L.A. dance clubs.","https://m.media-amazon.com/images/M/MV5BMTc5Nzc1OTk3OV5BMl5BanBnXkFtZTgwNDM1NTQ3NjM@._V1_UX182_CR0,0,182,268_AL_.jpg",102));
        movieRepository.save(new Movie("A Lego-kaland 2","It's been five years since everything was awesome and the citizens are facing a huge new threat: Lego Duplo invaders from outer space, wrecking everything faster than they can rebuild.","https://m.media-amazon.com/images/M/MV5BMTkyOTkwNDc1N15BMl5BanBnXkFtZTgwNzkyMzk3NjM@._V1_UX182_CR0,0,182,268_AL_.jpg",107));
        movieRepository.save(new Movie("Alita: A harc angyala","A deactivated female cyborg is revived, but cannot remember anything of her past life and goes on a quest to find out who she is.","https://m.media-amazon.com/images/M/MV5BNzVhMjcxYjYtOTVhOS00MzQ1LWFiNTAtZmY2ZmJjNjIxMjllXkEyXkFqcGdeQXVyNTc5OTMwOTQ@._V1_UX182_CR0,0,182,268_AL_.jpg",122));
        movieRepository.save(new Movie("Shazam!","We all have a superhero inside us, it just takes a bit of magic to bring it out. In Billy Batson's case, by shouting out one word - SHAZAM! - this streetwise fourteen-year-old foster kid can turn into the adult superhero Shazam.","https://m.media-amazon.com/images/M/MV5BMjIyNTkwODY1OF5BMl5BanBnXkFtZTgwMTUyMTA5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg",132));
        movieRepository.save(new Movie("The Best of Enemies","Civil rights activist Ann Atwater faces off against C.P. Ellis, Exalted Cyclops of the Ku Klux Klan, in 1971 Durham, North Carolina over the issue of school integration.","https://m.media-amazon.com/images/M/MV5BMjQ5MjA2NDkyM15BMl5BanBnXkFtZTgwNTIwNjUzNzM@._V1_UX182_CR0,0,182,268_AL_.jpg",100));
        movieRepository.save(new Movie("Kedvencek temetője","Louis Creed, his wife Rachel, and their two children Gage and Ellie move to a rural home where they are welcomed and enlightened about the eerie 'Pet Sematary' located nearby. After the ...","https://m.media-amazon.com/images/M/MV5BMjUyNjg1ODIwMl5BMl5BanBnXkFtZTgwNjMyOTYzNzM@._V1_UX182_CR0,0,182,268_AL_.jpg",101));

        screeningRepository.deleteAll();

        screeningRepository.save(new Screening(movieRepository.findByTitle("MARVEL KAPITÁNY").getId(), "MARVEL KAPITÁNY", 2,roomRepository.findByRoomNumber(2),true,"10:30","Monday",movieRepository.findByTitle("MARVEL KAPITÁNY").getDurationInMinutes()));

        screeningRepository.save(new Screening(movieRepository.findByTitle("MARVEL KAPITÁNY").getId(), "MARVEL KAPITÁNY", 3,roomRepository.findByRoomNumber(3),true,"20:43","Sunday",movieRepository.findByTitle("MARVEL KAPITÁNY").getDurationInMinutes()));
        screeningRepository.save(new Screening(movieRepository.findByTitle("Gloria Bell").getId(), "Gloria Bell",3,roomRepository.findByRoomNumber(3),true,"9:30","Tuesday",movieRepository.findByTitle("Gloria Bell").getDurationInMinutes()));

        screeningRepository.save(new Screening(movieRepository.findByTitle("KÖLCSÖNLAKÁS").getId(), "KÖLCSÖNLAKÁS", 3,roomRepository.findByRoomNumber(3),true,"9:30","Tuesday",movieRepository.findByTitle("KÖLCSÖNLAKÁS").getDurationInMinutes()));

        reservationRepository.deleteAll();

    }
}
