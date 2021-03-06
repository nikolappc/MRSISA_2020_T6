package isamrs.controller;

import isamrs.domain.AdministratorKlinickogCentra;
import isamrs.dto.AdminKCDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.service.AdministratorKlinickogCentraService;
import isamrs.service.AdresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminKC")
public class AdminKCController {
    @Autowired
    AdministratorKlinickogCentraService administratorKlinickogCentraService;

    @Autowired
    AdresaService adresaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdminKCDTO>> getAdministratoriKC(){
    	//autorizacija
        Collection<AdminKCDTO> col = administratorKlinickogCentraService.findAll().stream().map(AdminKCDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(col, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminKCDTO> getOne(@PathVariable("id") Integer id){
    	//autorizacija
        try{
            AdministratorKlinickogCentra administratorKlinike = administratorKlinickogCentraService.findOne(id);
            return new ResponseEntity<>(new AdminKCDTO(administratorKlinike), HttpStatus.OK);
        }catch (NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminKCDTO> createAdminKC(@RequestBody AdminKCDTO admin){
    	//autorizacija
        AdministratorKlinickogCentra a = administratorKlinickogCentraService.create(new AdministratorKlinickogCentra(admin));
        return new ResponseEntity<>(new AdminKCDTO(a), HttpStatus.OK);
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminKCDTO> updateAdminKC(@PathVariable("id") Integer id,@RequestBody AdminKCDTO admin){
    	//autorizacija
    	AdministratorKlinickogCentra a = administratorKlinickogCentraService.update(id, new AdministratorKlinickogCentra(admin));
        return new ResponseEntity<>(new AdminKCDTO(a), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
    	//autorizacija
        administratorKlinickogCentraService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
