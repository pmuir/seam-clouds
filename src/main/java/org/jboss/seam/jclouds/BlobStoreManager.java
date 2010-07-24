package org.jboss.seam.jclouds;

import javax.annotation.PreDestroy;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.weld.extensions.bean.generic.Generic;
import org.jclouds.blobstore.AsyncBlobStore;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.BlobStoreContextFactory;

@Generic(Service.class)
public class BlobStoreManager
{
   
   private final BlobStoreContext context;
   
   @Inject
   public BlobStoreManager(BlobStoreContextFactory factory, Service service)
   {
      this.context = factory.createContext(service.provider(), service.account(), service.key()); 
   }
   
   @Produces
   public BlobStoreContext getContext()
   {
      return context;
   }
   
   @Produces 
   public BlobStore getBlobStore()
   {
      return context.getBlobStore();
   }
   
   @Produces 
   public AsyncBlobStore getAsyncBlobStore()
   {
      return context.getAsyncBlobStore();
   }
   
   @SuppressWarnings("unused")
   @PreDestroy
   private void cleanup()
   {
      context.close();
   }
   
}
