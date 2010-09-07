package org.jboss.seam.infinispan;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.jboss.weld.extensions.bean.generic.Generic;
import org.jboss.weld.extensions.bean.generic.GenericProduct;

@Generic(Infinispan.class)
public class GenericCacheManager<K, V>
{

   @Inject
   private CacheContainer defaultCacheContainer;

   @Inject
   private Infinispan infinispan;
   
   @Inject @GenericProduct
   private Instance<CacheContainer> cacheContainer;
   
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
      return getCacheContainer().<K, V>getCache(infinispan.value()).getAdvancedCache();
   }
   
   @Produces
   public Cache<K, V> getCache()
   {
      return getCacheContainer().<K, V>getCache(infinispan.value()).getAdvancedCache();
   }

}
