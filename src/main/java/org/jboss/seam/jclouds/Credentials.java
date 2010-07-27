package org.jboss.seam.jclouds;

import org.jboss.weld.extensions.bean.generic.Generic;

@Generic(CloudService.class)
public class Credentials
{
   
   private final String account;
   private final String key;
   
   public Credentials(String account, String key)
   {
      this.account = account;
      this.key = key;
   }
   
   public String getAccount()
   {
      return account;
   }
   
   public String getKey()
   {
      return key;
   }
   
}
