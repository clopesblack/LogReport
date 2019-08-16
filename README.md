# LogReport

A small tool for reading a log file and generating an XML report.

## What was used in this project:
* Gradle
* Spring Spring Boot
* JAXB API
* Lombok
* Mongo
* Embedded Mongo

## Using Embedded Mongo And Tests

* The idea was to use Mongo, using as a cache tool, to retrieve the top 100 documents, remove them and then do it again. Thus avoiding memory problems. 
Mainly because of the Renderings list, which currently takes all documents and inputs it to the list.


* Unfortunately, the tests were still missing. I know how important it is, but for lack of time, I couldn't do it.

## How to use the project

* The application will print on the console a message to enter the file name.

```bash
$ gradlew bootRun

```


I hope you enjoy what has been done so far.
It was fun to do it.
