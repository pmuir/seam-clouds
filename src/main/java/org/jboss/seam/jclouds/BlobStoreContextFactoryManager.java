package org.jboss.seam.jclouds;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

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
   public BlobStoreContextFactory getBlobStoreContextFactory()
   {
      return factory;
   }

}
