public class FindNumber {

    public static void main(String[] args) {
        boolean print = true;

        System.out.println("Тестируем один поток...");
        long oneThreadTime = testOneThread(print);

        System.out.println("Тестируем три потока...");
        long threeThreadsTime = testThreeThreads(print);

        System.out.println("");
        System.out.println("Время работы одного потока: " + oneThreadTime + " ms");
        System.out.println("Время работы трех потоков: " + threeThreadsTime + " ms");

    }

    private static long testOneThread(boolean print) {
        long time = System.nanoTime(); //start

        System.out.println("Найдено " + findInRange(1_000_000, 4_000_000, print) + " чисел");

        time = (System.nanoTime() - time) / 1_000_000;
        return time;
    }

    private static long testThreeThreads(boolean print) {
        long time = System.nanoTime(); //start

        class Sum {
            int sum = 0;

            synchronized void add(int a) {
                sum += a;
            }
        }

        Sum cnt = new Sum();

        Thread t1 = new Thread(() -> cnt.add(findInRange(1_000_000, 2_000_000, print)));
        Thread t2 = new Thread(() -> cnt.add(findInRange(2_000_001, 3_000_000, print)));
        Thread t3 = new Thread(() -> cnt.add(findInRange(3_000_001, 4_000_000, print)));
        t1.start();
        t2.start();
        t3.start();

        //wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //complete
        System.out.println("Найдено " + cnt.sum + " чисел");

        time = (System.nanoTime() - time) / 1_000_000;
        return time;
    }

    private static int findInRange(int a, int b, boolean print) {
        int cnt = 0;

        for (int i = a; i <= b; i++) {
            if (i % 11 == 0 && i % 13 == 0 && i % 15 == 0) {
                cnt++;
                if (print)
                    System.out.println(i);
            }
        }

        return cnt;
    }

}
