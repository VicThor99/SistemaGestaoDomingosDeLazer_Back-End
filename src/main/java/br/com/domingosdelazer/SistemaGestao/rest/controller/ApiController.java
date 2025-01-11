package br.com.domingosdelazer.SistemaGestao.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@CrossOrigin(origins = "https://www.domingodelazer.click", allowedHeaders = "*", allowCredentials = "true", methods = {RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ApiController {

    @RequestMapping(value = "/*", method = RequestMethod.OPTIONS)
    public ResponseEntity<Object> handleOptionsRequest() {
        return ResponseEntity.noContent().build();
    }

}
