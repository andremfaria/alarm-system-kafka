# alarm-system-kafka
An alarm system that monitor hosts' CPU &amp; memory indicators and send notifications according to user configurations. 

## Context
After took some kafka udemy courses, i did this mini project to apply some of the knowledge that i got. 
 
### Kafka topics covered
* Consumer API
* Producer API
* Streams
* Sink Connector. 

### Requisites
* Multiple hosts send information about the memory and cpu usage. 
* The user has the possibility to configure mem/cpu limits and the time to trigger alarms.
* If the limits are exceed inside a time window then a notification must be send.
* All the messages must be audited in a text file.
### Architeture
![image](https://github.com/user-attachments/assets/e0b947b0-345a-4636-80d9-e5a051c77094)

## Configurations
### audit-consumer
#### resources/fileconnector.properties
Config var | Description | Example
------------ | ------------- | -------------
topics | Topic to read  | user_alarms
file | Log file to write | /Users/andfaria/test.log
### notifier-consumer
#### resources/application.properties
Config var | Description | Example
------------ | ------------- | -------------
smtpServer | Email server  | smtp.gmail.com
Port | Server port | 587
emailOrigin | Sender | notifier@gmail.com
username | User | notifier@gmail.com
password | Password | test
emailDestination | Destionation email | admin@gmail.com
telegramToken | Telegram Token | blablabla231321
telegramNotification | Set on/off telegram notification | on
telegramChatId | Conversation ID to send notification  | 123123


### transformer-stream
#### resources/alarms.properties
Config var | Description | Example
------------ | ------------- | -------------
memory | Minimum free memory in MBs | 500
cpu | Max cpu usage | 50
retention | Time window to consider cpu and memory indicators Milliseconds | 3600
## Running the app
### Test environment
- MacOS Catalina 10.15.4
- Kafka Server 2.3.0
- Java 8

> Note: In order to send emails successfully with gmail, i needed to disable the two-factor authenticator and enable less secure apps. 

### Compiling
Navigating to root dir and execute:

```

mvn clean install assembly:single

```

Start kafka and zookeeper:

```
docker-compose -f docker-compose.yaml up
```

### Running

#### audit-consumer
```
cd audit-consumer/
export CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')"
connect-standalone.sh config/worker.properties config/fileconnector.properties
```
#### client-producer
```
cd client_producer/target
#arg1 -> bootstrapServer, arg2 -> topic to write, arg3 -> interval between writes
java -cp client-producer-1.0.0-jar-with-dependencies.jar org.aflabs.kafka.producer.client.Producer "127.0.0.1:9092" host_stats 10000
```
#### transformer-stream
```
cd transformer-stream/target
#arg1 -> bootstrapServer, arg2 -> applicationId. If need edit file in classes/alarms.properties
java -cp "classes:./*" org.aflabs.kafka.stream.transformer.AlarmsStream "127.0.0.1:9092" "alarms-system-stream-9"
```
#### notifier-consumer
```
cd notifier-consumer/target
#arg1 -> bootstrapServer, arg2 -> topic to read. If need edit file in classes/application.properties
java -cp classes:notifier-consumer-1.0.0-jar-with-dependencies.jar org.aflabs.kafka.consumer.notifier.Consumer  "127.0.0.1:9092" "alarm-consumer"
