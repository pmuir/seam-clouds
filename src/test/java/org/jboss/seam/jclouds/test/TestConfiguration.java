package org.jboss.seam.jclouds.test;

import javax.enterprise.inject.Produces;

import org.jboss.seam.jclouds.CloudService;
import org.jboss.seam.jclouds.Credentials;

public class TestConfiguration
{

   public static final String ACCESS_KEY_ID_PROPERTY_NAME = "aws.accessKeyId";
   public static final String SECRET_ACCESS_KEY_PROPERTY_NAME = "aws.secretAccessKey";
   
   private final String accessKeyId;
   private final String secretAccessKey;
   
   public TestConfiguration()
   {
      if (System.getProperty(ACCESS_KEY_ID_PROPERTY_NAME) == null)
      {
         throw new IllegalStateException("System property [" + ACCESS_KEY_ID_PROPERTY_NAME + "] is not set");
      }
      if (System.getProperty(SECRET_ACCESS_KEY_PROPERTY_NAME) == null)
      {
         throw new IllegalStateException("System property [" + SECRET_ACCESS_KEY_PROPERTY_NAME + "] is not set");
      }
      this.accessKeyId = System.getProperty(ACCESS_KEY_ID_PROPERTY_NAME);
      this.secretAccessKey = System.getProperty(SECRET_ACCESS_KEY_PROPERTY_NAME);
   }
   
   @Produces
   @CloudService("s3")
   @S3
   public Credentials getS3Credentials()
   {
      return new Credentials(accessKeyId, secretAccessKey);
   }

}