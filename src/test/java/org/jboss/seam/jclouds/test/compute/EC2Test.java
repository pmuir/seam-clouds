package org.jboss.seam.jclouds.test.compute;

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

import static org.jclouds.compute.predicates.NodePredicates.runningWithTag;

import java.util.Set;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.jclouds.compute.copy.JCloudsComputeService;
import org.jboss.seam.jclouds.compute.copy.Running;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EC2Test
{
   
   public static final String TAG = "jcloudstest";

   // Configure Arquillian
   @Deployment
   public static Archive<?> deploy()
   {
      return ShrinkWrap.create(JavaArchive.class, "test.jar")
         .addPackage(EC2Test.class.getPackage())
         .addPackage(JCloudsComputeService.class.getPackage());
   }

   // Inject the @EC2TestComputeService ComputeService container, using the 
   // credentials from the properties file
   @Inject
   @EC2TestComputeService @Running
   private Set<ComputeMetadata> nodes;
   
   @Inject @EC2TestComputeService
   private TemplateBuilder templateBuilder;
   
   @Inject @EC2TestComputeService
   private ComputeService computeService;
   
   @Test
   public void testNode() throws Exception
   {
      // Start the default template
      Template template = templateBuilder.build();
      // TODO how to test this, for now just check stuff  got injected...
   }
   
   @After
   public void cleanup()
   {
      computeService.destroyNodesMatching(runningWithTag(TAG));
   }
   
}
