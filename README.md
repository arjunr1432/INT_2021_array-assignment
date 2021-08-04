# eRate AS Coding Assignment

## Intro:
We are developing a simple HTTP-based web site with REST API that maintains an integer array, initially empty, and features the following functionality:

• Displays the array content (HTTP) or returns the current array (API)

• Provides functionality to add an integer to the array

• Provides functionality to check whether the array can be split in two, without reordering the elements, so that the sum of the two resulting arrays is equal. For example, array [4, 1, 1, 1, 1] can be split this way (in [4] and [1, 1, 1, 1]), while [1, 1, 5, 3] cannot be split in two arrays with equal sums.

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


