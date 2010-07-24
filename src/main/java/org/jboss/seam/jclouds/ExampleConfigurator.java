package org.jboss.seam.jclouds;

import javax.enterprise.inject.Produces;

import org.jclouds.aws.s3.S3;
import org.jclouds.blobstore.BlobStoreContext;

public class ExampleConfigurator
{
   
   @SuppressWarnings("unused")
   @Produces
   @Service(provider = "s3", account = "foo", key = "bar")
   @S3
   private BlobStoreContext blobStoreContext;

}
