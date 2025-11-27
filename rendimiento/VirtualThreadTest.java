import module java.base;

public class VirtualThreadTest {
	public static class Tarea implements Callable<Integer> {
		private final int number;

		public Tarea(int number) {
			this.number = number;
		}

		@Override
		public Integer call() {
			var name = Thread.currentThread().isVirtual() ? "Virtual" : Thread.currentThread().getName();
			System.out.println("Hilo %s - Tarea %d esperando...".formatted(name, number));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Hilo %s - Tarea %d cancelada.".formatted(name, number));
				return -1;
			}
			System.out.println("Hilo %s - Tarea %d terminada.".formatted(name, number));
			return ThreadLocalRandom.current().nextInt(100);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		try {
			List.of(
					"HILOS DE PLATAFORMA:\t" + hilosDePlataforma(), 
					"HILOS VIRTUALES:\t" + hilosVirtuales()
			).forEach(IO::println);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private static String hilosDePlataforma() throws Exception {
		List<Tarea> tasks = new ArrayList<>();
		for (int i = 0; i < 10_000; i++) {
			tasks.add(new Tarea(i));
		}
		long time = System.nanoTime(), sum = 0;

		try (var executor = Executors.newFixedThreadPool(100)) {
			List<Future<Integer>> futures = executor.invokeAll(tasks);
			for (Future<Integer> future : futures) {
				sum += future.get();
			}
			time = System.nanoTime() - time;
			System.out.println("sum = " + sum + "; time = " + time + " ns");
		}
		return "sum = " + sum + "; time = " + time + " ns";
	}

	private static String hilosVirtuales() throws Exception {
		List<Tarea> tasks = new ArrayList<>();
		for (int i = 0; i < 10_000; i++) {
			tasks.add(new Tarea(i));
		}
		long time = System.nanoTime(), sum = 0;

		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			List<Future<Integer>> futures = executor.invokeAll(tasks);
			for (Future<Integer> future : futures) {
				sum += future.get();
			}
			time = System.nanoTime() - time;
			System.out.println("sum = " + sum + "; time = " + time + " ns");
		}
		return "sum = " + sum + "; time = " + time + " ns";
	}

}