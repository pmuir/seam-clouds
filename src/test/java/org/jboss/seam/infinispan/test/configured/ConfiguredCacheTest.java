package org.jboss.seam.infinispan.test.configured;

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
      return ShrinkWrap.create(JavaArchive.class, "test.jar")
      .addPackage(ConfiguredCacheTest.class.getPackage())
      .addPackage(Infinispan.class.getPackage());
   }
   
   /**
    * Inject a cache configured by application
    */
   @Inject @Tiny
   private AdvancedCache<String, String> tinyCache;
   
   @Test
   public void testTinyCache()
   {
      // Check that we have the correctly configured cache
      assertEquals(1, tinyCache.getConfiguration().getEvictionMaxEntries());
   }

}
