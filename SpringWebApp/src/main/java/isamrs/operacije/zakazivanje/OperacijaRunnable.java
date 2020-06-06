package isamrs.operacije.zakazivanje;

import isamrs.domain.*;
import isamrs.exceptions.NotFoundException;
import isamrs.registracija.OnRegistrationFailEvent;
import isamrs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Component
public class OperacijaRunnable implements Runnable {

    @Autowired
    OperacijaService operacijaService;

    @Autowired
    SalaService salaService;

    @Autowired
    KlinikaServiceImpl klinikaService;

    @Autowired
    LekarService lekarService;

    @Autowired
    TerminService terminService;

    @Autowired
    PregledService pregledService;

    @Autowired
    PacijentService pacijentService;

    Integer id;

    @Autowired
    DateFinder dateFinder;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    public OperacijaRunnable() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    @Transactional
    public void run() {
        Operacija o = null;
        try {
            o = operacijaService.findOne(this.id);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return;
        }
        if(o.getSala() == null){
            Klinika k = klinikaService.findByOperacija(this.id);
            Collection<Lekar> lekari = lekarService.findByKlinika(k);
            Collection<Sala> sale = salaService.findAll();

            Termin t = o.getTermin();

            dateFinder.setLekari(lekari);
            dateFinder.setSale(sale);
            dateFinder.setOperacijaRunnable(this);
            try {

                if(proveriTerminLekara(lekari, t) && dateFinder.calculateSala(t)) {
                    Sala s = dateFinder.getSala();
                    System.out.println("Nasao salu: id:"+s.getId()+" naziv:"+s.getNaziv());
                    o.setSala(s);
                    operacijaService.update(o.getId(), o);

                }
                else{
                    System.out.println("Menjam termin");
                    dateFinder.calculateTermin(t);
                    Termin novit = dateFinder.getTermin();
                    Sala s = dateFinder.getSala();
                    novit = terminService.create(novit);
                    o.setTermin(novit);
                    o.setSala(s);
                    o = operacijaService.update(o.getId(), o);
                    //Event
                    eventPublisher.publishEvent(new OnTerminChangedEvent(o.getZdravstveniKarton().getPacijent(), o.getTermin(),o));

                }
            }catch (Exception | NotFoundException e){
                e.printStackTrace();

                System.out.println("Greska nije promenjen termin");
            }
        }
    }

    private boolean proveriTerminLekara(Collection<Lekar> lekari, Termin t) {
        for(Lekar l:lekari){
            if(!proveriTerminLekara(l,t)){
                return false;
            }
        }
        return true;
    }


    private boolean proveriTerminLekara(Lekar lekar, Termin termin) {

        Collection<Pregled> pregledi = pregledService.findByLekar(lekar);
        for(Pregled p: pregledi) {
            //Ako je termin posle kraja nekog pregleda to je ok
            if(p.getSala() == null)
                continue;

            if(p.getTermin().getKraj().before(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if(p.getTermin().getPocetak().after(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }
        Collection<Operacija> operacije = operacijaService.findByLekar(lekar);

        for(Operacija p: operacije ) {
            if(p.getId().equals(this.id)){
                continue;
            }
            if(p.getSala() == null)
                continue;

            //Ako je termin posle kraja nekog pregleda to je ok
            if(p.getTermin().getKraj().before(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if(p.getTermin().getPocetak().after(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }


        return true;
    }

}
