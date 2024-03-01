package dev.bura.jetty.df.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author a.bloshchetsov
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello() {
        return "{\"result\":0,\"message\":\"Hello!\"}";
    }

    @GetMapping(value = "/hello-with-pause-2000", produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloWithPause2000() {
        try {
            log.info("Before sleep");
            Thread.sleep(2000);
            log.info("After sleep");

            return "{\"result\":0,\"message\":\"Hello!\"}";
        } catch (InterruptedException e) {
            log.warn("The request didn't have enough time to be processed and was interrupted");
            return null;
        }
    }

}
