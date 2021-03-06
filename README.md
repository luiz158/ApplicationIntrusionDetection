Application Intrusion Detection
============
This repository is all about Application Intrusion Detection. Have a look at my [presentations](https://blog.dominikschadow.de/events) I've given on this topic or watch the 
recordings at [W-JAX 2015](https://jaxenter.de/web-app-security-43952) (German) or [JavaZone 2016](https://vimeo.com/181788148) (English).

# duke-encounters
**Duke Encounters** is a complete web application utilizing Application Intrusion Detection based on [OWASP AppSensor](http://appsensor.org). 
This web application is using [Spring Boot](http://projects.spring.io/spring-boot) with a [h2](http://www.h2database.com) in-memory database 
and offers a [Thymeleaf](http://www.thymeleaf.org) UI. Keep in mind that all entered information is only stored temporarily and will be lost 
when restarting. Run this application with **mvn spring-boot:run**. After launching, open the web application in your browser at **http://localhost:8080**.

As an alternative, you can use [Boxfuse](https://boxfuse.com) to fuse, launch the app within an immutable image and open the web application 
in your browser at **http://localhost:8080**.

And of course you can use [Docker](https://www.docker.com) to create an image via `mvn docker:build`, launch the container 
via `docker run -d -i -p 8080:8080 -t duke-encounters` and open the web application in your browser at **http://localhost:8080**.

Available users are listed in the [src/main/resources/data.sql](https://github.com/dschadow/ApplicationIntrusionDetection/blob/master/duke-encounters/src/main/resources/data.sql) 
file. Username and password are always identical.

## Meta
[![Build Status](https://travis-ci.org/dschadow/ApplicationIntrusionDetection.svg)](https://travis-ci.org/dschadow/ApplicationIntrusionDetection)
[![Code Climate](https://codeclimate.com/github/dschadow/ApplicationIntrusionDetection/badges/gpa.svg)](https://codeclimate.com/github/dschadow/ApplicationIntrusionDetection)
[![codecov](https://codecov.io/gh/dschadow/ApplicationIntrusionDetection/branch/develop/graph/badge.svg)](https://codecov.io/gh/dschadow/ApplicationIntrusionDetection)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
