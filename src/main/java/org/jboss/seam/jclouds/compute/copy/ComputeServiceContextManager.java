package org.jboss.seam.jclouds.compute.copy;

import java.util.Properties;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.weld.extensions.bean.generic.Generic;
import org.jboss.weld.extensions.bean.generic.GenericConfiguration;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;

@GenericConfiguration(JCloudsComputeService.class)
public class ComputeServiceContextManager
{
   
   @Inject
   private ComputeServiceContextFactory factory;
   
   @Inject @Generic
   private Properties properties;
   
   @Inject
   private JCloudsComputeService config;
   
   @Produces @Default
   public ComputeServiceContext getContext()
   {
      return this.factory.createContext(config.provider(), properties);
   }
   
   public void cleanup(@Disposes @Generic ComputeServiceContext context)
   {
      context.close();
   }
   
}
