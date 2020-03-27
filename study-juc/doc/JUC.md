## 

# 1. 线程

## 1.1

## 1.2

# 2 线程池

## 2.1 线程池好处

​		线程池是一种多线程处理形式，处理过程中将任务添加到队列，然后在创建线程后自动启动这些任务。线程池提供了一种限制和管理资源（包括执行一个任务）。 每个线程池还维护一些基本统计信息，例如已完成任务的数量。

- 降低资源消耗：通过重复利用已创建的线程降低线程创建和销毁造成的消耗
- 提高响应速度： 当任务到达时，任务可以不需要等到线程创建就能立即执行
- 提高线程的可管理性：线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。

## 2.2 Executor框架

​		Executor框架是一个根据一组执行策略调用，调度，执行和控制的异步任务的框架，目的是提供一种将”任务提交”与”任务如何运行“分离开来的机制。		

​		Executor 框架是 Java5 之后引进的，在 Java 5 之后，通过 Executor 来启动线程比使用 Thread 的 start 方法更好，除了更易管理，效率更好（用线程池实现，节约开销）外，还有关键的一点：有助于避免 this 逃逸问题。

​		Executor 框架不仅包括了线程池的管理，还提供了线程工厂、队列以及拒绝策略等，Executor 框架让并发编程变得更加简单。

### 2.2.1 框架结构（主要有3部分）

1. 任务（Runnable和Callabel）

   执行任务需要实现的 Runnable接口 或 Callable接口。Runnable接口或 Callable接口 实现类都可以被 ThreadPoolExecutor或 ScheduledThreadPoolExecutor执行。

2. 任务执行（Executor）

3. 异步计算的结果（Future）

   Future 接口以及 Future 接口的实现类 FutureTask 类都可以代表异步计算的结果。

   当我们把 Runnable 接口 或 Callable 接口 的实现类提交给 ThreadPoolExecutor 或 ScheduledThreadPoolExecutor 执行。（调用 submit() 方法时会返回一个 FutureTask  对象）

### 2.2.2 框架的使用

1. 线程首先要创建实现 Runnable 或者 Callable 接口的任务对象
2. 把创建完成的实现 Runnable/Callable接口的 对象直接交给 ExecutorService 执行: ExecutorService.execute（Runnable command））或者也可以把 Runnable 对象或Callable 对象提交给 ExecutorService 执行（ExecutorService.submit（Runnable task）或 ExecutorService.submit（Callable  task））
3. 如果执行 ExecutorService.submit（…），ExecutorService 将返回一个实现Future接口的对象（我们刚刚也提到过了执行 execute()方法和 submit()方法的区别，submit()会返回一个 FutureTask 对象）。由于 FutureTask 实现了 Runnable，我们也可以创建 FutureTask，然后直接交给 ExecutorService 执行。
4. 最后，主线程可以执行 FutureTask.get()方法来等待任务执行完成。主线程也可以执行 FutureTask.cancel（boolean mayInterruptIfRunning）来取消此任务的执行。

### 2.2.3 核心API

1. Executor

   运行任务的简单接口

2. ExecutorService

   扩展了 Executor 接口，支持有返回值的线程，支持管理线程的生命周期。

3. ScheduledExecutorService

   扩展了 ExecutorService 接口，支持定期执行任务。

4. AbstractExecutorService

   ExecutorService 接口的默认实现。

5. ThreadPoolExecutor

   Executor 框架最核心的类，它继承了 AbstractExecutorService 类。

6. ScheduledThreadPoolExecutor

   ScheduledExecutorService 接口的实现，一个可定时调度任务的线程池。

7. Executors

   可以通过调用 Executors 的静态工厂方法来创建线程池并返回一个 ExecutorService 对象。

## 2.3 ThreadPoolExecutor

### 2.3.1 工作流程

1. workerCount < corePoolSize

   创建并启动一个线程来执行新提交的任务

2.  workerCount >= corePoolSize，且线程池内的阻塞队列未满

   将任务添加到该阻塞队列中

3.  workerCount >= corePoolSize && workerCount < maximumPoolSize，且线程池内的阻塞队列已满

   创建并启动一个线程来执行新提交的任务

4. workerCount >= maximumPoolSize，且线程池内的阻塞队列已满

   根据拒绝策略来处理该任务, 默认的处理方式是直接抛异常

### 2.3.1 运行状态

1. RUNNING

   运行状态，指可以接受任务执行队列里的任务

2. SHUTDOWN

   关闭状态，指调用了shutdown()方法，不再接受新任务，但队列里的任务得执行完毕

3. STOP

   停止状态，指调用了 shutdownNow()方法，不再接受新任务，同时抛弃阻塞队列里的所有任务并中断所有正在执行的任务

4. TIDYING

   整理状态，所有任务都执行完毕，在调用shutdow()或showdownNow()中都会尝试更新为这个状态

5. TERMINATED

   终止状态，当执行terminated()后会更新为这个状态

### 2.3.1 构造参数

1. int  corePoolSize

   线程池的核心线程数量。核心线程数线程数定义了最小可以同时运行的线程数量。

2. int  maximumPoolSize

   线程池的最大线程数。当队列中存放的任务达到队列容量的时候，当前可以同时运行的线程数量变为最大线程数。

3. long  keepAliveTime

   当线程数大于核心线程数时，多余的空闲线程存活的最长时间。当线程池中的线程数量大于 corePoolSize 的时候，如果这时没有新的任务提交，核心线程外的线程不会立即销毁，而是会等待，直到等待的时间超过了 keepAliveTime才会被回收销毁；

4. TimeUnit  unit

   keepAliveTime 参数的时间单位单位。

5. BlockingQueue<Runnable>  workQueue

   任务队列，用来储存等待执行任务的队列。当新任务来的时候会先判断当前运行的线程数量是否达到核心线程数，如果达到的话，信任就会被存放在队列中。

6. ThreadFactory  threadFactory

   线程工厂，用来创建线程，一般默认即可

7. RejectedExecutionHandler handler

   拒绝策略，当提交的任务过多而不能及时处理时，我们可以定制策略来处理任务。如果当前同时运行的线程数量达到最大线程数量并且队列也已经被放满了任时，ThreadPoolTaskExecutor 定义一些策略

   - ThreadPoolExecutor.AbortPolicy：抛出 RejectedExecutionException来拒绝新任务的处理
   - ThreadPoolExecutor.CallerRunsPolicy：调用执行自己的线程运行任务，也就是直接在调用execute方法的线程中运行(run)被拒绝的任务，如果执行程序已关闭，则会丢弃该任务。因此这种策略会降低对于新任务提交速度，影响程序的整体性能。另外，这个策略喜欢增加队列容量。如果您的应用程序可以承受此延迟并且你不能任务丢弃任何一个任务请求的话，你可以选择这个策略。
   - ThreadPoolExecutor.DiscardPolicy： 不处理新任务，直接丢弃掉。
   - ThreadPoolExecutor.DiscardOldestPolicy：此策略将丢弃最早的未处理的任务请求。

### 2.3.1 终止线程池

1. shutdown

   不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务。将线程池切换到 SHUTDOWN 状态；并调用 interruptIdleWorkers 方法请求中断所有空闲的 worker。最后调用 tryTerminate 尝试结束线程池。

2. shutdownNow

   立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务。与 shutdown 方法类似，不同的地方在于：设置状态为 STOP；中断所有工作线程，无论是否是空闲的。取出阻塞队列中没有被执行的任务并返回。

3. isShutdown

   调用了 shutdown 或 shutdownNow 方法后，isShutdown 方法就会返回 true

4. isTerminaed

   当所有的任务都已关闭后，才表示线程池关闭成功，这时调用 isTerminaed 方法会返回 true

### 2.3.1 推荐ThreadPoolExecutor构造函数创建线程池

​		在《阿里巴巴 Java 开发手册》“并发处理”这一章节，明确指出线程资源必须通过线程池提供，不允许在应用中自行显示创建线程。

​		为什么呢？

> 使用线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源开销，解决资源不足的问题。如果不使用线程池，有可能会造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。

​		另外《阿里巴巴 Java 开发手册》中强制线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 构造函数的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。

​		Executors 返回线程池对象的弊端如下：

> - FixedThreadPool 和 SingleThreadExecutor：允许请求的队列长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致 OOM。
>- CachedThreadPool 和 ScheduledThreadPool：允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致 OOM。

1. 方式一：通过ThreadPoolExecutor构造函数实现（推荐） 
2. 方式二：通过 Executor 框架的工具类 Executors 来实现 我们可以创建三种类型的 ThreadPoolExecutor：
   - FixedThreadPool
   - SingleThreadExecutor
   - CachedThreadPool

### 2.3.1 几种常见对比

1. Runnable 和 Callable

   ​		Runnable自 Java 1.0 以来一直存在，但Callable仅在 Java 1.5 中引入,目的就是为了来处理Runnable不支持的用例。Runnable 接口不会返回结果或抛出检查异常，但是Callable 接口可以。所以，如果任务不需要返回结果或抛出异常推荐使用 Runnable接口，这样代码看起来会更加简洁。

   ​		工具类 Executors 可以实现 Runnable 对象和 Callable 对象之间的相互转换。Executors.callable(Runnable task)或Executors.callable(Runnable task，Object resule)。

2. execute() 和 submit()

   - execute()：用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功与否；
   - submit()：用于提交需要返回值的任务。线程池会返回一个 Future 类型的对象，通过这个 Future 对象可以判断任务是否执行成功，并且可以通过 Future 的 get()方法来获取返回值，get()方法会阻塞当前线程直到任务完成，而使用 get（long timeout，TimeUnit unit）方法则会阻塞当前线程一段时间后立即返回，这时候有可能任务没有执行完。

3. shutdown() 和 shutdownNow()

   - shutdown()：关闭线程池，线程池的状态变为 `SHUTDOWN`。线程池不再接受新任务了，但是队列里的任务得执行完毕。
   - shutdownNow()：关闭线程池，线程的状态变为 `STOP`。线程池会终止当前正在运行的任务，并停止处理排队的任务并返回正在等待执行的 List。

4. isTerminated() VS isShutdown()

   - isShutDown 当调用 `shutdown()` 方法后返回为 true。
   - isTerminated 当调用 `shutdown()` 方法后，并且所有提交的任务完成后返回为 true

## 2.4 几种常见的线程池详解

### 2.4.1 FixedThreadPool

1. 介绍

   FixedThreadPool 被称为可重用固定线程数的线程池。

   ```java
      /**
        * 创建一个可重用固定数量线程的线程池
        */
       public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
           return new ThreadPoolExecutor(nThreads, nThreads,
                                         0L, TimeUnit.MILLISECONDS,
                                         new LinkedBlockingQueue<Runnable>(),
                                         threadFactory);
       }
       public static ExecutorService newFixedThreadPool(int nThreads) {
           return new ThreadPoolExecutor(nThreads, nThreads,
                   0L, TimeUnit.MILLISECONDS,
                   new LinkedBlockingQueue<Runnable>());
       }
   ```

   从上面源代码可以看出新创建的 FixedThreadPool 的 corePoolSize 和 maximumPoolSize 都被设置为 nThreads，这个 nThreads 参数是我们使用的时候自己传递的。

2. 执行任务过程

   - 如果当前运行的线程数小于 corePoolSize， 如果再来新任务的话，就创建新的线程来执行任务
   - 当前运行的线程数等于 corePoolSize 后， 如果再来新任务的话，会将任务加入 LinkedBlockingQueue
   - 线程池中的线程执行完 手头的任务后，会在循环中反复从 LinkedBlockingQueue 中获取任务来执行

3. 为什么不推荐使用FixedThreadPool？

   FixedThreadPool 使用无界队列 LinkedBlockingQueue（队列的容量为 Intger.MAX_VALUE）作为线程池的工作队列会对线程池带来如下影响：

   - 当线程池中的线程数达到 corePoolSize 后，新任务将在无界队列中等待，因此线程池中的线程数不会超过 corePoolSize
   - 由于使用无界队列时 maximumPoolSize 将是一个无效参数，因为不可能存在任务队列满的情况。所以，通过创建 FixedThreadPool的源码可以看出创建的 FixedThreadPool 的 corePoolSize 和 maximumPoolSize 被设置为同一个值
   - 由于 1 和 2，使用无界队列时 keepAliveTime 将是一个无效参数
   - 运行中的 FixedThreadPool（未执行 shutdown()或 shutdownNow()）不会拒绝任务，在任务比较多的时候会导致 OOM（内存溢出）

### 2.4.2  SingleThreadExecutor

1. 介绍

   SingleThreadExecutor 是只有一个线程的线程池。

   ```java
      /**
        *返回只有一个线程的线程池
        */
       public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
           return new FinalizableDelegatedExecutorService
               (new ThreadPoolExecutor(1, 1,
                                       0L, TimeUnit.MILLISECONDS,
                                       new LinkedBlockingQueue<Runnable>(),
                                       threadFactory));
       }
      public static ExecutorService newSingleThreadExecutor() {
           return new FinalizableDelegatedExecutorService
               (new ThreadPoolExecutor(1, 1,
                                       0L, TimeUnit.MILLISECONDS,
                                       new LinkedBlockingQueue<Runnable>()));
       }
   ```

   从上面源代码可以看出新创建的 `SingleThreadExecutor` 的 `corePoolSize` 和 `maximumPoolSize` 都被设置为 1.其他参数和 `FixedThreadPool` 相同。

2. 执行任务过程

   - 如果当前运行的线程数少于 corePoolSize，则创建一个新的线程执行任务；
   - 当前线程池中有一个运行的线程后，将任务加入 LinkedBlockingQueue
   - 线程执行完当前的任务后，会在循环中反复从LinkedBlockingQueue 中获取任务来执行；

3. 为什么不推荐使用SingleThreadExecutor？

   SingleThreadExecutor 使用无界队列 LinkedBlockingQueue 作为线程池的工作队列（队列的容量为 Intger.MAX_VALUE）。SingleThreadExecutor 使用无界队列作为线程池的工作队列会对线程池带来的影响与 FixedThreadPool 相同。说简单点就是可能会导致 OOM，

### 2.4.3 CachedThreadPool 

1. 介绍

   CachedThreadPool 是一个会根据需要创建新线程的线程池

   ```java
       /**
        * 创建一个线程池，根据需要创建新线程，但会在先前构建的线程可用时重用它。
        */
       public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
           return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                         60L, TimeUnit.SECONDS,
                                         new SynchronousQueue<Runnable>(),
                                         threadFactory);
       }
       public static ExecutorService newCachedThreadPool() {
           return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                         60L, TimeUnit.SECONDS,
                                         new SynchronousQueue<Runnable>());
       }
   ```

   CachedThreadPool 的corePoolSize 被设置为空（0），maximumPoolSize被设置为 Integer.MAX.VALUE，即它是无界的，这也就意味着如果主线程提交任务的速度高于 maximumPool 中线程处理任务的速度时，CachedThreadPool 会不断创建新的线程。极端情况下，这样会导致耗尽 cpu 和内存资源。

2.  执行任务过程

   - 首先执行 SynchronousQueue.offer(Runnable task) 提交任务到任务队列。如果当前 `maximumPool` 中有闲线程正在执行 SynchronousQueue.poll(keepAliveTime,TimeUnit.NANOSECONDS)，那么主线程执行 offer 操作与空闲线程执行的 poll 操作配对成功，主线程把任务交给空闲线程执行，execute()方法执行完成，否则执行下面的步骤 2；
   - 当初始 maximumPool 为空，或者 `maximumPool` 中没有空闲线程时，将没有线程执行 SynchronousQueue.poll(keepAliveTime,TimeUnit.NANOSECONDS)。这种情况下，步骤 1 将失败，此时 CachedThreadPool 会创建新线程执行任务，execute 方法执行完成；

3. 1为什么不推荐使用CachedThreadPool？

   CachedThreadPool允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致 OOM。

## 2.5 ScheduledThreadPoolExecutor 

​		ScheduledThreadPoolExecutor 主要用来在给定的延迟后运行任务，或者定期执行任务。

### 2.5.11123123

## 2.6 线程池大小确定

​		线程池数量的确定一直是困扰着程序员的一个难题，大部分程序员在设定线程池大小的时候就是随心而定。我们并没有考虑过这样大小的配置是否会带来什么问题，我自己就是这大部分程序员中的一个代表。

​		由于笔主对如何确定线程池大小也没有什么实际经验，所以，这部分内容参考了网上很多文章/书籍。

​		首先，可以肯定的一点是线程池大小设置过大或者过小都会有问题。合适的才是最好，貌似在 95 % 的场景下都是合适的。

​		如果阅读过我的上一篇关于线程池的文章的话，你一定知道：

​		如果我们设置的线程池数量太小的话，如果同一时间有大量任务/请求需要处理，可能会导致大量的请求/任务在任务队列中排队等待执行，甚至会出现任务队列满了之后任务/请求无法处理的情况，或者大量任务堆积在任务队列导致 OOM。这样很明显是有问题的！ CPU 根本没有得到充分利用。

​		但是，如果我们设置线程数量太大，大量线程可能会同时在争取 CPU 资源，这样会导致大量的上下文切换，从而增加线程的执行时间，影响了整体执行效率。

> 上下文切换：
>
> 多线程编程中一般线程的个数都大于 CPU 核心的个数，而一个 CPU 核心在任意时刻只能被一个线程使用，为了让这些线程都能得到有效执行，CPU 采取的策略是为每个线程分配时间片并轮转的形式。当一个线程的时间片用完的时候就会重新处于就绪状态让给其他线程使用，这个过程就属于一次上下文切换。概括来说就是：当前任务在执行完 CPU 时间片切换到另一个任务之前会先保存自己的状态，以便下次再切换回这个任务时，可以再加载这个任务的状态。任务从保存到再加载的过程就是一次上下文切换。
>
> 上下文切换通常是计算密集型的。也就是说，它需要相当可观的处理器时间，在每秒几十上百次的切换中，每次切换都需要纳秒量级的时间。所以，上下文切换对系统来说意味着消耗大量的 CPU 时间，事实上，可能是操作系统中时间消耗最大的操作。
>
> Linux 相比与其他操作系统（包括其他类 Unix 系统）有很多的优点，其中有一项就是，其上下文切换和模式切换的时间消耗非常少。

有一个简单并且适用面比较广的公式：

- CPU 密集型任务（N+1）：这种任务消耗的主要是 CPU 资源，可以将线程数设置为 N（CPU 核心数）+1，比 CPU 核心数多出来的一个线程是为了防止线程偶发的缺页中断，或者其它原因导致的任务暂停而带来的影响。一旦任务暂停，CPU 就会处于空闲状态，而在这种情况下多出来的一个线程就可以充分利用 CPU 的空闲时间。
- I/O 密集型任务（2N）：这种任务应用起来，系统会用大部分的时间来处理 I/O 交互，而线程在处理 I/O 的时间段内不会占用 CPU 来处理，这时就可以将 CPU 交出给其它线程使用。因此在 I/O 密集型任务的应用中，我们可以多配置一些线程，具体的计算方法是 2N。

## 2.7 线程池隔离

### 2.7.1 介绍		

​		如果我们很多业务都依赖于同一个线程池,当其中一个业务因为各种不可控的原因消耗了所有的线程，导致线程池全部占满。

​		这样其他的业务也就不能正常运转了，这对系统的打击是巨大的。

​		比如我们 Tomcat 接受请求的线程池，假设其中一些响应特别慢，线程资源得不到回收释放；线程池慢慢被占满，最坏的情况就是整个应用都不能提供服务。

​		所以我们需要将线程池进行隔离。通常的做法是按照业务进行划分：

​		比如下单的任务用一个线程池，获取数据的任务用另一个线程池。这样即使其中一个出现问题把线程池耗尽，那也不会影响其他的任务运行。

### 2.7.2 Hystrix隔离

​	Hystrix 是一款开源的容错插件，具有依赖隔离、系统容错降级等功能。

​	

# 3 锁

## 3.1 syste

# 4 同步工具

## 4.1 CountDownLatch

## 4.2 CyclicBarrier

## 4.3 Semaphore

# 5 原子变量