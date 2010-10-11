package org.jboss.seam.infinispan.test.cacheManager.programatic;

import static org.jboss.seam.infinispan.Deployments.baseDeployment;
import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.infinispan.AdvancedCache;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for a cache container defined programatically
 * 
 * @author Pete Muir
 * @see Config
 */
@RunWith(Arquillian.class)
public class ProgramaticCacheContainerTest
{
   
   
   @Deployment
   public static Archive<?> deployment()
   {
      return baseDeployment()
      .addPackage(ProgramaticCacheContainerTest.class.getPackage());
   }
   
   @Inject @Small
   private AdvancedCache<?, ?> smallCache;

   @Inject SmallCacheObservers observers;
   
   @Test
   public void testSmallCache()
   {
      assertEquals(7, smallCache.getConfiguration().getEvictionMaxEntries());
      assertEquals(1, observers.getCacheStartedEventCount());
   }
   
}
