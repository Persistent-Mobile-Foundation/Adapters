# Mobile Foundation http adapter sample
This package contains http adapter ready to be built and deployed to Mobile Foundation server

## Prerequisits
You must have maven installed on your machine to use this artifact.
To install maven see: https://maven.apache.org/install.html

## Create the adapter file
To create the httpAdapter.adapter file run the following command. The httpAdapter.adapter file will be created in the target folder:
```
$ mvn clean install
```

## Deploy the adapter to Mobile Foundation server
To deploy the adapter to Mobile Foundation server edit these properties in the pom.xml file:

* mfpfUrl - The url of the Mobile Foundation server.
* mfpfUser - The login user name of the Mobile Foundation server
* mfpfPassword - The login password of the Mobile Foundation server
* mfpfRuntime - The runtime name for which the adapter is deployed. Usually it is 'mfp'.

In most cases the existing value are correct.
Run the following command to deploy the httpAdapter.adapter file to the server:
```
$ mvn adapter:deploy
```

#### note
It is possible to create the adapter file and deploy it to the server by using the command:
```
$ mvn clean install adapter:deploy
```

## Push and pull the adapter configuration
Adapter configuration can override values of properties from the adapter.xml. It is possible to pull and push the adapter configuration by using maven command.
In order to pull or push the adapter configuration, the property 'mfpfConfigFile' (path to an adapter config file)
must be either set in the pom.xml or provided in the command line (see examples below)

To pull the adapter configuration use:
```
$ mvn adapter:configpull -DmfpfConfigFile=target/myconfig.json
```
This will pull the configuration of the adapter from the Mobile Foundation server and will write it to the file 'target/myconfig.json'. If the file does not exist it will be created, otherwise
it will be overridden.

Example for pushing adapter configuration to the admin service
```
$ mvn adapter:configpush -DmfpfConfigFile=target/myconfig.json
```

This will push the configuration written in the file 'target/myconfig.json' to the Mobile Foundation server. The file must exist for this to succeed.