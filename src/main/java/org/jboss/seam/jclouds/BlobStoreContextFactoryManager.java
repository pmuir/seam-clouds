package org.jboss.seam.jclouds;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.jclouds.blobstore.BlobStoreContextFactory;

@ApplicationScoped
public class BlobStoreContextFactoryManager
{

   private final BlobStoreContextFactory factory;

   public BlobStoreContextFactoryManager()
   {
      this.factory = new BlobStoreContextFactory(new Properties());
   }

   @Produces
   public BlobStoreContextFactory getBlobStoreContextFactory()
   {
      return factory;
   }

}
