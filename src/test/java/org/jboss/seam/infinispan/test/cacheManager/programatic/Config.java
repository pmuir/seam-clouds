package org.jboss.seam.infinispan.test.cacheManager.programatic;

import javax.enterprise.inject.Produces;

import org.infinispan.config.Configuration;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.seam.infinispan.Infinispan;

/**
 * Creates caches programatically.
 * 
 * @author pmuir
 * 
 */
public class Config
{

   /**
    * Associates the "small" cache with the qualifier {@link Small}. The
    * {@link CacheContainer} is created programatically in the method. This
    * isn't to be advised, as the {@link CacheContainer} is a heavy weight
    * object.
    */
   @Produces
   @Infinispan("small")
   @Small
   public CacheContainer getSmallCacheContainer()
   {
      Configuration defaultConfiguration = new Configuration();
      defaultConfiguration.setEvictionMaxEntries(7);
      EmbeddedCacheManager cacheManager = new DefaultCacheManager(defaultConfiguration);
      return cacheManager;
   }

}