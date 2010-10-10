package org.jboss.seam.jclouds.test.compute;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.jboss.seam.jclouds.compute.copy.JCloudsComputeService;
import org.jboss.weld.extensions.resourceLoader.Resource;

@ApplicationScoped
public class Config
{

   /**
    * Configure JClouds to create a compute node on EC2.
    * 
    * @param awsProperties the properties containing the properties
    *           jclouds.identity and jclouds.credential needed to access AWS
    * @return the {@link Properties} providing AWS credentials
    */
   @Produces
   @JCloudsComputeService(provider = "ec2")
   @RequestScoped
   @EC2TestComputeService
   public Properties getS3CredentialsProperties(@Resource("aws.properties") Properties awsProperties)
   {
      return awsProperties;
   }

}