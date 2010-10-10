package org.jboss.seam.jclouds.blobstore;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.weld.extensions.bean.generic.Generic;
import org.jboss.weld.extensions.bean.generic.GenericConfiguration;
import org.jclouds.blobstore.AsyncBlobStore;
import org.jclouds.blobstore.BlobMap;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.InputStreamMap;
import org.jclouds.rest.Utils;


@GenericConfiguration(JCloudsBlobStore.class)
public class BlobStoreManager
{
   
   @Inject @Generic
   private BlobStoreContext context;
   
   @Inject
   private JCloudsBlobStore config;
   
   @Produces 
   public BlobStore getBlobStore()
   {
      return context.getBlobStore();
   }
   
   @Produces
   public InputStreamMap getInputStreamMap()
   {
      checkContainerSpecified();
      return context.createInputStreamMap(config.container());
   }
   
   @Produces
   public BlobMap getBlobMap()
   {
      checkContainerSpecified();
      return context.createBlobMap(config.container());
   }
   
   @Produces
   public AsyncBlobStore getAsyncBlobStore()
   {
      return context.getAsyncBlobStore();
   }
   
   @Produces
   public Utils getUtils()
   {
      return context.getUtils();
   }
   
   private void checkContainerSpecified()
   {
      if (config.container().equals(""))
      {
         throw new IllegalArgumentException("No container specified. For example, use @" + JCloudsBlobStore.class.getSimpleName() + "(\"s3\",\"bobs-container\")");
      }
   }
   
}
