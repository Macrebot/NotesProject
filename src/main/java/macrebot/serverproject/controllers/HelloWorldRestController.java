package macrebot.serverproject.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloWorldRestController {

    @GetMapping({ "/hello", "/hola" })
    public String helloWorld() {
        System.out.println("Solicitud ejecutada");
        return "Hello World";
    }

}
