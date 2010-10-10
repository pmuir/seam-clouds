package org.jboss.seam.jclouds.compute.copy;

import static org.jboss.weld.extensions.reflection.Reflections.cast;

import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.weld.extensions.bean.generic.Generic;
import org.jboss.weld.extensions.bean.generic.GenericConfiguration;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.LoadBalancerService;
import org.jclouds.compute.Utils;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.Location;
import org.jclouds.rest.RestContext;

@GenericConfiguration(JCloudsComputeService.class)
public class ComputeServiceManager
{
   
   @Inject @Generic
   private ComputeServiceContext context;
   
   @Produces
   public ComputeService getComputeService()
   {
      return context.getComputeService();
   }
   
   @Produces @Running
   public Set<ComputeMetadata> getNodes()
   {
      return cast(getComputeService().listNodes());
   }
   
   @Produces
   public Set<Location> getAssignableLocations()
   {
      return cast(getComputeService().listAssignableLocations());
   }
   
   @Produces
   public Set<Hardware> getHardwareProfiles()
   {
      return cast(getComputeService().listHardwareProfiles());
   }
   
   @Produces
   public Set<Image> getImages()
   {
      return cast(getComputeService().listImages());
   }
   
   @Produces
   public LoadBalancerService getLoadBalancerService()
   {
      return context.getLoadBalancerService();
   }
   
   @Produces
   public <S, A> RestContext<S, A> getProviderSpecificContext()
   {
      return context.getProviderSpecificContext();
   }
   
   @Produces
   public TemplateBuilder getTemplateBuilder()
   {
      return getComputeService().templateBuilder();
   }
   
   @Produces
   public TemplateOptions getTemplateOptions()
   {
      return getComputeService().templateOptions();
   }
   
   @Produces 
   public Utils getUtils()
   {
      return context.getUtils();
   }
   
}
