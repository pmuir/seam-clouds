package org.jboss.seam.jclouds;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.weld.extensions.bean.generic.Generic;
import org.jboss.weld.extensions.bean.generic.GenericProduct;
import org.jclouds.blobstore.AsyncBlobStore;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.BlobStoreContextFactory;

@Generic(CloudService.class)
public class BlobStoreManager
{
   
   @Inject
   private BlobStoreContextFactory factory;
   
   @Inject @GenericProduct
   private Credentials credentials;
   
   @Inject
   private CloudService cloudService;
   
   private BlobStoreContext context;
   
   @SuppressWarnings("unused")
   @PostConstruct
   private void init()
   {
      this.context = this.factory.createContext(cloudService.value(), credentials.getAccount(), credentials.getKey());
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
   private void preDestroy()
   {
      this.context.close();
   }
   
}
