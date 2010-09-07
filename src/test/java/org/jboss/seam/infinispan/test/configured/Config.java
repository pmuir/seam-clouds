package org.jboss.seam.infinispan.test.configured;

import javax.enterprise.inject.Produces;

import org.infinispan.config.Configuration;
import org.jboss.seam.infinispan.Infinispan;

public class Config
{

   /**
    * Configure a "tiny" cache (with a very low number of entries), and associate
    * it with the qualifier {@link Tiny}.
    * 
    * This will use the default cache container.
    */
   @Produces
   @Infinispan("tiny")
   @Tiny
   public Configuration getTinyConfiguration()
   {
      Configuration configuration = new Configuration();
      configuration.setEvictionMaxEntries(1);
      return configuration;
   }

}