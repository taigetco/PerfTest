package volatiletest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import junit.framework.Assert;

import org.junit.Test;

public class VolatileTest
{
  @Test
  public void testLazyInitializedWithoutLocalVariable(){
    LazyInstance instance = new LazyInstanceWithoutLocalVariable();
    for (int i=0 ; i< 100; i++)
      testFunction(instance);
  }
  
  @Test
  public void testLazyInitializedWithLocalVariable(){
    LazyInstance instance = new LazyInstanceWithLocalVariable();
    for (int i=0 ; i< 100; i++)
      testFunction(instance);
  }
  
  
  private long testFunction(final LazyInstance instance)
  {
    final CountDownLatch startlatch = new CountDownLatch(1001);
    final CountDownLatch endlatch = new CountDownLatch(1001);
    final AtomicLong lapse = new AtomicLong(0);
    for (int i = 0; i < 1000; i++)
    {
      new Thread(new Runnable()
      {
        @Override
        public void run()
        {
          startlatch.countDown();
          long startTime = System.nanoTime();
          Assert.assertNotNull(instance.getObject());
          lapse.getAndAdd(System.nanoTime() - startTime);
          endlatch.countDown();
        }
      }).start();
    }
    startlatch.countDown();
    endlatch.countDown();
    return lapse.get();
  }
}
