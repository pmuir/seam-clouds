package org.jboss.seam.jclouds.blobstore;

import java.util.Properties;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.weld.extensions.bean.generic.ApplyScope;
import org.jboss.weld.extensions.bean.generic.Generic;
import org.jboss.weld.extensions.bean.generic.GenericConfiguration;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.BlobStoreContextFactory;

@GenericConfiguration(JCloudsBlobStore.class)
public class BlobStoreContextManager
{
   
   @Inject
   private BlobStoreContextFactory factory;
   
   @Inject @Generic
   private Properties properties;
   
   @Inject
   private JCloudsBlobStore config;
   
   @Produces @Default @ApplyScope
   public BlobStoreContext getContext()
   {
      BlobStoreContext ctx = this.factory.createContext(config.provider(), properties);
      return ctx;
   }
   
   public void cleanupContext(@Disposes @Generic BlobStoreContext context)
   {
      context.close();
   }
   
}
