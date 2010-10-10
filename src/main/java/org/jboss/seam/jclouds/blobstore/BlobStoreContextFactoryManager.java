package org.jboss.seam.jclouds.blobstore;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.jboss.weld.extensions.bean.defaultbean.DefaultBean;
import org.jclouds.blobstore.BlobStoreContextFactory;

@ApplicationScoped
public class BlobStoreContextFactoryManager
{

   private final BlobStoreContextFactory factory;

   public BlobStoreContextFactoryManager() throws IOException
   {
      this.factory = new BlobStoreContextFactory();
   }

   @Produces
   @DefaultBean(BlobStoreContextFactory.class)
   public BlobStoreContextFactory getBlobStoreContextFactory()
   {
      return factory;
   }

}
