package org.jboss.seam.infinispan;

import java.util.Collection;
import java.util.HashSet;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.Producer;

import org.infinispan.config.Configuration;
import org.infinispan.manager.CacheContainer;
import org.jboss.weld.extensions.annotated.AnnotatedTypeBuilder;
import org.jboss.weld.extensions.bean.generic.GenericLiteral;

public class InfinispanExtension implements Extension
{

   static class ConfigurationHolder
   {
      
      private final Producer<Configuration> producer;
      private final String name;
      
      ConfigurationHolder(Producer<Configuration> producer, String name)
      {
         this.producer = producer;
         this.name = name;
      }
      
      Producer<Configuration> getProducer()
      {
         return producer;
      }
      
      String getName()
      {
         return name;
      }
      
   }
   
   private final Collection<ConfigurationHolder> configurations;
   
   InfinispanExtension()
   {
      this.configurations = new HashSet<InfinispanExtension.ConfigurationHolder>();
   }
   
   void registerConfiguration(@Observes BeforeBeanDiscovery event, BeanManager beanManager)
   {
      event.addAnnotatedType(makeGeneric(Configuration.class));
      event.addAnnotatedType(makeGeneric(CacheContainer.class));
   }
   
   private <X> AnnotatedType<X> makeGeneric(Class<X> clazz)
   {
      AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<X>().readFromType(clazz);
      builder.addToClass(new GenericLiteral(Infinispan.class));
      return builder.create();
   }
   
   void observeConfiguration(@Observes ProcessProducer<?, Configuration> event)
   {
      if (event.getAnnotatedMember().isAnnotationPresent(Infinispan.class))
      {
         // This is generic configured configuration, so auto-register it
         String name = event.getAnnotatedMember().getAnnotation(Infinispan.class).value();
         configurations.add(new ConfigurationHolder(event.getProducer(), name));
      }
   }
   
   Collection<ConfigurationHolder> getConfigurations()
   {
      return configurations;
   }
   
}
