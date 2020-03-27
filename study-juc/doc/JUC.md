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

1. corePoolSize
2. maximumPoolSize
3. keepAliveTime
4. unit
5. workQueue
6. threadFactory
7. handler

### 2.3.1 终止线程池

1. shutdown

   不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务。将线程池切换到 SHUTDOWN 状态；并调用 interruptIdleWorkers 方法请求中断所有空闲的 worker。最后调用 tryTerminate 尝试结束线程池。

2. shutdownNow

   立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务。与 shutdown 方法类似，不同的地方在于：设置状态为 STOP；中断所有工作线程，无论是否是空闲的。取出阻塞队列中没有被执行的任务并返回。

3. isShutdown

   调用了 shutdown 或 shutdownNow 方法后，isShutdown 方法就会返回 true

4. isTerminaed

   当所有的任务都已关闭后，才表示线程池关闭成功，这时调用 isTerminaed 方法会返回 true





# 3 锁

## 3.1

# 4 同步工具

## 4.1 CountDownLatch

## 4.2 CyclicBarrier

## 4.3 Semaphore

# 5 原子变量