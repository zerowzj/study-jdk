package study.jdk.juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Mutex {

    class Sync extends AbstractQueuedSynchronizer {

        //是否处于占用状态
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        //当状态为0的时候获取锁，CAS操作成功，则state状态为1，
        @Override
        protected boolean tryAcquire(int acquires) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //释放锁，将同步状态置为0
        @Override
        protected boolean tryRelease(int acquires) {
            if (getState() == 0)
                throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            throw new UnsupportedOperationException();
        }
    }

    private Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }
}
