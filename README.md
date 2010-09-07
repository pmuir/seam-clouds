Seam Clouds
===========

This module contains integration between for popular cloud frameworks and CDI.


JClouds
-------

Currently, support for JClouds BlobStore is provided.


Infinispan
----------

Currently, support for Inifispan's Cache API is provided.


Running the tests
-----------------

To run the tests, you must have an AWS account, and sign up for S3. Having done this,
add this profile to your `settings.xml`:

`      <profile>
         <id>aws</id>
         <activation>
            <property>
               <name>aws</name>
               <value>!false</value>
            </property>
         </activation>
         <properties>
            <!-- Your AWS Access Key ID -->
            <aws.accessKeyId></aws.accessKeyId>
            <!-- Your AWS Secret Access Key-->
            <aws.secretAccessKey></aws.secretAccessKey>
         </properties>
      </profile>`


Running the tests from Eclipse
------------------------------

To run the tests from Eclipse, you'll need to configure two _Program Arguments_ in the test's _Run Configuration_:

`-Daws.accessKeyId=<AccessKeyId>
-Daws.secretAccessKey=<SecretAccessKey>`