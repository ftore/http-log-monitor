# HTTP log monitoring console application

## Requirements
You need Java 8 and gradle to build and run this program. 

## Getting started
You can get help message by running:

```
usage: java -jar http-log-monitor-0.0.1-SNAPSHOT.jar [-f <file>] [-h] [-t
       <traffic>]
 -f,--file <file>         Absolute access log file location (default:
                          /tmp/access.log)
 -h,--help                Print help message
 -t,--traffic <traffic>   Traffic threshold per second (default: 10
                          requests per second
```

## Further Explanations

#### Consume an actively written-to w3c-formatted HTTP access log
The program tails apache access.log file passed by -f argument. By default it reads file from the following location:

```
/tmp/access.log
``` 

It uses h2 memory database to save logs for further aggregations.


#### Every 10s, display in the console the sections of the web site with the most hits, as well as interesting summary statistics on the traffic as a whole.

Current program displays the following stats:

```
TOP 10 SECTIONS
+-------------------------+------------+
| Section                 | Count      |
+-------------------------+------------+
| /blog                   | 457        |
|                         | 452        |
| /presentations          | 369        |
| /images                 | 186        |
| /projects               | 104        |
| /files                  | 78         |
| /articles               | 34         |
| /scripts                | 17         |
| /icons                  | 8          |
| /misc                   | 6          |
+-------------------------+------------+

STATUS CODE COUNTS
+------------------+------------+
| Status Codes     | Count      |
+------------------+------------+
| 2XX              | 1491       |
| 3XX              | 198        |
| 4XX              | 37         |
| 5XX              | 1          |
+------------------+------------+

TOTAL BYTES TRANSFERRED
+----------------------+------------+
| Total Throughput     | 215272272KB    |
+----------------------+------------+

```

#### Make sure a user can keep the console app running and monitor traffic on their machine
This application is a simple console application. It is also, simple java program.
In order to run in the background, one should use nohup.

#### Whenever total traffic for the past 2 minutes exceeds a certain number on average, add a message saying that “High traffic generated an alert - hits = {value}, triggered at {time}”

#### Whenever the total traffic drops again below that value on average for the past 2 minutes, add another message detailing when the alert recovered

#### Make sure all messages showing when alerting thresholds are crossed remain visible on the page for historical reasons

The alerts will be displayed on the console in two different color:
- red: High traffic generated an alert - hits = {value}, triggered at {time}
- green: High traffic alert recovered - hits = {value}, recovered at {time}

#### Explain how you’d improve on this application design.
