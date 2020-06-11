package isamrs.operacije.zakazivanje;

import isamrs.domain.*;
import isamrs.exceptions.NotFoundException;
import isamrs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class DateFinder {

    Collection<Lekar> lekari;
    Collection<Sala> sale;

    private Sala sala;
    private Termin termin;

    @Autowired
    PosetaService posetaService;

    @Autowired
    AdministratorKlinikeService administratorKlinikeService;

    @Autowired
    LekarService lekarService;

    @Autowired
    TerminService terminService;

    @Autowired
    PregledService pregledService;

    @Autowired
    OperacijaService operacijaService;

    @Autowired
    SalaService salaService;

    @Autowired
    KlinikaServiceImpl klinikaService;


    @Autowired
    ApplicationEventPublisher eventPublisher;

    private Integer id;

    public Collection<Lekar> getLekari() {
        return lekari;
    }

    public void setLekari(Collection<Lekar> lekari) {
        this.lekari = lekari;
    }

    public Collection<Sala> getSale() {
        return sale;
    }

    public void setSale(Collection<Sala> sale) {
        this.sale = sale;
    }

    public void calculateTermin(Termin t) throws Exception {
        List<List<Termin>> termini;
        Duration d = t.getDuration();
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        for(int week = 0; week<4;week++){
            termini = new LinkedList<List<Termin>>();
            for(int day = 0; day < 7; day++){
                termini.add(new LinkedList<>());
                for(Lekar l: lekari){
                    l = lekarService.findOne(l.getId());
                    List<RadnoVreme> radnoVremes = new LinkedList<>(l.getRadnoVreme());
                    if (radnoVremes.isEmpty()){
                        continue;
                    }
                    RadnoVreme radnoVreme = radnoVremes.get(radnoVremes.size()-1);
                    LocalTime rstart = radnoVreme.getPocetak().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                    LocalTime rend = radnoVreme.getKraj().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

                    if(termini.get(day).isEmpty()){
                        LocalDateTime start, end;
                        LocalDateTime[] res = createFromRadnoVreme(now,day, rstart, rend, d);
                        if (res==null){
                            break;
                        }
                        start = res[0];
                        end = res[1];
                        termini.get(day).add(new Termin(Date.from(start.atZone(ZoneId.systemDefault()).toInstant()), Date.from(end.atZone(ZoneId.systemDefault()).toInstant())));
                    }else{
                        if(!adjustTermin(termini, day, rstart, rend, now)){
                            break;
                        }
                    }
                    Collection<Poseta> posete = posetaService.findByLekar(l);
                    for(Poseta poseta : posete){
                        Termin tposete = poseta.getTermin();
                        LocalDateTime pstart = tposete.getPocetak().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        LocalDateTime pend = tposete.getKraj().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                        LinkedList<Termin> terminiPoslePosete = new LinkedList<Termin>();
                        for(Termin tt: termini.get(day)){

                            LocalDateTime tstart = tt.getPocetak().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                            LocalDateTime tend = tt.getKraj().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                            if(tstart.isAfter(pend) || tend.isBefore(pstart)){
                                terminiPoslePosete.add(tt);
                                continue;
                            }
                            if(pstart.isAfter(tstart)&&pstart.isBefore(tend)){
                                terminiPoslePosete.add(new Termin(tstart, pstart));
                            }
                            if(pend.isBefore(tend)&&pend.isAfter(tstart)){
                                terminiPoslePosete.add(new Termin(pend, tend));
                            }
                        }
                        termini.set(day, terminiPoslePosete);
                    }
                    for(Termin termin: termini.get(day)){
                        if(termin.getDuration().compareTo(d)>-1){
                            Date start = termin.getPocetak();
                            Date end = Date.from((start.toInstant().atZone(ZoneId.systemDefault()).plus(d)).toInstant());
                            Termin konacni = new Termin(start, end);
                            calculateSala(konacni);
                            return;
                        }
                    }
                }
                now = now.plusDays(1);
            }
        }
        throw new Exception();
    }

    public boolean calculateSala(Termin t)  throws Exception{
        for (Sala s: sale){
            if(administratorKlinikeService.proveriTerminSala(s, t)){
                this.sala = s;
                this.termin = t;
                return true;
            }
        }
        return false;
    }

    private boolean adjustTermin(List<List<Termin>> termini, int day, LocalTime rstart, LocalTime rend, LocalDateTime now) {
        Termin curr = termini.get(day).get(0);

        LocalTime tstart = curr.getPocetak().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime tend = curr.getKraj().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        if(rstart.isAfter(tend) || rend.isBefore(tstart)){
            termini.get(day).remove(0);
            return false;
        }
        if(rstart.isAfter(tstart)){
            LocalDateTime newStart = LocalDateTime.of(now.toLocalDate(),rstart );
            curr.setPocetak(Date.from(newStart.atZone(ZoneId.systemDefault()).toInstant()));
        }
        if(rend.isBefore(tend)){
            LocalDateTime newEnd = LocalDateTime.of(now.toLocalDate(),rend );
            curr.setKraj(Date.from(newEnd.atZone(ZoneId.systemDefault()).toInstant()));
        }
        return true;
    }

    LocalDateTime[] createFromRadnoVreme(LocalDateTime now, int day, LocalTime rstart, LocalTime rend, Duration duration){
        LocalTime nowLT = LocalTime.of(now.getHour(), now.getMinute());
        LocalDateTime start;
        LocalDateTime end;
        if(day == 0 && nowLT.isAfter(rstart)){
            if(nowLT.isAfter(rend)){
                return null;
            }else{
                start = now.minusDays(0);
                end = LocalDateTime.of(LocalDate.now(), rend);
            }
        }else{
            start = LocalDateTime.of(LocalDate.now(), rstart);
            end = LocalDateTime.of(LocalDate.now(), rend);
        }
        if (Duration.between(start, end).compareTo(duration)<0){
            return null;
        }
        return new LocalDateTime[]{start, end};
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void resolve(Integer id) {
        Operacija o = null;
        try {
            o = operacijaService.findOne(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return;
        }
        if(o.getSala() == null){
            Klinika k = klinikaService.findByOperacija(id);
            Collection<Lekar> lekari = lekarService.findByKlinika(k);
            Collection<Sala> sale = salaService.findByKlinika(k);

            Termin t = o.getTermin();

            try {

                if(proveriTerminLekara(lekari, t) && calculateSala(t)) {
                    Sala s = getSala();
                    System.out.println("Nasao salu: id:"+s.getId()+" naziv:"+s.getNaziv());
                    o.setSala(s);
                    operacijaService.update(o.getId(), o);

                }
                else{
                    System.out.println("Menjam termin");
                    calculateTermin(t);
                    Termin novit = getTermin();
                    Sala s = getSala();
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
