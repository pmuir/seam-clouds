package org.jboss.seam.infinispan;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.infinispan.config.Configuration;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.seam.infinispan.InfinispanExtension.ConfigurationHolder;
import org.jboss.seam.infinispan.event.cachemanager.CacheManagerEventBridge;

@ApplicationScoped
public class CacheContainerManager
{

   private final EmbeddedCacheManager cacheContainer;
   
   // Constructor for proxies
   CacheContainerManager() 
   {
      this.cacheContainer = null;
   }
   
   @Inject
   public CacheContainerManager(InfinispanExtension extension, BeanManager beanManager, CacheManagerEventBridge eventBridge)
   {
      this.cacheContainer = new DefaultCacheManager();
      CreationalContext<Configuration> ctx = beanManager.createCreationalContext(null);
      for (ConfigurationHolder configurationHolder : extension.getConfigurations())
      {
         Configuration configuration = configurationHolder.getProducer().produce(ctx);
         cacheContainer.defineConfiguration(configurationHolder.getName(), configuration);
         // Register any listeners
         eventBridge.registerObservers(configurationHolder.getQualifiers(), configurationHolder.getName(), this.cacheContainer);
      }
   }
   
   @Produces
   public EmbeddedCacheManager getCacheContainer()
   {
      return cacheContainer;
   }
   
   @PreDestroy
   void cleanup()
   {
      cacheContainer.stop();
   }
   
}
