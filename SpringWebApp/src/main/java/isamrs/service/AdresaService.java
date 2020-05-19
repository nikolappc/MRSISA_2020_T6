package isamrs.service;

import isamrs.domain.Adresa;
import org.springframework.stereotype.Service;

@Service
public interface AdresaService {
    public Adresa createAdresa(Adresa a);
}
