package org.jboss.seam.infinispan.test.listener;

import org.infinispan.notifications.Listener;
import org.jboss.seam.infinispan.Infinispan;

@Listener
@Infinispan("small")
public class ModificationListener
{

}
