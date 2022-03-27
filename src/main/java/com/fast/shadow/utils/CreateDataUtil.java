package com.fast.shadow.utils;

import com.fast.shadow.model.User;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-22
 * @Description:com.fast.shadow.utils
 * @Version:1.0
 **/


public class CreateDataUtil {

    public static List<User> userList = new CopyOnWriteArrayList<>();

    //最大线程数控制
    private static int MAX_THREADS = 5;
    //跑批分页大小
    private static int EXPIRED_PAGE_SIZE = 50;

    public static void main(String[] args) throws Exception {
        CreateDataUtil createDataUtil = new CreateDataUtil();
                createDataUtil.test();
//        List<List<User>> partition = Lists.partition(userList, 10);

        createDataUtil.dataHandler(userList);

    }


    public  void dataHandler(List<User> list) {
        //处理数据数量
        int listSize = list.size();

        //线程数
        int runSize;
        if (listSize % EXPIRED_PAGE_SIZE == 0) {
            runSize = (listSize / EXPIRED_PAGE_SIZE);
        } else {
            runSize = (listSize / EXPIRED_PAGE_SIZE) + 1;
        }
        ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(runSize);
        CountDownLatch countDownLatch = new CountDownLatch(runSize);
        final Semaphore semaphore = new Semaphore(MAX_THREADS);

        List handleList = null;
        for (int i = 0; i < runSize; i++) {

            if ((i + 1) == runSize) {
                int startIndex = i * EXPIRED_PAGE_SIZE;
                int endIndex = list.size();
                handleList = list.subList(startIndex, endIndex);
            } else {
                int startIndex = i * EXPIRED_PAGE_SIZE;
                int endIndex = (i + 1) * EXPIRED_PAGE_SIZE;
                handleList = list.subList(startIndex, endIndex);
            }
            System.out.println("分批长度："+handleList.size());
            SyncTask task = new SyncTask(handleList, countDownLatch, semaphore);
            executor.execute(task);
        }


    }


    class SyncTask implements Runnable {
        private List<User> list;
        private CountDownLatch countDownLatch;
        private Semaphore semaphore;

        public SyncTask(List<User> list, CountDownLatch countDownLatch, Semaphore semaphore) {
            this.list = list;
            this.countDownLatch = countDownLatch;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            if (list!=null) {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "处理list长度：" + list.size());
//                    list.stream().forEach(fileDto -> {
//                        //业务处理
//                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }
            //线程任务完成
            countDownLatch.countDown();
        }
    }


    /**
     * 使用jdk8新特性并行处理 数据
     *
     * @param list
     */
    public static void processList(List<Object> list) {
        Random random = new Random(47);
        Map<Integer, List<Object>> group = list.parallelStream().collect(Collectors.groupingBy(e -> random.nextInt(100)));
        group.values().parallelStream().forEach(e -> e.forEach(i ->
                //这里可以根据自己的需要做处理
                System.out.println("使用jdk8新特性打印处理数据" + i)));
    }

    /**
     * 使用多线程处理数据
     *
     * @param list
     * @param nThreads
     * @throws Exception
     */
    public static void processList2(List<Object> list, final int nThreads) throws Exception {
        // 存最后的结果
        StringBuffer ret = new StringBuffer();
        if (list == null || list.isEmpty()) {
            //这里可以根据自己的业务需要去做处理
            System.out.println("要处理的数据为空");
        }
        int size = list.size();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Future<Object>> futures = new ArrayList<Future<Object>>(nThreads);
        //根据线程 进行数据的拆分
        for (int i = 0; i < nThreads; i++) {
            final List<Object> subList = list.subList(size / nThreads * i, size / nThreads * (i + 1));
            Callable<Object> task = new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    //存当前线程处理的数据，最后统一汇总打印
                    StringBuffer sb = new StringBuffer();
                    for (Object str : subList) {
                        sb.append(str + "\t");
                    }
                    return sb.toString();
                }
            };
            futures.add(executorService.submit(task));
        }
        // 汇总处理的结果
        for (Future<Object> future : futures) {
            ret.append(future.get());
        }
        executorService.shutdown();
        System.out.println("并发处理结果: " + ret.toString());
    }

    public void test() {
        //定义线程池大小为8，因为有8个redis的master实例
        ExecutorService pool = Executors.newFixedThreadPool(8);
        //结束的倒数锁,因为线程池有8个线程，所以也定义8个线程结束锁计数
        final CountDownLatch latch = new CountDownLatch(8);
        for (int i = 0; i < 8; i++) {
            final int tmp = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        userList.addAll(getUsers(100000));
                    } finally {
                        //有一个线程进来就减1
                        latch.countDown();
                    }
                }
            };
            pool.submit(runnable);
        }
        try {
            //阻塞，直到latch为0才执行下面的输出语句
            latch.await();
            System.out.println("组装usersize:" + userList.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }


    //方便下面添加用戶測試，num是创造多少个user实体类对象
    public static List<User> getUsers(int num) {
        List<User> tUsers = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            byte a = 1;
            User user = new User();
            user.setAddTime(new Date());
            user.setIsdel(false);
            user.setMoney(new BigDecimal(12.00));
            user.setAddTime(new Date());
            user.setRemark(i + "");
            user.setState(a);
            user.setLeftMoney(1f);
            user.setUsername("测试" + UUID.randomUUID().toString().split("-")[1]);
            tUsers.add(user);
        }
        return tUsers;
    }

    public static User getUser() {
        byte a = 1;
        User user = new User();
        user.setAddTime(new Date());
        user.setIsdel(false);
        user.setMoney(new BigDecimal(14.00));
        user.setAddTime(new Date());
        user.setRemark("添加用户");
        user.setState(a);
        user.setLeftMoney(2f);
        user.setUsername("测试" + UUID.randomUUID().toString().split("-")[1]);
        return user;
    }
}
