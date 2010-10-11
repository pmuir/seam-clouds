package org.jboss.seam.infinispan.test.defaultCache;

import static org.jboss.seam.infinispan.Deployments.baseDeployment;
import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests that the default cache is available
 * 
 * @author Pete Muir
 *
 */
@RunWith(Arquillian.class)
public class DefaultCacheTest
{
   
   @Deployment
   public static Archive<?> deployment()
   {
      return baseDeployment()
         .addPackage(DefaultCacheTest.class.getPackage());
   }
   
   /**
    * The default cache can be injected with no configuration
    */
   @Inject
   private Cache<String, String> cache;
   
   @Inject
   private AdvancedCache<String, String> advancedCache;
   
   @Test
   public void testDefaultCache()
   {
      // Simple test to make sure the default cache works
      cache.put("pete", "British");
      cache.put("manik", "Sri Lankan");
      assertEquals("British", cache.get("pete"));
      assertEquals("Sri Lankan", cache.get("manik"));
      /*
       * Check that the advanced cache contains the same data as the simple
       * cache. As we can inject either Cache or AdvancedCache, this is double
       * checking that they both refer to the same underlying impl and Seam
       * Clouds isn't returning the wrong thing.
       */
      assertEquals("British", advancedCache.get("pete"));
      assertEquals("Sri Lankan", advancedCache.get("manik"));
   }

}
