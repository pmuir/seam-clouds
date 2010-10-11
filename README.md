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
create the file src/test/resources/aws.properties

    jclouds.identity <AccessKeyId>
    jclouds.credential <SecretAccessKey>
