# dojo-spring-billet

## Create package 

1. git clone https://github.com/zesetup/dojo-spring-billet.git
2. mvn package

## From Eclipse IDE

1. File -> Import -> Projects from Git, Import as general project
2. Project properties -> Configure -> Convert to Maven Project
3. Project properties -> Export -> War FIle

## Access from Web-browser

Application URL:

http://%your_server_host%/dojo-spring-billet/


REST GET from service:

http://%your_server_host%/dojo-spring-billet/employee


## Openshift

??? Build Config MAVEN_ARGS: dependency:copy-dependencies -Popenshift  -e