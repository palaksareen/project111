package uk.co.o2.soaclient;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.o2.utility.NotO2CustomerException;
import uk.co.o2.utility.PUKNotFoundException;
import uk.co.o2.utility.SOAException;

@Service
public class SoaServiceCaller {

	static class SumTask implements Callable<String> {
		String mpn;	   

		@Autowired
		SoaService soaService;

		public SumTask(String mpn){
			this.mpn=mpn;
		}
		@Override
		public String call() throws PUKNotFoundException, NotO2CustomerException, SOAException  {
			return soaService.getPuk(mpn);
		}
	} 

	public static String execute(String mpn) throws  SOAException,PUKNotFoundException, NotO2CustomerException{
		ExecutorService service =  Executors.newSingleThreadExecutor();
		SoaServiceCaller.SumTask sumTask = new SoaServiceCaller.SumTask(mpn);
		Future<String> future = service.submit(sumTask);
		String result=null;
		try {
			System.out.println("\n\nStarted............");
			result = future.get(100000, TimeUnit.MILLISECONDS);
			System.out.println("\n\nFinished............");
		} catch (TimeoutException | InterruptedException | ExecutionException e) {
			throw new SOAException(e.getMessage());
		}
		service.shutdown();
		try {
			service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
		}
		return result;
	}

}
