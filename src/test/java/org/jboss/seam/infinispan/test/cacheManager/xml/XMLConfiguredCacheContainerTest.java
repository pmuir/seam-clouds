package org.jboss.seam.infinispan.test.cacheManager.xml;

import static org.jboss.seam.infinispan.Deployments.baseDeployment;
import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.infinispan.AdvancedCache;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.infinispan.Infinispan;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test that a cache configured in XML is available, and that it can be
 * overridden
 * 
 * @see Config
 * @author Pete Muir
 * 
 */
@RunWith(Arquillian.class) @Ignore
public class XMLConfiguredCacheContainerTest
{

   @Deployment
   public static Archive<?> deployment()
   {
      return baseDeployment()
         .addPackage(XMLConfiguredCacheContainerTest.class.getPackage());
   }

   @Inject
   @VeryLarge
   private AdvancedCache<?, ?> largeCache;

   @Inject
   @Quick
   private AdvancedCache<?, ?> quickCache;

   @Test
   public void testVeryLargeCache()
   {
      assertEquals(1000, largeCache.getConfiguration().getEvictionMaxEntries());
   }

   @Test
   public void testQuickCache()
   {
      assertEquals(1000, quickCache.getConfiguration().getEvictionMaxEntries());
      assertEquals(1, quickCache.getConfiguration().getEvictionWakeUpInterval());
   }

}
