package org.jboss.seam.infinispan.test.cacheManager.external;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.infinispan.AdvancedCache;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.infinispan.Infinispan;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for a cache container defined by some external mechanism
 * 
 * @author Pete Muir
 * @see Config
 */
@RunWith(Arquillian.class)
public class ExternalCacheContainerTest
{
   
   
   @Deployment
   public static Archive<?> deployment()
   {
      return ShrinkWrap.create(JavaArchive.class, "test.jar")
      .addPackage(ExternalCacheContainerTest.class.getPackage())
      .addPackage(Infinispan.class.getPackage());
   }
   
   @Inject @Large
   private AdvancedCache<?, ?> largeCache;
   
   @Inject @Quick
   private AdvancedCache<?, ?> quickCache;
   
   @Test
   public void testLargeCache()
   {
      assertEquals(100, largeCache.getConfiguration().getEvictionMaxEntries());
   }
   
   @Test
   public void testQuickCache()
   {
      assertEquals(1, quickCache.getConfiguration().getEvictionWakeUpInterval());
   }

}
