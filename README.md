# README

## Deployment

Java 8 is required to run this application.

### From source

The application can be deployed from source code by following the steps below.

- Execute *mvn package* command in the directory containing pom.xml file.
- Set environment variable *CONFIGURATION-WEATHER-SERVICE-OPEN_WEATHER_MAP-API-KEY* to your OpenWeatherMap API key.
- Execute *java -jar target/weather-service-1.0-SNAPSHOT.jar server ./weather-service.yml* command.
- The application documentation will be accessible at **http://localhost:2080/v1/docs/** url.
- The current weather API will be accessible at **http://localhost:2080/v1/current?country=UK&city=Cambridge** url.

### From package

For convenience I have prepackaged the application and copied configuration file to directory called *package*. The
application can be deployed from package by following the steps below.

- Set environment variable *CONFIGURATION-WEATHER-SERVICE-OPEN_WEATHER_MAP-API-KEY* to your OpenWeatherMap API key.
- Execute *java -jar weather-service-1.0-SNAPSHOT.jar server ./weather-service.yml* command in the *package* directory.

### From Dockerfile

For convenience I have created a Dockerfile that cane be used to run the application in the *package* directory. The 
application can be deployed from Dockerfile by following the steps below.

- Execute *sudo docker build -t weather-service .* inside *package* directory.
- Execute *sudo docker run -d -p 2080:2080 -p 2081:2081 -e CONFIGURATION-WEATHER-SERVICE-OPEN_WEATHER_MAP-API-KEY=[your OpenWeatherMap API key] weather-service*


## Configuration

### Dropwizard configuration:
    
Dropwizard can be configured via **weather-service.yml** file located in the root directory. The parameters can be overridden 
by the **environment variables**. This configuration file can be used to configure Jetty which is the underlying web server
of the application.
        
See more:

- Dropwizard configuration - https://www.dropwizard.io/1.0.5/docs/manual/configuration.html

### Application configuration
   
The application can be configured via **environment variables**. The configuration concerns **Cross-Origin Resource filter**, 
**Denial of Service filter** and **Open Weather Map Engine**.


## Versions
    
The API paths have to be **versioned** in order to prevent clients of the APIs from breaking when changes to the APIs 
are made. There are many methods of versioning the APIs such as path parameter, query parameter or custom request 
header. My personal preference is a **path parameter** because of increased **discoverability**, ability to do **GET requests 
directly from the browser** and **better alignment with object oriented paradigm**.


## Health

The application health is an important part of the operational management of any platform. The most common ways to 
expose the information about the health are **Liveness API**, **Readiness API** and a **Health Check**. 

### Liveness Check

The Livenesss API states weather the application is **able to receive requests** and can be accessed via 
**http://localhost:2080/health/live**.

### Readiness Check

The Readiness API states whether application is **ready to process requests** i.e. whether all integrated pieces and 
internal code are ready for processing and can be accessed via **http://localhost:2080/health/ready**.

### Health Check

The Health Check for the application can be used to **monitor the application** throughout its life. It gives the
ability to determine if **application should be redeployed** which is especially important in a **large platform** where 
**automation is critical** for managing the system. The Health Check for this application can be accessed via
**http://localhost:2081/healthcheck**.


## Filters

This implementation contains two common request **filters** to prevent **Cross-Origin Resource Sharing** and **Denial of 
Service**.
    
#### Cross-Origin Resource filter

The Cross-Origin Resource Sharing filter **facilitates security restrictions** for the **incoming requests** and can be 
configured via **environment variables** listed below.

| Parameter                                          | Description                                                |
|----------------------------------------------------|------------------------------------------------------------|
| CONFIGURATION-WEATHER-SERVICE-CORS-ALLOWED-ORIGINS | Value of the Access-Control-Allow-Origin response header.  |
| CONFIGURATION-WEATHER-SERVICE-CORS-ALLOWED-HEADERS | Value of the Access-Control-Allow-Headers response header. |
| CONFIGURATION-WEATHER-SERVICE-CORS-ALLOWED-METHODS | Value of the Access-Control-Allow-Methods response header. |

#### Denial of Service filter
    
The Denial of Service filter **limits the amount of incoming requests** to make sure that the application **is not flooded
with requests** which can cause it to **run ouf of resources and crash**. This filter can be configured via **environment** 
variables listed below.
    
| Parameter                                              | Description                                                                 |
|--------------------------------------------------------|-----------------------------------------------------------------------------|
| CONFIGURATION-WEATHER-SERVICE-DOS-MAX-REQUESTS-PER-SEC | Value of the maximum number of requests per second for a single connection. |
| CONFIGURATION-WEATHER-SERVICE-DOS-DELAY-MS             | Value of the delay imposed on requests over the limit.                      |


## API Documentation

It is always important to provide an **easy to read API documentation** for the developers. This application uses **Swagger**
to generate **OpenAPI 3 specification** which is available at **http://localhost:2080/v1/openapi.json**. 

This specification can be fed into an **User Interface** to generate documentation pages ready for developers to use. This
application uses **Swagger UI** for this purpose which is a **well established** and **easy to use visualization interface** for 
API resources. The Swagger UI is available at **http://localhost:2080/docs/**.

See more:

- OpenAPI 3 - https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.0.md
- Swagger - https://swagger.io/
- Swagger UI - https://swagger.io/tools/swagger-ui/
    
## Error handling

This implementation contains error handling for **common exceptions** that can happen during request processing. The **error
format** is specified below.
    
| Parameter | Description                                                                                                                                              |
|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| id        | A unique identifier for this particular occurrence of the problem.                                                                                       |
| link      | An object that contains link information that corresponds to the error.                                                                                  |
| status    | The HTTP status code applicable to this problem.                                                                                                         |
| title     | A short, human-readable summary of the problem that should not change from occurrence to occurrence of the problem, except for purposes of localization. |
| detail    | A human-readable explanation specific to this occurrence of the problem. This field’s value can be localized.                                            |


## Metrics

The Dropwizard Metrics is part of a Dropwizard library and **captures basic metrics** about **JVM and the application** 
such as **number of connection**, **number of requests per method**, **number of responses per status code** etc. This 
data can be accessed via **http://localhost:2081/metrics?pretty=true**.


## Authentication

The preferred method of **authentication for APIs** utilizes **JSON Web Token** to make sure that **whoever is making a 
request is who they say they are**. This is accomplished by generating a **cryptographic key pair** consisting of a 
**Private Key** and a **Public Key**. 

The **Private Key** is used to **generate a signature** for the request before it is sent to the server and **has to be 
stored in a secure way**.

The **Public Key** is used to validate that the request signature **matches the actual request** and is **available 
publicly**. 

This authentication method makes it possible to have a **completely stateless REST service** as there is **no need for 
sessions** to store the state of user authentication.

## Authorization

The **authorization** can be achieved by enriching the **JSON Web Token** with the authorization information. This 
consists of **roles** that a user making the request has **assigned**. The actual **authorization logic** should be 
placed at the **REST layer** of the system to **prevent unauthorized requests** as soon as possible.

My preferred authorization method consists of **Shiro like syntax** where one role exists per CRUD API. The table below
illustrates how **API Methods** are related to **CRUD** and authorization **roles**.

| Method | CRUD   | Role | Example     | Description                                      |
|--------|--------|------|-------------|--------------------------------------------------|
| POST   | Create | c    | documents:c | The user making a request can create a document. |
| GET    | Read   | r    | documents:r | The user making a request can read documents.    |
| PATCH  | Update | u    | documents:u | The user making a request can update a document. |
| DELETE | Delete | d    | documents:d | The user making a request can delete a document. |

This can be **enhanced** by **combining the roles** under a given resource for example *documents:cd* means that user 
making a request **can create** and **delete** documents but not **read** or **update** them. Further **enhancements** 
can be made to **map resources** to **integer IDs** in order to **reduce the size** of the **JWT** which helps to 
**reduce** the **network traffic** between the client and server.

See more:

- Shiro authorization - https://shiro.apache.org/permissions.html

## Test cases

| Description                                                                           | URL                                                                    | Output                                                                             |
|---------------------------------------------------------------------------------------|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| Send a request without parameters                                                     | http://localhost:2080/v1/current                                          | 400 Response with errors specified for both country and city as missing parameters |
| Send a request with only country specified                                            | http://localhost:2080/v1/current?country=UK                               | 400 Response with error specified for missing city parameter                       |
| Send a request with only city specified                                               | http://localhost:2080/v1/current?city=Cambridge                           | 400 Response with error specified for missing country parameter                    |
| Send a request with correct country parameter and incorrect city parameter            | http://localhost:2080/v1/current?country=UK&city=Dublin                   | 404 Response with error specified for resource not found                           |
| Send a request with correct city parameter and incorrect country parameter            | http://localhost:2080/v1/current?country=U&city=Dublin                    | 400 Response with validation error specified for country                           |
| Send a request with correct city and country parameter                                | http://localhost:2080/v1/current?country=UK&city=Cambridge                | 200 Response with weather parameters                                               |
| Send a request with correct country and city parameter and incorrect format parameter | http://localhost:2080/v1/current?country=UK&city=Cambridge&format=JSO     | 400 Response with error specified for incorrect format parameter                   |
| Send a request with correct country and city parameter and correct format parameter   | http://localhost:2080/v1/current?country=UK&city=Cambridge&format=JSON    | 200 Response with weather parameters specified in JSON format                      |
| Send a request with correct country and city parameter and correct format parameter   | http://localhost:2080/v1/current?country=UK&city=Cambridge&format=YAML    | 200 Response with weather parameters specified in YAML format                      |
| Send a request with correct country and city parameter and incorrect units parameter  | http://localhost:2080/v1/current?country=UK&city=Cambridge&units=IMP      | 400 Response with error specified for incorrect units parameter                    |
| Send a request with correct country and city parameter and correct units parameter    | http://localhost:2080/v1/current?country=UK&city=Cambridge&units=METRIC   | 200 Response with weather parameters specified in METRIC units                     |
| Send a request with correct country and city parameter and correct units parameter    | http://localhost:2080/v1/current?country=UK&city=Cambridge&units=IMPERIAL | 200 Response with weather parameters specified in IMPERIAL units                   |

## Other things to consider when designing APIs

- Pagination
- Sorting
- Filtering
- Metadata
- HATEOAS
- Pooling for long running API requests
- Sync / Async API responses