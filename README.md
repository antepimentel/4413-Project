# Project Setup
## Using a local MySQL database

1. Install MySQL, it will probably ask you install some other things as well 
2. Create a new schema with MySQL Workbench
3. Run the project-create.sql script on the new schema
4. In the Eclipse project, create a new file called context.xml in the META-INF folder, copy this into it

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context privileged="true" reloadable="true">
<WatchedResource>WEB-INF/web.xml</WatchedResource>
<Manager pathname="" />
<Resource name="jdbc/project_DB" auth="Container" type="javax.sql.DataSource"
             maxTotal="100" maxIdle="30" maxWaitMillis="10000"
             username="{YOUR USERNAME}" password="{YOUR PASSWORD}" driverClassName="com.mysql.jdbc.Driver"
             url="jdbc:mysql://localhost:3306/{YOUR DATABASE NAME}"/>
</Context>

```
5. In the Eclipse project, add this to your WEB-INF/web.xml
```xml
<resource-ref>
    <description>DB Connection</description>
    <res-ref-name>jdbc/project_DB</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
</resource-ref>
```
---
