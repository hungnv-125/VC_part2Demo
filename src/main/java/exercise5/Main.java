package exercise5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import static spark.Spark.*;

/* Chinh sua
tren server github */
public class Main {

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

	private static LoadingCache<Integer, List<Integer>> cache;
	static {
		cache = CacheBuilder.newBuilder().maximumSize(10) // set size
				.expireAfterWrite(20, TimeUnit.SECONDS) // set time expire
				.expireAfterAccess(10, TimeUnit.SECONDS).build(new CacheLoader<Integer, List<Integer>>() {
					@Override
					public List<Integer> load(Integer n) throws Exception {
						return getListPrime(n);
					}
				});
	}

	public static List<Integer> getListPrime(int n) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 2; i <= n; i++) {
			if (isPrime(i))
				list.add(i);
		}

		return list;
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		port(8080);
		get("/prime", (req, res) -> {
			int tmp = Integer.parseInt(req.queryParams("n"));
			return cache.get(tmp).toString();
		});

	}
	/* Chinh sua tren server may client
	*/

}
