package gordin.threadpool;

import gordin.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/22/13
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */
class PoolThread extends Thread {
    private final BlockingQueue<Runnable> tasks;
    private boolean isStopped = false;
    private final Object lock = new Object();

    public PoolThread(BlockingQueue<Runnable> tasks)
    {
        this.tasks = tasks;
    }

    public void run()
    {
        boolean isStopped;
        synchronized (lock)
        {
            isStopped = this.isStopped;
        }

        while(!isStopped)
        {
            try {
                Runnable runnable = tasks.take();
                runnable.run();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            synchronized (lock)
            {
                isStopped = this.isStopped;
            }
        }
    }

    public void pauseThread()
    {
        synchronized (lock)
        {
            this.isStopped = false;
        }
    }

}