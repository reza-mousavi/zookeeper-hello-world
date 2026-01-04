# ZOOKEEPER: A Distributed Coordination Service

ZooKeeper is a distributed, open-source coordination service for distributed applications. It is designed to be easy to program to, and uses a data model styled after the familiar directory tree structure of file systems. It runs in Java and has bindings for both Java and C.

- ![Service](./_resources/zkservice.jpg)

## Design Goals

- **Simplicity** Similar to file system, fairly easy to understand 
- **Fast**
- **High Availability** It supports cluster, ensemble, of hosts
- **Ordered** Transactions are ordered
- **High Throughput** data is kept in-memory.

## Terms

- **Namespace** organized similarly to a standard file system holds data. 
- **Znode** The namespace consists of data registers - called znodes.


## Data Model

Unlike standard file systems, each node in a ZooKeeper namespace can have data associated with it as well as children. It is like having a file-system that allows a file to also be a directory. 

Znodes maintain a stat structure that includes version numbers for data changes, ACL changes, and timestamps, to allow cache validations and coordinated updates. Each time a znode's data changes, the version number increases. For instance, whenever a client retrieves data it also receives the version of the data.

ZooKeeper also has the notion of ephemeral nodes. These znodes exists as long as the session that created the znode is active. When the session ends the znode is deleted.

### Namespace

The namespace provided by ZooKeeper is much like that of a standard file system. A name is a sequence of path elements separated by a slash (/). Every node in ZooKeeper's namespace is identified by a path.

- ![Namespace](./_resources/zknamespace.jpg)

## Zookeeper API

One of the design goals of ZooKeeper is providing a very simple programming interface. As a result, it supports only these operations:

| Api          | Description                              |
|--------------|------------------------------------------|
| create       | creates a node at a location in the tree |
| delete       | deletes a node                           |
| exists       | tests if a node exists at a location     |
| get          | reads the data from a node               |
| set          | writes data to a node                    |
| get children | retrieves a list of children of a node   |
| sync         | waits for data to be propagated          |

### Zookeeper CLI

```
$ help
$ ls /
$ create /mycollection test
$ get /mycollection
$ set /mycollection junk
$ delete /mycollection
```

### Zookeeper on Docker

Run as a docker container

```
$ docker run --name some-zookeeper --restart always -d zookeeper
```

This image includes EXPOSE 2181 2888 3888 8080 (the zookeeper client port, follower port, election port, AdminServer port respectively), so standard container linking will make it automatically available to the linked containers. Since the Zookeeper "fails fast" it's better to always restart it.

Connect to Zookeeper from an application in another Docker container

```
$ docker run --name some-app --link some-zookeeper:zookeeper -d application-that-uses-zookeeper
```

Connect to Zookeeper from the Zookeeper command line client

```
$ docker run -it --rm \ 
            --network=zookeeper_zookeeper-network
            --link some-zookeeper:zookeeper \ 
            zookeeper zkCli.sh -server zookeeper
```

## Java Programs

### Program Design

Conventionally, ZooKeeper applications are broken into two units, 
one which maintains the connection, and the other which monitors data. 
In this application, the class called the **Executor** maintains the 
ZooKeeper connection, and the class called the **DataMonitor** monitors
the data in the ZooKeeper tree. 

Also, Executor contains the main thread and contains the execution logic. 
It is responsible for what little user interaction there is, as well as 
interaction with the executable program you pass in as an argument and 
which the sample (per the requirements) shuts down and restarts, according 
to the state of the znode.

## Running Application

- Build Java Client using Java 17
- run docker images
- Not working!!! ot be done manually!!! Create a new znode as create example simple

## More Info


| Technology       | Description                                                                                     |
|------------------|-------------------------------------------------------------------------------------------------|
| Zookeeper        | [Zookeeper](https://zookeeper.apache.org/)                                                      |
| Zookeeper CLI    | [Zookeeper CLI](https://zookeeper.apache.org/doc/current/zookeeperCLI.html)                     |
| Zookeeper Java   | [Zookeeper for Programmers](https://zookeeper.apache.org/doc/current/zookeeperProgrammers.html) |
| Zookeeper Docker | [Zookeeper Docker](https://hub.docker.com/_/zookeeper)                                          |
