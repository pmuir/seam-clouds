package org.jboss.seam.infinispan.event.cache;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.infinispan.notifications.Listenable;
import org.infinispan.notifications.cachelistener.event.CacheEntryActivatedEvent;
import org.infinispan.notifications.cachelistener.event.Event;
import org.jboss.seam.infinispan.event.AbstractEventBridge;

public class CacheEventBridge extends AbstractEventBridge<Event>
{

   public void registerObservers(Set<Annotation> qualifierSet, Listenable listenable)
   {
      Annotation[] qualifiers = qualifierSet.toArray(new Annotation[qualifierSet.size()]);
      if (hasObservers(CacheEntryActivatedAdapter.EMTPTY, qualifiers))
      {
         listenable.addListener(new CacheEntryActivatedAdapter(getBaseEvent().select(CacheEntryActivatedEvent.class, qualifiers)));
      }
   }

}
