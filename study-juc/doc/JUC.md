# 1 线程池

## 1.1 线程池好处

​		线程池是一种多线程处理形式，处理过程中将任务添加到队列，然后在创建线程后自动启动这些任务。线程池提供了一种限制和管理资源（包括执行一个任务）。 每个线程池还维护一些基本统计信息，例如已完成任务的数量。

- 降低资源消耗：通过重复利用已创建的线程降低线程创建和销毁造成的消耗
- 提高响应速度： 当任务到达时，任务可以不需要等到线程创建就能立即执行
- 提高线程的可管理性：线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。

## 1.2 Executor框架

### 1.1 简介

​		Executor框架是一个根据一组执行策略调用，调度，执行和控制的异步任务的框架，目的是提供一种将”任务提交”与”任务如何运行”分离开来的机制。		

​		Executor 框架是 Java5 之后引进的，在 Java 5 之后，通过 Executor 来启动线程比使用 Thread 的 start 方法更好，除了更易管理，效率更好（用线程池实现，节约开销）外，还有关键的一点：有助于避免 this 逃逸问题。

​		Executor 框架不仅包括了线程池的管理，还提供了线程工厂、队列以及拒绝策略等，Executor 框架让并发编程变得更加简单。

### 2.2 框架结构（主要有三部分）

1. 任务（Runnable和Callabel）

   执行任务需要实现的 Runnable接口 或 Callable接口。Runnable接口或 Callable接口 实现类都可以被 ThreadPoolExecutor或 ScheduledThreadPoolExecutor执行。

2. 任务执行（Executor）

3. 异步计算的结果（Future）

   Future 接口以及 Future 接口的实现类 FutureTask 类都可以代表异步计算的结果。

   当我们把 Runnable 接口 或 Callable 接口 的实现类提交给 ThreadPoolExecutor 或 ScheduledThreadPoolExecutor 执行。（调用 submit() 方法时会返回一个 FutureTask  对象）