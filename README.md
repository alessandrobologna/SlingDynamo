SlingDynamo
===========

SlingDynamo is an Amazon's dynamo DB based Sling resource provider implementation

Table Structure in DynamoDB
===========================
Below is a specific table structure which is supported OOTB by this implementation. This table structure is generic enough to store any data in hierarchial structure. The only requirement for this table is to have 4 columns in addition to other application specific columns. <br/>

1. id (Number) - The unique identifier for a row
2. child_id (Number) - Unique identifier of a child row in context of its parent row
3. parent (Number) - id of the parent of this child row
4. children (Number Set)- A number set if the child_ids of this parent row

Here's a sample table structure:

| id | name          | child_id (Unique id in context of parent row) | children (child_ids of children rows) | parent (id of parent row) |
|----|---------------|-----------------------------------------------|---------------------------------------|---------------------------|
| 1  | North America |                                               | {1}                                   |                           |
| 2  | South America |                                               | {1}                                   |                           |
| 3  | USA           | 1                                             | {1,2}                                 | 1                         |
| 4  | Brazil        | 1                                             |                                       | 2                         |
| 5  | California    | 1                                             | {1}                                   | 3                         |
| 6  | San Francisco | 1                                             |                                       | 5                         |
| 7  | Texas         | 2                                             |                                       | 3                         |

If this table structure does not suit your requirements, you can always modify the DynamoDBResourceProvider to suit your requirements.

Usage Instructions
==================
1. Either download it from maven central or build it yourself.
2. Install the bundle on your sling instance.
3. Open up http://&lt;your-sling-instance&gt;/system/console/configMgr
4. Configure "Dynamo DB Resource Provider Factory" as follows
  1. Provide the aws region where your dynamodb resides in 'aws.region'. For e.g. 'us-west-2'
  2. Provide the root path under which you would want to access your dynamo DB resources in 'provider.roots'. For e.g. /content/dynamodb
  3. Optionally provide 'aws.endpoint' . This setting would override the 'aws.region' setting.
  4. Optionally provide 'resourceType' if you want to use your own rendering script for the dynamodb resources. If left blank, it uses the sling's default rendering mechanism.
5. Configure "SlingDynamoCredentialProvider" as follows:
  1. Provide your aws access key in aws.access.key.name
  2. Provide your aws access secret in aws.secret.access.key.name
6. Now access your dynamodb resource as follows:<br/>
http://localhost:8080/content/dynamodb/&lt;table_name&gt;/&lt;id&gt;.json<br/>
http://localhost:8080/content/dynamodb/&lt;table_name&gt;/&lt;id&gt;/&lt;child_id1&gt;/&lt;child_id2&gt;/.../&lt;child_idn&gt;.json<br/>
For e.g.<br/>
http://localhost:8080/content/dynamodb/data/1.json<br/>
would return<br/>
<pre>
{
id: "1",
name: "North America",
children: "[1]"
}
</pre>
http://localhost:8080/content/dynamodb/data/1/1.json<br/>
<pre>
{
id: "3",
child_id: "1",
name: "USA",
parent: "1",
children: "[1,2]"
}
</pre>

Building it yourself
====================
1. Clone https://github.com/sdmcraft/SlingDynamo
2. cd SlingDynamo
3. mvn clean install (This would try to install the bundle on your local sling instance at localhost:8080. So ensure that its running)

Running Tests
=============
This project uses 'Sling Testing Tools' as specified at [0](http://sling.apache.org/documentation/development/sling-testing-tools.html). The integration tests are run via HTTP requests against a Sling test instance that is started during the Maven build cycle. This blog at [1](http://labs.sixdimensions.com/blog/2013-06-05/creating-integration-tests-apache-sling/) is really helpful in getting things setup for writing the integration tests.

Also the tests use local DynamoDB instance (see [2](http://labs.sixdimensions.com/blog/2013-06-05/creating-integration-tests-apache-sling/)) which is basically a local DynamoDB setup with the same api support as the real dynamodb on AWS. This prevents the tests from incurring api usage costs on AWS. Here's a very useful resource for integrating local dynamodb with maven build cycle at [3](http://dynamodb.jcabi.com/)

1. [0] - http://sling.apache.org/documentation/development/sling-testing-tools.html
2. [1] - http://labs.sixdimensions.com/blog/2013-06-05/creating-integration-tests-apache-sling/
3. [2] - http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Tools.DynamoDBLocal.html
4. [3] - http://dynamodb.jcabi.com/
