package org.jboss.seam.jclouds.test.blobstore;

import static org.jboss.seam.jclouds.test.blobstore.S3BlobStoreProducer.CONTAINER;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jclouds.blobstore.BlobMap;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.domain.Blob;

@RequestScoped
public class Consumer
{
   
   @Inject
   @TestContainer
   private BlobMap blobMap;

   @Inject
   @TestContainer
   private BlobStore blobStore;
   
   @PostConstruct
   public void createContainer()
   {
      blobStore.createContainerInLocation(null, CONTAINER);
   }
   
   @PreDestroy
   public void removeContainer()
   {
      blobMap.remove(CONTAINER);
   }
   
   public void putInBlobStore(String key, String payload)
   {
      // Create the blob
      Blob blob = blobMap.newBlob(key);
      // Add some content to transient container
      blob.setPayload("Pete!");
      blobMap.put(key, blob);
   }
   
   public String getFromBlobStore(String key) throws IOException
   {
      // Read back the result, convert the stream to a string and check it is
      // correct
      return convertStreamToString(blobMap.get(key).getPayload().getInput());
   }
   
   public BlobStore getBlobStore()
   {
      return blobStore;
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
