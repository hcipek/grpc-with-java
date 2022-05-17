# premature-microservices-with-java

This repository is all about a playground to Learn gRPC communication with Microservices with are build in Java.

Based on gRPC Udemy Course

# **Project Protobuf**

**Required Dependencies**

- io.grpc / grpc.protobuf (For gRPC libraries i guess)
- org.apache.tomcat / annotations-api / scope : provided (currently dont know but tomcat name gives me idea, probably helps to create protos at runtime)

**Required Plugins**

1. protobuf-maven-plugin

- protoSourceRoot declares path of proto files
- protocArtifact setting the executable

2. Executing at compile time

**Instructions**

- Create proto folder under the *src/main* (not mandatory but prefered way)
- Protobuf Plugin of Intellij(its already installed on mine but lower version may required to install)
- All proto files must be under *src/main/proto(or whatever path choosed in pom.xml/protobuf-maven-plugin)*
- After .proto files created under declared path, you need to maven compile(`mvn clean install -DskipTests` also do the trick)
- This will create class files for .proto files under *target/generated-sources/protobuf/java*

**About Proto Options**

- option java_multiple_files helps us with creation of multiple classes instead of one nested class.
- option java_package declares the package path to our proto class to helps us to import in our java files.

**About Using Generated Classes**

- After compile finished the classes are going to be generated.
- If your IDE couldn't inspect this classes, that means your target path for your proto files are not marked as source root.
- To solve this, Mark the Directory as Source Root, you can find the directory under target/generated-sources/project-name(right click java folder and mark it as a source folder)
- Generated class constructors are PRIVATE, so you need to use the Builder pattern ,which is given by all generated classes, with static newBuilder() method.

**Methods**

- Proto Generated Class Methods used at com.cipek.protobuf.PersonDemo class in main method.
- In Builder, you will have a setter method for each of your fields.
- After setting every field you wanted, run the _**build()**_ method to construct your object.
- The object has _**equals**_ method as usual as the all Java objects that extends Object class, but it works through the field values, compare them all.
- _**Equals**_ method is direct compare, so it's case-sensitive for Strings and also type sensitive, so int 1 vs long 1 will be fail, probably.
- **_toByteArray_** method converts proto Java file into _**byte array**_.
- _**parseFrom**_ method creates proto Java file from given parameter data type, it also accepts byte array(how convenient)

**Protobuf vs JSON comparison**

- In PerformanceTest class, there is a comparison between JSON and Proto. This test based on how long it takes to convert JSON/Proto objects into byte array and parsing them from the byte array we just converted. This operations runned 1.000.000 times at first, JSON Conversion cycle takes 1255ms and Proto conversions takes 133ms.
- After that same operations runned 5.000.000 times, this time JSON takes 3018ms to finish, Proto operations takes 330ms.
- After that, this process repeated 5 times in a for loop, to see is there any improvement. For JSON minimum time requirement was 1955ms while Proto required minimum 268ms.
- This experiment shows us Proto is way more lighter than JSON and we can understand why gRPC is more effective and faster way to communicate between servers.
