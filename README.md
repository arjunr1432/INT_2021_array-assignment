# eRate AS Coding Assignment

## Intro:
We are developing a simple HTTP-based web site with REST API that maintains an integer array, initially empty, and features the following functionality:

• Displays the array content (HTTP) or returns the current array (API)

• Provides functionality to add an integer to the array

• Provides functionality to check whether the array can be split in two, without reordering the elements, so that the sum of the two resulting arrays is equal. For example, array [4, 1, 1, 1, 1] can be split this way (in [4] and [1, 1, 1, 1]), while [1, 1, 5, 3] cannot be split in two arrays with equal sums.
## System design
The system design document along with the implementation algorithm can be found at below git url.
```
https://github.com/arjunr1432/array-assignment/blob/master/documents/02_eRateAS_System_Design_Doccument.pdf
```
## Open API Specification
We have defined the API specification in Open API 3.0, and the same can be found at the below git url.
```
https://github.com/arjunr1432/array-assignment/blob/master/documents/03_OpenAPI_Specification_V1.yaml
```
## Postman Collection
The complete API collection is added in the following git url, which you can directly import and test our APIs
```
https://github.com/arjunr1432/array-assignment/blob/master/documents/04_eRate.postman_collection.json
```
## How to install the application:
This is a simple spring-boot application with basic spring security.

Inorder to start with local, do the following steps.
1. Clone the repository to your local
```
 git clone git@github.com:arjunr1432/array-assignment.git

```
2. Install the application using following command.
```
mvn clean install
```
3. Test the application using following command.
```
mvn clean test
```
4. Start the application using following command.
```
mvn spring-boot:run
```
OR you can get the docker image from my public repository, and start the application as mentioned in 05_eRateAS_Application_TestingFlow.pdf
```
https://hub.docker.com/repository/docker/arjunr1432/erate-assignment
```

## Application testing and Debugging
Documentation on application setup and sample request response available in the below git url
```
https://github.com/arjunr1432/array-assignment/blob/master/documents/05_eRateAS_Application_TestingFlow.pdf
```

