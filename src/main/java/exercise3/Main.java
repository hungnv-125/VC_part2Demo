package exercise3;

import static spark.Spark.get;
import static spark.Spark.port;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main implements Runnable {
	//public HashMap<Integer, Time> tmpHashMap = new HashMap<>();
	public static ConcurrentHashMap<Integer, List<Integer>> CACHE = new ConcurrentHashMap <Integer, List<Integer>>();
	public static ConcurrentHashMap<Integer, Long> timeRequset = new ConcurrentHashMap<Integer, Long>();
	public static ConcurrentHashMap<Integer, Long> timeLife = new ConcurrentHashMap<Integer, Long>();
	
	public static long startTime;
	public static long startAddCacheTime;

	public void run() {
		Long current = System.currentTimeMillis();


		for (Map.Entry<Integer, Long> map : timeRequset.entrySet()) {
			if (current - map.getValue() > 10000) {
				timeLife.remove(map.getKey());
				CACHE.get(map.getKey()).removeAll(CACHE.get(map.getKey()));
				CACHE.remove(map.getKey());
				timeRequset.remove(map.getKey());
			}
		}

		for (Map.Entry<Integer, Long> map : timeLife.entrySet()) {
			if (current - map.getValue() > 20000) {
				timeRequset.remove(map.getKey());
				CACHE.get(map.getKey()).removeAll(CACHE.get(map.getKey()));
				CACHE.remove(map.getKey());
				timeLife.remove(map.getKey());
			}
		}

	}
	public static boolean isPrime(int n) {
		if (n < 2)
			return false;
		else if (n == 2)
			return true;
		else {
			if (n % 2 == 0)
				return false;
			else {
				int i, sqrtN = (int) Math.sqrt(n);
				for (i = 3; i < sqrtN; i++)
					if (n % i == 0)
						return false;
				return true;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		

		port(8080);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		
		executor.scheduleWithFixedDelay(new Main(), 0, 1, TimeUnit.SECONDS);
		

		get("/prime", (req, res) -> {
			int tmp = Integer.parseInt(req.queryParams("n"));
			
			if(CACHE.containsKey(tmp)) {
				timeRequset.replace(tmp, timeRequset.get(tmp), System.currentTimeMillis());
				return "cache  "+ CACHE.get(tmp).toString();
			}
			else {
				List<Integer> listPrime = new ArrayList<Integer>();
				for (int i = 1; i <= tmp; i++) {
					if (isPrime(i))
						listPrime.add(i);
				}
				timeRequset.put(tmp, System.currentTimeMillis());
				timeLife.put(tmp, System.currentTimeMillis());
				CACHE.put(tmp, listPrime);
				
				return listPrime.toString();
			}
		});


	}

}
