package isamrs.controller;
import isamrs.domain.Recepti;
import isamrs.dto.ReceptiDTO;
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
    public ResponseEntity<ReceptiDTO> setRecept(@RequestBody ReceptiDTO receptiDTO, @PathVariable("id") Integer id){
        Recepti r = receptService.update(id, toEntity(receptiDTO));
        return new ResponseEntity<>(toDTO(r), HttpStatus.OK);
    }

    public Recepti toEntity(ReceptiDTO r){
        return new Recepti(r.getId(), r.isOveren(), r.getLek());
    }
    public ReceptiDTO toDTO(Recepti r){
        return new ReceptiDTO(r);
    }
}
