package isamrs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import isamrs.domain.Dijagnoza;
import isamrs.domain.Lekar;
import isamrs.domain.Operacija;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.domain.TipPosete;
import isamrs.domain.ZdravstveniKarton;
import isamrs.dto.DijagnozaDTO;
import isamrs.dto.OperacijaDTO;
import isamrs.dto.post.OperacijaPostDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.operacije.zakazivanje.OperacijaRunnable;
import isamrs.repository.DijagnozaRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PacijentRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.TipPoseteRepository;
import isamrs.repository.ZdravstveniKartonRepository;
import isamrs.tasks.ThreadPoolTaskSchedulerConfig;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class OperacijaServiceImpl implements OperacijaService {

	@Autowired
	ThreadPoolTaskSchedulerConfig threadPoolTaskSchedulerConfig;

	@Autowired
	private OperacijaRepository operacijaRepository;

	@Autowired
	private ZdravstveniKartonRepository kartonRepo;
	
	@Autowired
	OperacijaRunnable operacijaRunnable;
	
	@Autowired
	private DijagnozaRepository dijagnozaRepo;

	@Autowired
	private TipPoseteRepository tipRepo;
	
	@Autowired
	private LekarRepository lekarRepo;
	
	@Override
	public Collection<Operacija> findAll() {

		return operacijaRepository.findAll();
	}

	@Override
	public Operacija findOne(Integer id) throws NotFoundException {

		Operacija o = operacijaRepository.findById(id).orElseThrow(NotFoundException::new);
		return o;
	}

	@Override
	public Operacija create(Operacija t) {
		TipPosete tp = tipRepo.findById(t.getTipPosete().getId()).get();
		t.setTipPosete(tp);
		Operacija o = operacijaRepository.save(t);
		ThreadPoolTaskScheduler threadPoolTaskScheduler = threadPoolTaskSchedulerConfig.threadPoolTaskScheduler();
		operacijaRunnable.setId(o.getId());
		LocalDate date = LocalDate.now().plusDays(1);
		LocalDateTime datetime = date.atTime(LocalTime.of(0,0));
		threadPoolTaskScheduler.schedule(operacijaRunnable, Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant()));
		return o;
	}

	@Override
	public Operacija update(Integer id, Operacija t) throws NotFoundException {
		if(!operacijaRepository.existsById(id)){
			throw new NotFoundException();
		}
		return operacijaRepository.save(t);
	}

	@Override
	public void delete(Integer id) {
		operacijaRepository.deleteById(id);
	}

	@Override
	public List<Operacija> findByKartonId(Integer id) {
		return operacijaRepository.findByIdKarton(id);
	}

	@Override
	public Collection<Operacija> findByLekar(Lekar lekar) {
		return operacijaRepository.findByLekar(lekar);
	}

	@Override
	public Iterable<? extends Operacija> findBySala(Sala s) {
		return operacijaRepository.findBySala(s);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Operacija update(Integer id, OperacijaDTO pregled, Integer idLekar) throws NotFoundException {
		Operacija pregledForUpdate = operacijaRepository.findById(id).orElseThrow(NotFoundException::new);
		
		try {
			ZdravstveniKarton zk =pregledForUpdate.getZdravstveniKarton();
			spoljna:for(DijagnozaDTO dto : pregled.getDijagnoze()) {
				for(Dijagnoza d : zk.getDijagnoza()) {
					if(d.getId() == dto.getId())
						continue spoljna;
				}
				Dijagnoza d = dijagnozaRepo.findById(dto.getId()).orElseGet(null);
				
				zk.getDijagnoza().add(d);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException();
		}
		pregledForUpdate.setOpis(pregled.getOpis());
		pregledForUpdate.setOdradjen(true);
		return operacijaRepository.save(pregledForUpdate);
	}
}
