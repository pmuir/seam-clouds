package org.jboss.seam.infinispan.test.cacheManager.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.infinispan.config.Configuration;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.seam.infinispan.Infinispan;
import org.jboss.weld.extensions.resourceLoader.Resource;

@ApplicationScoped
public class Config
{

   /**
    * Associate the "very-large" cache (configured below) with the qualifier
    * {@link VeryLarge}.
    */
   @SuppressWarnings("unused")
   @Produces
   @Infinispan("very-large")
   @VeryLarge
   private final CacheContainer veryLargeCacheContainer;

   /**
    * Associate the "quick-very-large" cache (configured below) with the qualifier
    * {@link Quick}.
    */
   @SuppressWarnings("unused")
   @Produces
   @Infinispan("quick-very-large")
   @Quick
   private CacheContainer quickVeryLargeCacheContainer;

   // Constructor for proxies
   @SuppressWarnings("unused")
   Config()
   {
      this.quickVeryLargeCacheContainer = null;
      this.veryLargeCacheContainer = null;
   }

   /**
    * Create the Cache Container from XML, and associate it with the producer
    * fields.
    * 
    * @param xml The XML stream will be injected, based on the resource name
    *           provided
    * 
    */
   @Inject
   public Config(@Resource("infinispan.xml") InputStream xml) throws IOException
   {
      // Create the cache container from the XML config, and associate with the
      // producer fields
      EmbeddedCacheManager cacheManager = new DefaultCacheManager(xml);
      this.veryLargeCacheContainer = cacheManager;
      this.quickVeryLargeCacheContainer = cacheManager;

      // Define the very-large and quick-very-large configuration, based on the
      // defaults
      cacheManager.defineConfiguration("very-large", cacheManager.getDefaultConfiguration().clone());

      Configuration quickVeryLargeConfiguration = cacheManager.getDefaultConfiguration().clone();
      quickVeryLargeConfiguration.setEvictionWakeUpInterval(1);
      cacheManager.defineConfiguration("quick-very-large", quickVeryLargeConfiguration);
   }

}