# Fetch Pictures

The Fetch Pictures is an app to get information and images from Shutter Stock API (https://api-reference.shutterstock.com/).

The main idea here is consume the endpoint (v2/images/search) to get different images based on query (e.g: search images from kite, computers and so).


## Design and Architecture

The app was made thinking about how it could be easy to maintain and how can be easy to add more features. For that reason, it was made using MvvM and Clean Architecture. 

There are a lot of interfaces and classes which were classified and separated into packages. The main packages are:
- environment (everything here is about Android ecosystem - Activities, Storage, Adapters, Image Loaders and so)
- viewmodels (ViewModel Classes)
- interactors (holds all use cases from the application)
- model (classes used by application - the entities)

Based on Clean Architecture, classes inside packages / layers could have more knoledge (dependency) about other classes. For instance, one class that belongs to model package does not know nothing about one class inside interactors package. 

This architecture helps a lot since we could test classes with less effort and the components could be more decouple from each other.

## Problem to Solve

### _Data Consuming_   
One of main problems is the data consuming. The application needs to fetch information from one API (Shutter Stock) and after that download and show the images into the screen. To avoid unecessary traffic, the application has a cache for the data which is provided by Shutter Stock API. 

## Used Libraries
Here some libraries used to build and test this app:

- Dagger 2 (Dependency Injection)
- GSON (Json Serialization / Deserialization)
- Glide (Image Loader)
- Retrofit (Consume Rest APIs)
- OkHttp (Http Connection)
- Coroutines (Easy way to implement async operation)
- Room (An ORM for Android) 
- Constraint Layout
- JUnit (Java Unit testing)
- mockk (For tests purpose - Mock objects for testing)
- okhttp3-mock web server (A server mock which could be used in junit tests)

## Pull, Build, Test & Run

### Pull the code
First of all, you will need to download the code:
```
git clone https://github.com/ericbbraga/FetchPictures
```

### Requirements
Second, the Shutter Stock Api requires a __API Token__ to work. For that reason is necessary create an account and download the token content. Please create a file with the token content into the file __shutterstock.token__ on root of directory project.

### Build Application

After that it is possible to build the application on command line:

```
./gradlew app:assembleDebug
```

The application _app-debug.apk_ will be inside directory __app/build/outputs/apk/debug__

or using the Android Studio

### Running Tests
It is possible to run unit tests using the following command:

```
./gradlew app:testDebug
```

### Running Application
```
./gradlew app:installDebug
```

## Future Work
- Improve the Image Loader (Glide) to get more smooth effect
- Implements UI Tests (using espresso)
- Implements some junit tests