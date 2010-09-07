package org.jboss.seam.infinispan.test.cacheManager.external;

import javax.enterprise.inject.Produces;

import org.infinispan.config.Configuration;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.seam.infinispan.Infinispan;

/**
 * Creates a number of caches, based on come external mechanism (here
 * represented by a static). For example, the {@link CacheContainer} could be
 * looked up in JNDI.
 * 
 * @author Pete Muir
 * 
 */
public class Config
{

   private static final CacheContainer CACHE_CONTAINER;

   /**
    * Create the cache managers
    */
   static
   {
      EmbeddedCacheManager cacheManager = new DefaultCacheManager();
      Configuration largeConfiguration = new Configuration();
      largeConfiguration.setEvictionMaxEntries(100);
      cacheManager.defineConfiguration("large", largeConfiguration);

      Configuration quickConfiguration = new Configuration();
      quickConfiguration.setEvictionWakeUpInterval(1);
      cacheManager.defineConfiguration("quick", quickConfiguration);

      CACHE_CONTAINER = cacheManager;
   }

   /**
    * Associate the "large" cache with the qualifier {@link Large}
    */
   @SuppressWarnings("unused")
   @Produces
   @Infinispan("large")
   @Large
   private CacheContainer largeCacheContainer = CACHE_CONTAINER;

   /**
    * Associate the "quick" cache with the qualifier {@link Quick}
    */
   @SuppressWarnings("unused")
   @Produces
   @Infinispan("quick")
   @Quick
   private CacheContainer quickCacheContainer = CACHE_CONTAINER;

}