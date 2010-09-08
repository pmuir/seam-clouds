package org.jboss.seam.infinispan.test.notification;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.infinispan.notifications.cachemanagerlistener.event.CacheStartedEvent;
import org.infinispan.notifications.cachemanagerlistener.event.CacheStoppedEvent;

@ApplicationScoped
public class Cache1Observers
{

   private CacheStartedEvent cacheStartedEvent;
   private int cacheStartedEventCount;
   private int cacheStoppedEventCount;
   private CacheStoppedEvent cacheStoppedEvent;

   /**
    * Observe the cache started event for the cache associated with @Cache1
    */
   void observeCacheStarted(@Observes @Cache1 CacheStartedEvent event)
   {
      this.cacheStartedEventCount++;
      this.cacheStartedEvent = event;
   }

   /**
    * Observe the cache stopped event for the cache associated with @Cache1
    */
   void observeCacheStopped(@Observes @Cache1 CacheStoppedEvent event)
   {
      this.cacheStoppedEventCount++;
      this.cacheStoppedEvent = event;
   }

   public CacheStartedEvent getCacheStartedEvent()
   {
      return cacheStartedEvent;
   }

   public int getCacheStartedEventCount()
   {
      return cacheStartedEventCount;
   }

   public CacheStoppedEvent getCacheStoppedEvent()
   {
      return cacheStoppedEvent;
   }

   public int getCacheStoppedEventCount()
   {
      return cacheStoppedEventCount;
   }

}
