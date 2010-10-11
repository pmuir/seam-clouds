package org.jboss.seam.infinispan;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.infinispan.AdvancedCache;
import org.infinispan.manager.CacheContainer;
import org.jboss.weld.extensions.bean.generic.Generic;
import org.jboss.weld.extensions.bean.generic.GenericConfiguration;

@GenericConfiguration(Infinispan.class)
public class GenericCacheManager<K, V>
{

   @Inject
   private CacheContainer defaultCacheContainer;
   
   @Inject @Generic
   private Instance<CacheContainer> cacheContainer;

   @Inject @Generic
   private Infinispan infinispan;
   
   private CacheContainer getCacheContainer()
   {
      if (cacheContainer.isUnsatisfied())
      {
         return defaultCacheContainer;
      }
      else
      {
         return cacheContainer.get();
      }
   }
   
   @Produces
   public AdvancedCache<K, V> getAdvancedCache()
   {
      String name = infinispan.value();
      AdvancedCache<K, V> cache = getCacheContainer().<K, V>getCache(name).getAdvancedCache();
      return cache;
   }

}
