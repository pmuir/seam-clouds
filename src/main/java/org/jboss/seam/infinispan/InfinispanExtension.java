package org.jboss.seam.infinispan;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.Producer;

import org.infinispan.config.Configuration;
import org.infinispan.manager.CacheContainer;
import org.infinispan.notifications.Listener;
import org.jboss.weld.extensions.literal.GenericTypeLiteral;
import org.jboss.weld.extensions.reflection.annotated.AnnotatedTypeBuilder;

public class InfinispanExtension implements Extension
{

   static class ConfigurationHolder
   {
      
      private final Producer<Configuration> producer;
      private final Set<Annotation> qualifiers;
      private final String name;
      
      ConfigurationHolder(Producer<Configuration> producer, String name, AnnotatedMember<?> annotatedMember, BeanManager beanManager)
      {
         this.producer = producer;
         this.name = name;
         this.qualifiers = extractQualifiers(annotatedMember, beanManager);
      }
      
      private Set<Annotation> extractQualifiers(Annotated annotated, BeanManager beanManager)
      {
         Set<Annotation> qualifiers = new HashSet<Annotation>();
         for (Annotation annotation : annotated.getAnnotations())
         {
            if (beanManager.isQualifier(annotation.annotationType()))
            {
               qualifiers.add(annotation);
            }
         }
         return qualifiers;
      }
      
      Producer<Configuration> getProducer()
      {
         return producer;
      }
      
      String getName()
      {
         return name;
      }
      
      public Set<Annotation> getQualifiers()
      {
         return qualifiers;
      }
      
   }
   
   private final Collection<ConfigurationHolder> configurations;
   private final Map<String, Collection<InjectionTarget<Object>>> listeners;
   
   InfinispanExtension()
   {
      this.configurations = new HashSet<InfinispanExtension.ConfigurationHolder>();
      this.listeners = new HashMap<String, Collection<InjectionTarget<Object>>>();
   }
   
   void registerConfiguration(@Observes BeforeBeanDiscovery event, BeanManager beanManager)
   {
      event.addAnnotatedType(makeGeneric(Configuration.class));
      event.addAnnotatedType(makeGeneric(CacheContainer.class));
   }
   
   private <X> AnnotatedType<X> makeGeneric(Class<X> clazz)
   {
      AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<X>().readFromType(clazz);
      builder.addToClass(new GenericTypeLiteral(Infinispan.class));
      return builder.create();
   }
   
   void observeConfigurationProducer(@Observes ProcessProducer<?, Configuration> event, BeanManager beanManager)
   {
      if (event.getAnnotatedMember().isAnnotationPresent(Infinispan.class))
      {
         // This is generic configuration, so auto-register it
         String name = event.getAnnotatedMember().getAnnotation(Infinispan.class).value();
         configurations.add(new ConfigurationHolder(event.getProducer(), name, event.getAnnotatedMember(), beanManager));
      }
   }
   
   void observeListener(@Observes ProcessInjectionTarget<Object> event)
   {
      if (event.getAnnotatedType().isAnnotationPresent(Listener.class) && event.getAnnotatedType().isAnnotationPresent(Infinispan.class))
      {
         // This is generic listener, so auto-register it
         String name = event.getAnnotatedType().getAnnotation(Infinispan.class).value();
         addListener(name, event.getInjectionTarget());
      }
   }
   
   private void addListener(String name, InjectionTarget<Object> injectionTarget)
   {
      Collection<InjectionTarget<Object>> injectionTargets = listeners.get(name);
      if (injectionTargets == null)
      {
         injectionTargets = new ArrayList<InjectionTarget<Object>>();
         listeners.put(name, injectionTargets);
      }
      injectionTargets.add(injectionTarget);
   }
   
   Collection<ConfigurationHolder> getConfigurations()
   {
      return configurations;
   }
   
   Map<String, Collection<InjectionTarget<Object>>> getListeners()
   {
      return listeners;
   }
   
}
