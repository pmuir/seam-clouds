package org.jboss.seam.infinispan.test.cacheManager.programatic;

import static org.jboss.seam.infinispan.Deployments.baseDeployment;
import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.infinispan.AdvancedCache;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for a cache container defined programatically
 * 
 * @author Pete Muir
 * @see Config
 */
@RunWith(Arquillian.class) @Ignore
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
   
   @Test
   public void testSmallCache()
   {
      assertEquals(7, smallCache.getConfiguration().getEvictionMaxEntries());
   }
   
}
