package isamrs.service;

import isamrs.domain.Adresa;
import isamrs.repository.AdresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdresaServiceImpl implements AdresaService{

    @Autowired
    AdresaRepository adresaRepository;

    @Override
    public Adresa createAdresa(Adresa a) {
        return adresaRepository.save(a);
    }
}
