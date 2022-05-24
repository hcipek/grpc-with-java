# **Project Protobuf**

**Required Dependencies**

- io.grpc / grpc.protobuf (For gRPC libraries i guess)
- org.apache.tomcat / annotations-api / scope : provided (currently don't know but tomcat name gives me idea, probably helps to create protos at runtime)

**Required Plugins**

1. protobuf-maven-plugin

- protoSourceRoot declares path of proto files
- protocArtifact setting the executable

2. Executing at compile time

**Instructions**

- Create proto folder under the *src/main* (not mandatory but preferred way)
- Protobuf Plugin of Intellij(its already installed on mine but lower version may require installing)
- All proto files must be under *src/main/proto(or whatever path chose in pom.xml/protobuf-maven-plugin)*
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
- After that same operations ran 5.000.000 times, this time JSON takes 3018ms to finish, Proto operations takes 330ms.
- After that, this process repeated 5 times in a for loop, to see is there any improvement. For JSON minimum time requirement was 1955ms while Proto required minimum 268ms.
- This experiment shows us Proto is way lighter than JSON, we can understand why gRPC is more effective and faster way to communicate between servers.

**Comments on Proto**

- Similar to Java language, it supports
  - // for single line comment
  - /* */ for multiline comment

**Generate Different Files Than Java classes from .proto**

- We can manually trigger the protoc-3.6.1-'os_name_here'.exe to create file for another language.(for windows its _**protoc-3.6.1-windows-x86_64.exe**_)
- Open the terminal and run this command :

  ```
  .\protobuf\target\protoc-plugins\protoc-3.6.1-windows-x86_64.exe --LANGUAGE_OPT_OUT=output_path source_path

  ```

  - LANGUAGE_OPT_OUT must be changed with the desired language that you want to generate from proto file.
  - Possible Options are;

    - --cpp_out=OUT_DIR           Generate C++ header and source.
    - --csharp_out=OUT_DIR        Generate C# source file.
    - --java_out=OUT_DIR          Generate Java source file.
    - --js_out=OUT_DIR            Generate JavaScript source.
    - --objc_out=OUT_DIR          Generate Objective-C header and source.
    - --php_out=OUT_DIR           Generate PHP source file.
    - --python_out=OUT_DIR        Generate Python source file.
    - --ruby_out=OUT_DIR          Generate Ruby source file.
- For example, I ran this command to create .js file from the .proto file

  ```
  .\protobuf\target\protoc-plugins\protoc-3.6.1-windows-x86_64.exe --js_out=.\protobuf\src\main\proto\ .\protobuf\src\main\proto\person.proto
  ```
- Also, you can download compiled protoc.exe from ***https://github.com/protocolbuffers/protobuf/releases*** .I'm using Windows 10 at this project, so I downloaded protoc-version-win64.zip
- Put the protoc.exe file which is under /bin folder of the downloaded zip into the path that you want to execute protoc command.
- So our previous example will turn into `\your_path\protoc -js_out=.\protobuf\src\main\proto\ .\protobuf\src\main\proto\person.proto`

**Types in Proto**


| Java Type | Proto Type |
| ----------- | ------------ |
| int       | int32      |
| long      | int64      |
| double    | double     |
| float     | float      |
| String    | string     |
| byte[]    | bytes      |

**Composition**

- We can add another messages into our Proto file,which is person.proto, like person car and person address.
- After we add these messages, we can use them as Data Structures in our original Person message and declare fields of these messages.
- Example can be seen in CompositionDemo.java and person.proto files.

**Collections**


| Java Type  | Proto Type |
| ------------ | ------------ |
| Collection | repeated   |
| Map        | map        |

- repeated accepts any class that child of Collection class.
- map accepts any class that child of Collection class.
- If you use map, proto generator provides us some useful methods, such as get< PropertyName>Count, get< PropertyName>OrDefault and get< PropertyName>OrThrow

**Enums**

- Value that equal 0 is the default value. So be careful about which property you want to appoint as default.
- No type declarations, similar to Java.

**Default Values**

- If you not assign any values to proto object, it would be language defined values. These are;


| Proto Type | Default Value     |
| ------------ | ------------------- |
| numerics   | 0                 |
| bool       | false             |
| string     | ""                |
| enum       | first value       |
| repeated   | empty list        |
| map        | wrapper/empty map |

**Packages and Importing**

- So far I created all my proto messages under person.proto file. But in a better design it will be more meaningful if you declare a proto file for each message.
- So person.proto file is now only responsible for person message. Car, Address and Dealer messages has their own proto files, Car and Address is under common package.
- To use this messages in person proto, first we need to import the message that we want to use.
- Secondly we need to change type declarations, we need to add package name before the message type name.

**OneOf**

- Basically required one of declared objects, not all of more than one of them.
- Example can be seen at credentials.proto and OneOfDemo.java files.

**Wrappers in Proto**

- Previous proto types we see are the primitive types. Proto wrapper types can be accesses by importing google/protobuf/wrapper.proto .
- After that we can use wrapper types with;
  - google.protobuf.< WrapperName> propertyName = number;

**Byte Array Analysis**

* I created same object for both JSON and Protobuf. Then converted them into byte arrays.
* When I analysed the arrays, I saw that JSON Byte array is made by ANSI Characters. Every character inside of JSON String turned into ANSI Counterparts. Because of that, JSON byte array length always will be equal to JSON String(without blanks) length.
* In protobuf, it seems like all primitives has their own unique values. And they are ordered by their indexes, which are given in .proto files. This requires a proof, but I believe the formula is PRIMITIVE_BASE_VALUE+(INDEX*8). If we are using int32 primitive, value comes after this code. In string primitive type, length of the string comes then characters of string comes in order as their ANSI Code values. I believe reason of length is due to avoid unnecessary parsing or converting ANSI to actual values. So every string value requires only 2+length of string elements and every int32 requires just two elements inside of byte array. Even for best case in JSON, it can't match this. Most of the performance looks like coming from there.
* Recommended approach is using values between 1-15 for commonly used fields and 16-2047 for uncommonly-rarely used fields. Because values between 1 to 15 are takes only 1 bytes and 16 to 2047 take 2 bytes.

**About Proto Field Numbers**

- Each field must have a unique number.
- As mentioned before use 1 to 15 for commonly used fields and 16 to 2047 for not so commonly used fields.
- 1 is smallest number for fields.
- There is possible 2^29-1 numbers available.
- 19000-19999 are reserved, you cannot use them.
- Do not change field numbers after you shared the proto com.cipek.model with others.

**Versioning**

- When 2 services communicates each with proto file, they didn't know about field names, so they are only care about field numbers. Sometimes one com.cipek.service could change their proto file. They could change field name or add new fields.
- When this happen, a developer should be careful about changes he/she made. For example if a field number assigned and after a while it becomes deprecated, developer should not be remove or comment this change. Instead of that, field number and field name should be reserved, so if another developer comes for another change, he/she can understand this number could have some other value and to avoid from any conflicts or wrong assignments, developer going to use another field number to use for new feature. So for situations like this, developer must use the following code lines.
  - reserved < fieldNumber>/< fieldNumber to fieldNumber>;
  - reserved < "fieldName">/< "fieldName", ...., "fieldName">;

**Summary**

- Protobuf is Interface Description Language for API.
- Language and Platform neutral.
- Serialize/Deserialize structured data
- Very fast and Optimized for interservices communications
- Also supports many popular languages
- 1-15 field numbers should be used for commonly used fields.
- Reordering field numbers is bad.
- Add/Remove fields are okay, as long as you reserved the old field number.
- Renaming fields are fine but it could become confusing after a while.
- Should be careful about type conversions. Similar rules that most of the programming languages have are applying to proto as well.
