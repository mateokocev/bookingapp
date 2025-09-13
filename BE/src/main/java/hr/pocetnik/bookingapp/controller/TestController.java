package hr.pocetnik.bookingapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//  Za sljedeći dio važno je razumjeti kako i zašto. Na poveznici "https://spring.io/guides" pod kategorijom "REST APIs" se nalaze 3 uvodna artikla: "https://spring.io/guides/gs/spring-boot", "https://spring.io/guides/gs/rest-service" i "https://spring.io/guides/tutorials/rest"
//  Testni kontroler za prvu postavljenu rutu, tu je da bi naučio sintaksu i kako Spring funkcionira. Prije daljnje implementacije toplo preporučujem video: https://www.youtube.com/watch?v=WDBUlu_lYas&t=9s
@RestController
public class TestController {

    @GetMapping("/test")
    public String testRoute() {
        return "API je online!";
    }
}