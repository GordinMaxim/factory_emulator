package gordin.threadpool;

import gordin.BlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/21/13
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThreadPool {
    private BlockingQueue<Runnable> tasks;
    private List<PoolThread> threads = new LinkedList<PoolThread>();
    private boolean isStopped = false;

    public ThreadPool(int threadNum, int maxTasksNum)
    {
        tasks = new BlockingQueue<Runnable>(maxTasksNum);
        for(int i = 0; i < threadNum; i++)
        {
            threads.add(new PoolThread(tasks));
        }
    }

    public void start()
    {
        for(PoolThread thread : threads)
        {
            thread.start();
        }
    }

    public synchronized void execute(Runnable task) throws InterruptedException
    {
        if(this.isStopped)
            throw new IllegalStateException("ThreadPool is stopped");

        this.tasks.put(task);
    }

    public synchronized void stop()
    {
        this.isStopped = true;
        for(PoolThread thread : threads)
        {
            thread.stopThread();
        }
    }
}
