package isamrs.controller;
import isamrs.domain.Recepti;
import isamrs.dto.ReceptiDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.service.ReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recept")
public class ReceptController {

    @Autowired
    ReceptService receptService;

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setRecept(@RequestBody ReceptiDTO receptiDTO, @PathVariable("id") Integer id) throws NotFoundException {
        receptService.overiRecept(id, toEntity(receptiDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Recepti toEntity(ReceptiDTO r){
        return new Recepti(r.getId(), r.isOveren(), r.getLek());
    }
    public ReceptiDTO toDTO(Recepti r){
        return new ReceptiDTO(r);
    }
}
