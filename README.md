Install Java
=============
Flink is only supported on Java 8 and 11:
Setting up Java 11 on macOS (intel) using brew as teh primary package manager
```shell
brew install openjdk@11
# create a symlink to the Java version
sudo ln -sfn /usr/local/opt/openjdk@11/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-11.jdk
# add the Java version to the PATH and append to the .zshrc file
echo 'alias j11="export JAVA_HOME=/usr/local/opt/openjdk@11 ; java -version"' >> ~/.zshrc
```

This will allow you to switch to Java 11 by running the following command:
```shell
j11
```

Install Flink
=============
To install Flink, download the Flink binary from the [Flink website](https://flink.apache.org/downloads.html). For example, to download Flink 1.19.1, run the following commands:
```shell
wget https://archive.apache.org/dist/flink/flink-1.19.1/flink-1.19.1-bin-scala_2.12.tgz
tar -zxvf flink-1.19.1-bin-scala_2.12.tgz
export FLINK_HOME=$PWD/flink-1.19.1
```
To start the Flink cluster, run the following command:
```shell
$FLINK_HOME/bin/start-cluster.sh
```

To stop the Flink cluster, run the following command:
```shell
$FLINK_HOME/bin/stop-cluster.sh
```

Project setup
=============
To setup the project, run the following command:
```shell
mvn clean install -DskipTests
```


```shell
mvn clean package -Pbuild-jar
$FLINK_HOME/bin/flink run target/flink-dev-1.0-SNAPSHOT.jar
```
