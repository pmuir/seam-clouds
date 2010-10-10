package org.jboss.seam.jclouds.compute.copy;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.jboss.weld.extensions.bean.defaultbean.DefaultBean;
import org.jclouds.compute.ComputeServiceContextFactory;

@ApplicationScoped
public class ComputeServiceContextFactoryManager
{

   private final ComputeServiceContextFactory factory;

   public ComputeServiceContextFactoryManager() throws IOException
   {
      this.factory = new ComputeServiceContextFactory();
   }

   @Produces
   @DefaultBean(ComputeServiceContextFactory.class)
   public ComputeServiceContextFactory getComputeServiceContextFactory()
   {
      return factory;
   }

}
