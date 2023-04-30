package com.zakella;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {

    record PingPong(String result){};

    @GetMapping("/ping")
    public PingPong getAnswer(){
        return new PingPong("pong 1");

    }
}
