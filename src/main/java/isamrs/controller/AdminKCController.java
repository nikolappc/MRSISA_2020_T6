package isamrs.controller;

import isamrs.domain.AdministratorKlinickogCentra;
import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Adresa;
import isamrs.exceptions.NotFoundException;
import isamrs.service.AdministratorKlinickogCentraService;
import isamrs.service.AdministratorKlinikeService;
import isamrs.service.AdresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/adminKC")
public class AdminKCController {
    @Autowired
    AdministratorKlinickogCentraService administratorKlinickogCentraService;

    @Autowired
    AdresaService adresaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdministratorKlinickogCentra>> getAdministratoriKC(){
        Collection<AdministratorKlinickogCentra> col = administratorKlinickogCentraService.findAll();
        return new ResponseEntity<>(col, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorKlinickogCentra> getOne(@PathVariable("id") Integer id){
        try{
            AdministratorKlinickogCentra administratorKlinike = administratorKlinickogCentraService.findOne(id);
            return new ResponseEntity<>(administratorKlinike, HttpStatus.OK);
        }catch (NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorKlinickogCentra> createAdminKC(@RequestBody AdministratorKlinickogCentra admin){
        AdministratorKlinickogCentra a = administratorKlinickogCentraService.create(admin);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorKlinickogCentra> updateAdminKC(@PathVariable("id") Integer id,@RequestBody AdministratorKlinickogCentra admin){
        AdministratorKlinickogCentra a = administratorKlinickogCentraService.update(id, admin);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        administratorKlinickogCentraService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
