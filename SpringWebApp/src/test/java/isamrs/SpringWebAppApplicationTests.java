package isamrs;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import isamrs.domain.Pacijent;
import isamrs.service.LekarService;
import isamrs.service.PacijentService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringWebAppApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private PacijentService pacijentService;


	@Test()
	public void testPessimisticLockingScenario() throws Throwable {
		
		
		Assertions.assertThrows(PessimisticLockingFailureException.class, () -> {
		      
			ExecutorService executor = Executors.newFixedThreadPool(2);
			executor.submit(new Runnable() {
				
				@Override
				public void run() {
			        System.out.println("Startovan Thread 1");
			        lekarService.findOne(1); // izvrsavanje transakcione metode traje oko 200 milisekundi
				}
			});
			Future<?> future2 = executor.submit(new Runnable() {
				
				@Override
				public void run() {
			        System.out.println("Startovan Thread 2");
			        try { Thread.sleep(100); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
			        /*
			         * Drugi thread pokusava da izvrsi transakcionu metodu findOneById dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
			         * Metoda je oznacena sa NO_WAIT, sto znaci da drugi thread nece cekati da prvi thread zavrsi sa izvrsavanjem metode vec ce odmah dobiti PessimisticLockingFailureException uz poruke u logu:
			         * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : SQL Error: 0, SQLState: 55P03
			         * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : ERROR: could not obtain lock on row in relation "product"
			         * Prema Postgres dokumentaciji https://www.postgresql.org/docs/9.3/errcodes-appendix.html, kod 55P03 oznacava lock_not_available
			         */
			        lekarService.findOne(1);
				}
			});
			try {
			    future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
			} catch (ExecutionException e) {
			    System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
			    throw e.getCause();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			executor.shutdown();
		
		
		});
	}
	
	
	
	/*@Before
	public void setUp() throws Exception {
		pacijentService.save(new Pacijent("A", "AA", "aaa", "111", "1", "a@gmail.com", 1));
		pacijentService.save(new Pacijent("B", "BB", "bbb", "222", "2", "b@gmail.com", 2));
		pacijentService.save(new Pacijent("C", "CC", "ccc", "333", "3", "c@gmail.com", 3));
		pacijentService.save(new Pacijent("D", "DD", "ddd", "444", "4", "d@gmail.com", 4));
		pacijentService.save(new Pacijent("E", "EE", "eee", "555", "5", "e@gmail.com", 5));
	}
	
	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void testOptimisticLockingScenario() throws Throwable {	

		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<?> future1 = executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 1");
				Pacijent productToUpdate = pacijentService.findOne(1);// ocitan objekat sa id 1
				productToUpdate.setEmail("znj@gmail.com");// izmenjen ucitan objekat
				try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
				pacijentService.save(productToUpdate);// bacice ObjectOptimisticLockingFailureException
				
			}
		});
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");
				Pacijent productToUpdate = pacijentService.findOne(1);// ocitan isti objekat sa id 1 kao i iz prvog threada
				productToUpdate.setEmail("sklj@gmail.com");// izmenjen ucitan objekat
				pacijentService.save(productToUpdate);
			}
		});
		try {
		    future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
		} catch (ExecutionException e) {
		    System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
		    throw e.getCause();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();

	}*/

}
