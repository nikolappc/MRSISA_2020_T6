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
public class PregledRunnable implements Runnable {





    Integer id;

    @Autowired
    DateFinderPregled dateFinder;


    public PregledRunnable() {
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
        dateFinder.resolve(this.id);
    }


}
