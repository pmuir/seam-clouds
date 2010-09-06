package org.jboss.seam.jclouds.test;

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.jclouds.CloudService;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.domain.Blob;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class S3Test
{

   // Configure the bucket and object name 
   private static final String BUCKET_NAME = "cdi-jclouds-test";
   private static final String OBJECT_NAME = "testblob";

   // Configure Arquillian
   @Deployment
   public static Archive<?> deploy()
   {
      return ShrinkWrap.create("test.jar", JavaArchive.class).addPackage(S3Test.class.getPackage()).addPackage(CloudService.class.getPackage());
   }

   // Inject the S3 BlobStore, using the credentials from TestConfiguration
   @Inject
   @S3
   private BlobStore blobStore;

   @Test
   public void testService() throws Exception
   {
      try
      {
         // Create the container and blob
         blobStore.createContainerInLocation(null, BUCKET_NAME);
         Blob blob = blobStore.newBlob(OBJECT_NAME);
         // Add some content to S3
         blob.setPayload("Pete!");
         blobStore.putBlob(BUCKET_NAME, blob);
         
         // Read back the result, convert the stream to a string and check it is correct
         String result = convertStreamToString(blobStore.getBlob(BUCKET_NAME, OBJECT_NAME).getContent());
         assertEquals("Pete!", result);
      }
      finally
      {
         // Clean up up after ourselves
         blobStore.deleteContainer(BUCKET_NAME);
         // Note that CDI will clean up the JClouds services automatically for us!
      }
   }

   // Utility method - bog standard java!
   public static String convertStreamToString(InputStream is) throws IOException
   {
      /*
       * To convert the InputStream to String we use the
       * BufferedReader.readLine() method. We iterate until the BufferedReader
       * return null which means there's no more data to read. Each line will
       * appended to a StringBuilder and returned as String.
       */
      if (is != null)
      {
         final char[] buffer = new char[0x10000];
         StringBuilder out = new StringBuilder();
         Reader in = new InputStreamReader(is, "UTF-8");
         int read;
         do
         {
            read = in.read(buffer, 0, buffer.length);
            if (read > 0)
            {
               out.append(buffer, 0, read);
            }
         }
         while (read >= 0);
         return out.toString();
      }
      else
      {
         return "";
      }
   }

}
