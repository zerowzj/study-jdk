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

### 2.2.1 简介

​		Executor框架是一个根据一组执行策略调用，调度，执行和控制的异步任务的框架，目的是提供一种将”任务提交”与”任务如何运行“分离开来的机制。		

​		Executor 框架是 Java5 之后引进的，在 Java 5 之后，通过 Executor 来启动线程比使用 Thread 的 start 方法更好，除了更易管理，效率更好（用线程池实现，节约开销）外，还有关键的一点：有助于避免 this 逃逸问题。

​		Executor 框架不仅包括了线程池的管理，还提供了线程工厂、队列以及拒绝策略等，Executor 框架让并发编程变得更加简单。

### 2.2.2 框架结构（主要有3部分）

1. 任务（Runnable和Callabel）

   执行任务需要实现的 Runnable接口 或 Callable接口。Runnable接口或 Callable接口 实现类都可以被 ThreadPoolExecutor或 ScheduledThreadPoolExecutor执行。

2. 任务执行（Executor）

3. 异步计算的结果（Future）

   Future 接口以及 Future 接口的实现类 FutureTask 类都可以代表异步计算的结果。

   当我们把 Runnable 接口 或 Callable 接口 的实现类提交给 ThreadPoolExecutor 或 ScheduledThreadPoolExecutor 执行。（调用 submit() 方法时会返回一个 FutureTask  对象）

### 2.2.3 框架的使用

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

1. RUNNING（运行状态）
2. SHUTDOWN（关闭状态）
3. STOP（停止状态）
4. TIDYING（整理状态）
5. TERMINATED（已终止状态）

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



# 3 锁

## 3.1 syste

# 4 同步工具

## 4.1 CountDownLatch

## 4.2 CyclicBarrier

## 4.3 Semaphore

# 5 原子变量