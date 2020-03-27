## 

# 1. 线程

## 1.1

## 1.2

## 1.3

## 1.4

## 1.5

------



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

### 2.7.1 为什么要线程隔离？		

​		如果我们很多业务都依赖于同一个线程池,当其中一个业务因为各种不可控的原因消耗了所有的线程，导致线程池全部占满。这样其他的业务也就不能正常运转了，这对系统的打击是巨大的。

​		比如我们 Tomcat 接受请求的线程池，假设其中一些响应特别慢，线程资源得不到回收释放；线程池慢慢被占满，最坏的情况就是整个应用都不能提供服务。

​		所以我们需要将线程池进行隔离。通常的做法是按照业务进行划分：

​		比如下单的任务用一个线程池，获取数据的任务用另一个线程池。这样即使其中一个出现问题把线程池耗尽，那也不会影响其他的任务运行。

​		比如我们现在有3个业务调用分别是查询订单、查询商品、查询用户，且这三个业务请求都是依赖第三方服务-订单服务、商品服务、用户服务。三个服务均是通过RPC调用。当查询订单服务，假如线程阻塞了，这个时候后续有大量的查询订单请求过来，那么容器中的线程数量则会持续增加直致CPU资源耗尽到100%，整个服务对外不可用，集群环境下就是雪崩

### 2.7.2 Hystrix线程隔离

​		Hystrix 是一款开源的容错插件，具有依赖隔离、系统容错降级等功能。Hystrix是Netflix开源的一款容错框架，包含常用的容错方法：线程隔离、信号量隔离、降级策略、熔断技术。在高并发访问下，系统所依赖的服务的稳定性对系统的影响非常大，依赖有很多不可控的因素，比如网络连接变慢，资源突然繁忙，暂时不可用，服务脱机等。我们要构建稳定、可靠的分布式系统，就必须要有这样一套容错方法。

1. Hystrix是如何通过线程池实现线程隔离的

   ​		Hystrix通过命令模式，将每个类型的业务请求封装成对应的命令请求，比如查询订单->订单Command，查询商品->商品Command，查询用户->用户Command。每个类型的Command对应一个线程池。创建好的线程池是被放入到ConcurrentHashMap中。比如查询订单，当第二次查询订单请求过来的时候，则可以直接从Map中获取该线程池。执行Command的方式一共四种

   - execute()

     以同步堵塞方式执行run()。调用execute()后，hystrix先创建一个新线程运行run()，接着调用程序要在execute()调用处一直堵塞着，直到run()运行完成。

   - queue()

     以异步非堵塞方式执行run()。调用queue()就直接返回一个Future对象，同时hystrix创建一个新线程运行run()，调用程序通过Future.get()拿到run()的返回结果，而Future.get()是堵塞执行的。

   - observe()

     事件注册前执行run()/construct()。第一步是事件注册前，先调用observe()自动触发执行run()/construct()（如果继承的是HystrixCommand，hystrix将创建新线程非堵塞执行run()；如果继承的是HystrixObservableCommand，将以调用程序线程堵塞执行construct()），第二步是从observe()返回后调用程序调用subscribe()完成事件注册，如果run()/construct()执行成功则触发onNext()和onCompleted()，如果执行异常则触发onError()。

   - toObservable()

   ### 2.7.4 

   ​		执行依赖代码的线程与请求线程(比如Tomcat线程)分离，请求线程可以自由控制离开的时间，这也是我们通常说的异步编程，Hystrix是结合RxJava来实现的异步编程。通过设置线程池大小来控制并发访问量，当线程饱和的时候可以拒绝服务，防止依赖问题扩散。

   ​		线程隔离的优点

   1. 应用程序会被完全保护起来，即使依赖的一个服务的线程池满了，也不会影响到应用程序的其他部分。
   2. 我们给应用程序引入一个新的风险较低的客户端lib的时候，如果发生问题，也是在本lib中，并不会影响到其他内容，因此我们可以大胆的引入新lib库。

   线程隔离的缺点:

   1. 线程池的主要缺点就是它增加了计算的开销，每个业务请求（被包装成命令）在执行的时候，会涉及到请求排队，调度和上下文切换。不过Netflix公司内部认为线程隔离开销足够小，不会产生重大的成本或性能的影响。
   2. 对于不依赖网络访问的服务，比如只依赖内存缓存这种情况下，就不适合用线程池隔离技术，而是采用信号量隔离

   

# 3 锁

## 3.1

## 3.1 synchronized

​		synchronized是一种独占式的重量级锁，在运行到同步方法或者同步代码块的时候，让程序的运行级别由用户态切换到内核态，把所有的线程挂起，通过操作系统的指令，去调度线程。这样会频繁出现程序运行状态的切换，线程的挂起和唤醒，会消耗系统资源，为了提高效率，引入了偏向锁、轻量级锁、尽量让多线程访问公共资源的时候，不进行程序运行状态的切换。

synchronized作用：

1. 确保线程互斥的访问同步代码 
2. 保证共享变量的修改能够及时可见
3.  有效解决重排序问题

Java中每一个对象都可以作为锁，这是synchronized实现同步的基础：

1. 普通同步方法，锁是当前实例对象
2. 静态同步方法，锁是当前类的class对象
3. 同步方法块，锁是括号里面的对象

synchronized作为锁特点：

1. 不可中断

### 3.1.1 几个概念

1. 自旋

   互斥同步对性能最大的影响是阻塞的实现，挂起线程和恢复线程的操作都需要转入内核态中完成，这些操作给系统的并发性能带来了很大的压力。同时，虚拟机的开发团队也注意到在许多应用上，共享数据的锁定状态只会持续很短的一段时间，为了这段时间去挂起和恢复线程并不值得。如果物理机器有一个以上的处理器，能让两个或以上的线程同时并行执行，我们就可以让后面请求锁的那个线程“稍等一会”，但不放弃处理器的执行时间，看看持有锁的线程是否很快就会释放锁。为了让线程等待，我们只须让线程执行一个忙循环（自旋），这项技术就是所谓的自旋锁。

   - 自旋锁在JDK 1.4.2中就已经引入，只不过默认是关闭的，可以使用-XX:+UseSpinning参数来开启，在JDK 1.6中就已经改为默认开启了。自旋等待不能代替阻塞，且先不说对处理器数量的要求，自旋等待本身虽然避免了线程切换的开销，但它是要占用处理器时间的， 所以如果锁被占用的时间很短，自旋等待的效果就会非常好，反之如果锁被占用的时间很长，那么自旋的线程只会白白消耗处理器资源，而不会做任何有用的工作， 反而会带来性能的浪费。因此自旋等待的时间必须要有一定的限度，如果自旋超过了限定的次数仍然没有成功获得锁，就应当使用传统的方式去挂起线程了。自旋次数的默认值是10次，用户可以使用参数-XX:PreBlockSpin来更改。
   - 在JDK 1.6中引入了自适应的自旋锁。自适应意味着自旋的时间不再固定了，而是由前一次在同一个锁上的自旋时间及锁的拥有者的状态来决定。如果在同一个锁对象 上，自旋等待刚刚成功获得过锁，并且持有锁的线程正在运行中，那么虚拟机就会认为这次自旋也很有可能再次成功，进而它将允许自旋等待持续相对更长的时间， 比如100个循环。另一方面，如果对于某个锁，自旋很少成功获得过，那在以后要获取这个锁时将可能省略掉自旋过程，以避免浪费处理器资源。有了自适应自 旋，随着程序运行和性能监控信息的不断完善，虚拟机对程序锁的状况预测就会越来越准确，虚拟机就会变得越来越“聪明”了。

2. 锁削除

3. 锁粗化

   ​		我们在编写代码的时候，总是推荐将同步块的作用范围限制得尽量小——只在共享数据的实际作用域中才进行同步，这样是为了使得需要同步的操作数量尽可能变小，如果存在锁竞争，那等待锁的线程也能尽快地拿到锁。

   ​		大部分情况下，上面的原则都是正确的，但是如果一系列的连续操作都对同一个对象反复加锁和解锁，甚至加锁操作是出现在循环体中的，那即使没有线程竞争，频繁地进行互斥同步操作也会导致不必要的性能损耗。如果虚拟机探测到有这样一串零碎的操作都对同一个对象加锁，将会把加锁同步的范围扩展（锁粗化）到整个操作序列的外部。

### 3.1.1 实现原理

​		synchronized是在jvm中实现，是基于进入和退出Monitor对象来实现方法和代码块的同步

1. 同步代码块

   ​		monitorenter指令插入到同步代码块的开始位置，monitorexit指令插入到同步代码块的结束位置，JVM需要保证每一个monitorenter都有一个monitorexit与之相对应。任何对象都有一个monitor与之相关联，当且一个monitor被持有之后，他将处于锁定状态。线程执行到monitorenter指令时，将会尝试获取对象所对应的monitor所有权，即尝试获取对象的锁；

2. 同步方法

   ​		synchronized方法则会被翻译成普通的方法调用和返回指令如：invokevirtual、areturn指令，有一个ACC_SYNCHRONIZED标志，JVM就是通过该标志来判断是否需要实现同步的，具体过程为：当线程执行该方法时，会先检查该方法是否标志了ACC_SYNCHRONIZED，如果标志了，线程需要先获取monitor，获取成功后才能调用方法，方法执行完后再释放monitor，在该线程调用方法期间，其他线程无法获取同一个monitor对象。其实本质上和synchronized块相同，只是同步方法是用一种隐式的方式来实现，而不是显式地通过字节码指令。

### 3.1.1 锁状态

​		锁一共有四种状态（由低到高的次序）：无锁状态、偏向锁状态、轻量级锁状态、重量级锁状态。锁的等级只可以升级，不可以降级。这种锁升级却不能降级的策略，目的是为了提高获得锁和释放锁的效率。

1. 偏向锁

   ​		大多数情况下，锁不仅不存在多线程竞争，而且总是由同一个线程多次获得。为了让线程获得所得代价更低而引入了偏向锁，当一个线程访问同步代码块并获取锁时，会在线程的栈帧里创建lockRecord，在lockRecord里和锁对象的MarkWord里存储线程的线程id。

   ​		以后该线程在进入和退出同步块时不需要进行CAS操作来加锁和解锁，只需简单地测试一下对象头的Mark Word里是否存储着指向当前线程的偏向锁。

   ​		如果测试成功，表示线程已经获得了锁。如果测试失败，说明是其他线程访问这个对象，因为偏向锁不会主动释放，所以第二个线程可以看到对象时偏向状态，这时表明在这个对象上已经存在竞争了，检查原来持有该对象锁的线程是否依然存活。

   ​		如果挂了，则可以将对象变为无锁状态，然后重新偏向新的线程，如果原来的线程依然存活，则检查该对象的使用情况，如果仍然需要持有偏向锁，则偏向锁升级为轻量级锁（偏向锁就是这个时候升级为轻量级锁的）。如果不存在使用了，则可以将对象回复成无锁状态，然后重新偏向。

2. 轻量级锁

   线程在执行同步块之前，JVM会先在当前线程的栈桢中创建用于存储锁记录的空间，并将对象头中的Mark Word复制到锁记录中，然后线程尝试使用CAS将对象头中的Mark Word替换为指向锁记录的指针。如果成功，当前线程获得锁，如果失败，表示其他线程竞争锁，当前线程便尝试使用自旋来获取锁。如果，完成自旋策略还是发现线程没有释放锁，或者让别的线程占用，则线程试图将轻量级锁升级为重量级锁。

   轻量级锁认为竞争存在，但是竞争的程度很轻，一般两个线程对于同一个锁的操作都会错开，或者说稍微等待一下（自旋），另一个线程就会释放锁。 但是当自旋超过一定的次数，或者一个线程在持有锁，一个在自旋，又有第三个来访时，轻量级锁膨胀为重量级锁，重量级锁使除了拥有锁的线程以外的线程都阻塞，防止CPU空转。

3. 重量级锁

   就是让争抢锁的线程从用户态转换成内核态。让cpu借助操作系统进行线程协调。

   具体流程如下：

   | 锁       | 优点                                                         | 缺点                                           | 适用场景                                                     |
   | -------- | ------------------------------------------------------------ | ---------------------------------------------- | ------------------------------------------------------------ |
   | 偏向锁   | 加锁和解锁不需要额外的消耗，和执行非同步方法相比仅存在纳秒级的差距 | 如果线程间存在锁竞争，会带来额外的锁撤销的消耗 | 适用于只有一个线程访问同步块场景（只有一个线程进入临界区）   |
   | 轻量级锁 | 竞争的线程不会阻塞，提高了程序的响应速度                     | 如果始终得不到索竞争的线程，使用自旋会消耗CPU  | 追求响应速度，同步块执行速度非常快（多个线程交替进入临界区） |
   | 重量级锁 | 线程竞争不使用自旋，不会消耗CPU                              | 线程阻塞，响应时间缓慢                         | 追求吞吐量，同步块执行速度较慢（多个线程同时进入临界区）     |

   





## 3.1 Lock

# 4 同步工具

## 4.1 CountDownLatch

## 4.2 CyclicBarrier

## 4.3 Semaphore

# 5 原子变量