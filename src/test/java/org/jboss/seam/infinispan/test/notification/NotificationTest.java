package org.jboss.seam.infinispan.test.notification;

import static org.jboss.seam.infinispan.Deployments.baseDeployment;
import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.infinispan.AdvancedCache;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests that the simple form of configuration works
 * 
 * @author Pete Muir
 * @see Config
 *
 */
@RunWith(Arquillian.class)
public class NotificationTest
{
   
   @Deployment
   public static Archive<?> deployment()
   {
      return baseDeployment()
         .addPackage(NotificationTest.class.getPackage());
   }
   
   /**
    * Inject a cache configured by the application
    */
   @Inject @Cache1
   private AdvancedCache<String, String> cache1;
   
   /**
    * Inject a cache configured by application
    */
   @Inject @Cache2
   private AdvancedCache<String, String> cache2;
   
   @Inject
   private Cache1Observers observers1;
   
   @Inject
   private Cache2Observers observers2;

   @Test
   public void testSmallCache()
   {
      // Put something into the cache, ensure it is started
      cache1.put("pete", "Edinburgh");
      assertEquals("Edinburgh", cache1.get("pete"));
      assertEquals(1, observers1.getCacheStartedEventCount());
      assertEquals("cache1", observers1.getCacheStartedEvent().getCacheName());
      assertEquals(1, observers1.getCacheEntryCreatedEventCount());
      assertEquals("pete", observers1.getCacheEntryCreatedEvent().getKey());
      
      // Check cache isolation for events 
      cache2.put("mircea", "London");
      assertEquals("London", cache2.get("mircea"));
      assertEquals(1, observers2.getCacheStartedEventCount());
      assertEquals("cache2", observers2.getCacheStartedEvent().getCacheName());
      
      // Remove something
      cache1.remove("pete");
      assertEquals(1, observers1.getCacheEntryRemovedEventCount());
      assertEquals("pete", observers1.getCacheEntryRemovedEvent().getKey());
      assertEquals("Edinburgh", observers1.getCacheEntryRemovedEvent().getValue());
      
      // Manually stop cache1 to check that we are notified :-)
      assertEquals(0, observers1.getCacheStoppedEventCount());
      cache1.stop();
      assertEquals(1, observers1.getCacheStoppedEventCount());
      assertEquals("cache1", observers1.getCacheStoppedEvent().getCacheName());
      
   }

}
