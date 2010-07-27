package org.jboss.seam.jclouds;

import javax.enterprise.inject.Produces;

import org.jclouds.aws.s3.S3;

public class ExampleConfigurator
{
   
   @Produces
   @CloudService("s3")
   @S3
   public Credentials getS3Credentials()
   {
      return new Credentials("foo", "bar");
   }

}
