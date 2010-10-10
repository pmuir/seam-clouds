package org.jboss.seam.infinispan;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Inject;

import org.infinispan.AdvancedCache;
import org.infinispan.config.Configuration;
import org.infinispan.manager.CacheContainer;
import org.jboss.weld.extensions.bean.generic.Generic;
import org.jboss.weld.extensions.bean.generic.GenericConfiguration;

@GenericConfiguration(Infinispan.class)
public class GenericCacheManager<K, V>
{

   @Inject
   private CacheContainer defaultCacheContainer;

   @Inject @Generic
   private Infinispan infinispan;
   
   @Inject @Generic
   private Instance<CacheContainer> cacheContainer;
   
   @Inject
   private InfinispanExtension extension;
   
   @Inject
   private BeanManager beanManager;
   
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
      if (extension.getListeners().containsKey(name))
      {
         CreationalContext<Object> ctx = beanManager.createCreationalContext(null);
         for (InjectionTarget<Object> it : extension.getListeners().get(name))
         {
            Object listener = it.produce(ctx);
            it.inject(listener, ctx);
            it.postConstruct(listener);
            // TODO Add support for cleaning up listeners (depends on WELD-572)
         }
      }
      return cache;
   }

}
