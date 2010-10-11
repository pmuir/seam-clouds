package org.jboss.seam.infinispan.test.configured;

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
 * Tests that the simple form of configuration works
 * 
 * @author Pete Muir
 * @see Config
 *
 */
@RunWith(Arquillian.class)
public class ConfiguredCacheTest
{
   
   @Deployment
   public static Archive<?> deployment()
   {
      return baseDeployment()
         .addPackage(ConfiguredCacheTest.class.getPackage());
   }
   
   /**
    * Inject a cache configured by the application
    */
   @Inject @Tiny
   private AdvancedCache<String, String> tinyCache;
   
   /**
    * Inject a cache configured by application
    */
   @Inject @Small
   private AdvancedCache<String, String> smallCache;
   
   @Test
   public void testTinyCache()
   {
      // Check that we have the correctly configured cache
      assertEquals(1, tinyCache.getConfiguration().getEvictionMaxEntries());
   }

   @Test
   public void testSmallCache()
   {
      // Check that we have the correctly configured cache
      assertEquals(10, smallCache.getConfiguration().getEvictionMaxEntries());
   }

}
