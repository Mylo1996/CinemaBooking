package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CinemaController extends CinemaModel /*implements CommandLineRunner*/ {

    /*
    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public RoomRepository roomRepository;
    @Autowired
    public  ReservationRepository reservationRepository;
    @Autowired
    public  ScreeningRepository screeningRepository;


    CinemaModel cinemaModel = new CinemaModel();
    */

    @GetMapping("/")
    public String listMovies(Model model) {
        List<Movie> movies = super.listAllMovies();
        model.addAttribute("movies", movies);

        List<Screening> monday = super.listScreeningsByDay("Monday");
        model.addAttribute("monday", monday);

        List<Screening> tuesday = super.listScreeningsByDay("Tuesday");
        model.addAttribute("tuesday", tuesday);

        List<Screening> wednesday = super.listScreeningsByDay("Wednesday");
        model.addAttribute("wednesday", wednesday);

        List<Screening> thursday = super.listScreeningsByDay("Thursday");
        model.addAttribute("thursday", thursday);

        List<Screening> friday = super.listScreeningsByDay("Friday");
        model.addAttribute("friday", friday);

        List<Screening> saturday = super.listScreeningsByDay("Saturday");
        model.addAttribute("saturday", saturday);

        List<Screening> sunday = super.listScreeningsByDay("Sunday");
        model.addAttribute("sunday", sunday);

        return "index";
    }

    @PostMapping("/")
    public String saveReservationPost(Model model, @ModelAttribute("reservation") Reservation reservation){
        Reservation reservationToUpdate = super.findReservation(reservation.getId()).get();
        reservationToUpdate.setFirstname(reservation.getFirstname());
        reservationToUpdate.setLastname(reservation.getLastname());
        reservationToUpdate.setPhoneNumber(reservation.getPhoneNumber());
        reservationToUpdate.setEmail(reservation.getEmail());
        reservationToUpdate.setMenuNumber(reservation.getMenuNumber());
        reservationToUpdate.setPaymentType(reservation.getPaymentType());
        String screeningId = reservationToUpdate.getScreeningId();
        String reservedSeats = reservationToUpdate.getReservedSeats();

        Screening screening = super.findScreening(screeningId).get();
        List<Ticket> tickets = new ArrayList<>();
        String[] parameters = reservedSeats.split(";");
        for(int i =0; i<parameters.length;i++){
            int number = Integer.valueOf(parameters[i]);
            Ticket ticket = new Ticket(screening.getRoom().getSeatByNumber(number),1);
            screening.getRoom().setSeatByNumber(number);
            tickets.add(ticket);
        }
        reservationToUpdate.setTickets(tickets);

        int cost = 0;
        if(screening.getIs3D()){
            for(Ticket ticket : reservationToUpdate.getTickets()){
                cost += ticket.price3D*ticket.discount;
            }
        }
        else{
            for(Ticket ticket : reservationToUpdate.getTickets()){
                cost += ticket.price*ticket.discount;
            }
        }
        reservationToUpdate.setCost(cost);

        StringBuilder content = new StringBuilder("<div style=\"background: grey; color: white;\">Kedves "+reservationToUpdate.getFirstname()+" "+reservationToUpdate.getLastname()+"!<br> Foglalása a MoziVideoTK oldalon a "+screening.getMovieTitle()+" című filmre sikeresen megtörtént.<br><br> Foglalási azonosítója: <br>" + reservationToUpdate.getId() + "<br><br>Lefoglalt székek: <br>");
        for (String s: parameters) {
            content.append(s).append(", ");
        }
        content.append("<br><br> Ára: ").append(reservationToUpdate.getCost()).append("<br> Telefonszáma: ").append(reservationToUpdate.getPhoneNumber()).append("<br> Fizetés típusa: ").append(reservationToUpdate.getPaymentType()).append("<br> Menük száma: ").append(reservationToUpdate.getMenuNumber()).append("</div>");


        try {
            sendmail(reservation.getEmail(),"MoziVideoTK jegyfoglalás", content.toString());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        super.saveScreening(screening);
        super.saveReservation(reservationToUpdate);
        return "redirect:/";
    }


    private void sendmail(String mailTo, String subject, String content) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mozivideo.tk@gmail.com", "MoziVideoTk2019");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("mozivideo.tk@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
        msg.setSubject(subject);
        msg.setContent(content, "text/html;charset=utf-8");
        msg.setSentDate(new Date());

        /*
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, "text/html;charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        msg.setContent(multipart);
        */

        Transport.send(msg);

        System.out.println("EMAIL SENT!");
    }



    /*
    @GetMapping("/test")
    public String test(Model model) {

        return "test";
    }
    */

    /*
    @PostMapping("/table")
    public String tablenext(Model model, @ModelAttribute("screening") Screening screening){
        System.out.println("MovieId: "+ screening.getMovieId() + " RoomId: "+screening.getRoomId() + " StartTime: "+screening.getStartTime()+"\n");
        return "redirect:/table";
    }
    */
    
    @GetMapping("/screening")
    public String tableResponse(@RequestParam("movieId") String movieId,@RequestParam("screeningId") String screeningId, @RequestParam("roomNumber") int roomNumber, @RequestParam("is3D") boolean is3D, @RequestParam("startTime") String startTime, @RequestParam("startDay") String startDay, Model model){
        //Optional<Movie> tmp = super.findMovie(movieId);
        //Screening screening = new Screening(movieId, roomNumber,super.findRoomByNumber(roomNumber), is3D, startTime, startDay, tmp.get().getDurationInMinutes());
        //System.out.println("MovieId: "+ screening.getMovieId() + " RoomId: "+screening.getRoomId() +" is3D: "+screening.getIs3D()+ " StartDay: "+screening.getStartDay()+" StartTime: "+screening.getStartTime()+" EndTime: "+screening.getEndTime()+"\n");
        Screening screening = super.findScreening(screeningId).get();
        Room room = screening.getRoom();
        model.addAttribute("room", room);

        model.addAttribute("screeningId", screeningId);
        return "seatSelect";
    }


    @GetMapping("/reservation")
    public String seatResponse(@RequestParam("screeningId") String screeningId,@RequestParam("reservedSeats") String urlParameters,  Model model){
        /*Screening screening = super.findScreening(screeningId).get();
        List<Ticket> tickets = new ArrayList<>();
        String[] parameters = urlParameters.split(";");
        for(int i =0; i<parameters.length;i++){
            int number = Integer.valueOf(parameters[i]);
            Ticket ticket = new Ticket(screening.getRoom().getSeatByNumber(number),1);
            screening.getRoom().setSeatByNumber(number);
            tickets.add(ticket);
        }

        reservation.setTickets(tickets);
        */

        Reservation reservation = new Reservation();
        reservation.setScreeningId(screeningId);
        reservation.setReservedSeats(urlParameters);
        super.saveReservation(reservation);
        model.addAttribute("reservation",reservation);
        return "reservationForm";
    }

    @GetMapping("/cancelReservation")
    public String cancelReservation(Model model){
        Reservation reservation = new Reservation();
        model.addAttribute("reservation", reservation);

        return "cancelReservation";
    }

    @PostMapping("/cancelReservation")
    public String deleteReservation(Model model, @ModelAttribute("reservation") Reservation reservation){

        String Id = reservation.getId();
        Reservation r = super.findReservation(reservation.getId()).get();
        Screening s =super.findScreening(r.screeningId).get();

        List<Seat> toRemove = new ArrayList<>();
        for (Ticket ticket: r.getTickets()) {
            int ticketnumber =ticket.getSeat().getNumber();
            s.getRoom().getSeatByNumber(ticketnumber).setFree();

        }



        super.saveScreening(s);

        try {
            sendmail(r.getEmail(),"MoziVideoTK jegylemondás", "Tisztelt Vásárló <br> Sikeresen lemondta a "+Id+" azonosítójú foglalást!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.deleteReservationById(Id);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model){
        return "login";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model){
        List<Reservation> reservations=super.listAllReservations();

        Map<String, Integer> titlesAndPrices =  new HashMap<>();

        for (Reservation r:reservations) {
            Screening s = super.findScreening(r.getScreeningId()).get();

            if(titlesAndPrices.containsKey(s.getMovieTitle())) {
                titlesAndPrices.put(s.getMovieTitle(), titlesAndPrices.get(s.getMovieTitle()) + r.getCost());
            }else{
                titlesAndPrices.put(s.getMovieTitle(),r.getCost());
            }

        }

        List<String> titles = new ArrayList<>(titlesAndPrices.keySet());

        List<Integer> prices = new ArrayList<>();

        for (String t: titles) {
            prices.add(titlesAndPrices.get(t));
        }


        System.out.println(titles);
        System.out.println(prices);

        model.addAttribute("titleList",titles);
        model.addAttribute("priceList",prices);

        Integer[] intArray = new Integer[]{0,0,0,0 };

        List<Screening> screenings = super.listAllScreenings();
        for (Screening sc: screenings) {
            intArray[sc.getRoomNumber()-1]++;
        }

        List<Integer> listA = Arrays.asList(intArray);
        List<String> listB = new ArrayList<>();
        listB.add("1");
        listB.add("2");
        listB.add("3");
        listB.add("4");

        System.out.println(listA);

        System.out.println(listB);

        model.addAttribute("listA",listA);
        model.addAttribute("listB",listB);


        return "admin";
    }

    @GetMapping("/addMovie")
    public String showAddMoviePage(Model model){
        Movie movie = new Movie("", "","",0);
        model.addAttribute("movie", movie);

        return "addMovie";
    }

    @GetMapping("/reservations")
    public String showReservationsPage(Model model){
        List<Reservation> reservations = super.listAllReservations();
        model.addAttribute("reservations", reservations);

        return "reservations";
    }

    @GetMapping("/closeReservation/{id}")
    public String closeReservation(@PathVariable String id, Model model){
        Optional<Reservation> reservationToClose = super.findReservation(id);
        reservationToClose.get().setPaid();
        super.saveReservation(reservationToClose.get());

        return "redirect:/reservations";
    }

    @PostMapping("/addMovie")
    public String saveMovie(Model model, @ModelAttribute("movie") Movie movie){
        super.saveMovie(movie);
        return "redirect:/addMovie";
    }

    @GetMapping("/addRoom")
    public String showAddRoomPage(Model model){
        Room newRoom = new Room(0,0,0);
        model.addAttribute("newRoom", newRoom);

        List<Room> rooms = super.listAllRooms();
        model.addAttribute("rooms", rooms);

        return "addRoom";
    }

    @PostMapping("/addRoom")
    public String saveRoom(Model model, @ModelAttribute("room") Room room){
        super.saveRoom(room);

        return "redirect:/addRoom";
    }

    @GetMapping("/editRoom/{id}")
    public String editRoom(@PathVariable String id, Model model){
        Optional<Room> roomToEdit = super.findRoom(id);
        model.addAttribute("roomToEdit", roomToEdit);

        return "editRoom";
    }

    @GetMapping("/deleteRoom/{id}")
    public String deleteRoom(@PathVariable String id, Model model){
        super.deleteRoomById(id);

        return "redirect:/addRoom";
    }

    @GetMapping("/addScreening")
    public String showAddScreeningPage(Model model){
        Screening newScreening = new Screening();
        model.addAttribute("newScreening", newScreening);

        List<Screening> screenings = super.listAllScreenings();
        model.addAttribute("screenings", screenings);

        //Set<String> titleMap = super.fillTitleSet();
        List<Movie> allMovies = super.listAllMovies();
        Set<String> titleSet = new HashSet<>();
        for (int i = 0; i < allMovies.size(); i++){
            titleSet.add(allMovies.get(i).getTitle());
        }
        model.addAttribute("titleSet", titleSet);  //set of all occurring movie titles

        return "addScreening";
    }

    @PostMapping("/addScreening")
    public String saveScreening(Model model, @ModelAttribute("newScreening") Screening newScreening){

        System.out.println(newScreening);

        setScreeningIdByTitle(newScreening); // sets movieId by given title

        setScreeningDurationByTitle(newScreening);  // sets duration by given title

        setScreeningRoomByRoomNumber(newScreening); // sets room attribute by given roomNumber

        super.saveScreening(newScreening);

        return "redirect:/addScreening";
    }

/*    @GetMapping("/editScreening/{id}")
    public String editScreening(@PathVariable String id, Model model){
        Optional<Screening> screeningToEdit = super.findScreening(id);
        model.addAttribute("screeningToEdit", screeningToEdit);

        return "editScreening";
    }

    @GetMapping("/deleteScreening/{id}")
    public String deleteScreening(@PathVariable String id, Model model){
        super.deleteScreeningById(id);

        return "redirect:/addScreening";
    }*/

    private void setScreeningTitleById(Screening screening){
        screening.setMovieTitle(super.findMovie(screening.getMovieId()).get().getTitle());
    }

    private void setScreeningIdByTitle(Screening screening){
        screening.setMovieId(super.findMovieByTitle(screening.getMovieTitle()).getId());
    }

    private void setScreeningDurationByTitle(Screening screening){
        screening.setDurationInMinutes(super.findMovieByTitle(screening.getMovieTitle()).getDurationInMinutes());
    }

    private void setScreeningRoomByRoomNumber(Screening screening){
        screening.setRoom(super.findRoomByNumber(screening.roomNumber));
    }
}