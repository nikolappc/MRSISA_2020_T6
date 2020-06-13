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
import isamrs.domain.Tip;
import isamrs.domain.TipPosete;
import isamrs.service.LekarService;
import isamrs.service.PacijentService;
import isamrs.service.TipPoseteService;
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
			        //try { Thread.sleep(150); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
			        try { Thread.sleep(20); } catch (InterruptedException e) { }
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

	}

}
