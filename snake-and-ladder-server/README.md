#**Snake & Ladders Game**

- This project is all about understanding and implementing BiDirectional Streams in RPC.
- The tutorial I saw was doing it in different way, in their version;
  - Rolling dice for both client and server.
  - Dice result was sending from client side.
- In my version, client calls rollTheDice, dice was randomly generated in server, server handles move of client, send the response of client movement, after that server rolls the dice for itself, moves and send another response about its movement.
- In response object, server sends details about client and server current position, last rolled dice, who is next, detailed information about movement and board. 
- My purpose here is making clients job easier. In frontend and mobile applications, we always desire to have less logic inside in our code. It makes our UI works better and faster. 
- Before try the app, please run `mvn clean install -DskipTests` command.
- App requires Java 18 to run.
- Server can be started from `src/main/java/com/cipek/server/SnakeAndLaddersGrpcServer` .
- After server started you can try the client side in `src/test/java/com/cipek/client/GameClientTest` by running the class or `test_snakeAndLaddersGame` method.
- After running the client you should see this kind of response.

On Server Side
```
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 1
SERVER has rolled dice and get 6. SERVER going step by step... SERVER's Current Position is : 6
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 2
SERVER has rolled dice and get 2. SERVER going step by step... SERVER's Current Position is : 8
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 3
SERVER has rolled dice and get 4. It looks like that SERVER found a LADDER! Flying must be feels like this...SERVER's Current Position is : 52
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 4
SERVER has rolled dice and get 3. SERVER going step by step... SERVER's Current Position is : 55
CLIENT has rolled dice and get 2. CLIENT going step by step... CLIENT's Current Position is : 6
SERVER has rolled dice and get 3. SERVER going step by step... SERVER's Current Position is : 58
CLIENT has rolled dice and get 6. It looks like that CLIENT found a LADDER! Flying must be feels like this...CLIENT's Current Position is : 52
SERVER has rolled dice and get 3. SERVER going step by step... SERVER's Current Position is : 61
CLIENT has rolled dice and get 3. CLIENT going step by step... CLIENT's Current Position is : 55
SERVER has rolled dice and get 6. SERVER going step by step... SERVER's Current Position is : 67
CLIENT has rolled dice and get 2. CLIENT going step by step... CLIENT's Current Position is : 57
SERVER has rolled dice and get 2. SERVER going step by step... SERVER's Current Position is : 69
CLIENT has rolled dice and get 3. CLIENT going step by step... CLIENT's Current Position is : 60
SERVER has rolled dice and get 4. So unlucky! SERVER found a SNAKE! Careful, don't brake your hips...SERVER's Current Position is : 17
CLIENT has rolled dice and get 3. CLIENT going step by step... CLIENT's Current Position is : 63
SERVER has rolled dice and get 4. SERVER going step by step... SERVER's Current Position is : 21
CLIENT has rolled dice and get 5. CLIENT going step by step... CLIENT's Current Position is : 68
SERVER has rolled dice and get 4. So unlucky! SERVER found a SNAKE! Careful, don't brake your hips...SERVER's Current Position is : 5
CLIENT has rolled dice and get 2. CLIENT going step by step... CLIENT's Current Position is : 70
SERVER has rolled dice and get 1. SERVER going step by step... SERVER's Current Position is : 6
CLIENT has rolled dice and get 6. It looks like that CLIENT found a LADDER! Flying must be feels like this...CLIENT's Current Position is : 97
SERVER has rolled dice and get 1. SERVER going step by step... SERVER's Current Position is : 7
CLIENT has rolled dice and get 6.  but its out of reach! CLIENT's Current Position is : 97
SERVER has rolled dice and get 4. SERVER going step by step... SERVER's Current Position is : 11
CLIENT has rolled dice and get 2. CLIENT going step by step... CLIENT's Current Position is : 99
SERVER has rolled dice and get 6. SERVER going step by step... SERVER's Current Position is : 17
CLIENT has rolled dice and get 2.  but its out of reach! CLIENT's Current Position is : 99
SERVER has rolled dice and get 1. SERVER going step by step... SERVER's Current Position is : 18
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 100
CLIENT WINS the GAME!
```

On Client Side
```
C:\Users\HAKANC\.jdks\openjdk-18.0.1.1\bin\java.exe -ea -Didea.test.cyclic.buffer.size=1048576 "-javaagent:C:\Users\HAKANC\AppData\Local\JetBrains\IntelliJ IDEA 2021.3.1\lib\idea_rt.jar=53468:C:\Users\HAKANC\AppData\Local\JetBrains\IntelliJ IDEA 2021.3.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Users\HAKANC\.m2\repository\org\junit\platform\junit-platform-launcher\1.4.2\junit-platform-launcher-1.4.2.jar;C:\Users\HAKANC\AppData\Local\JetBrains\IntelliJ IDEA 2021.3.1\lib\idea_rt.jar;C:\Users\HAKANC\AppData\Local\JetBrains\IntelliJ IDEA 2021.3.1\plugins\junit\lib\junit5-rt.jar;C:\Users\HAKANC\AppData\Local\JetBrains\IntelliJ IDEA 2021.3.1\plugins\junit\lib\junit-rt.jar;C:\Users\HAKANC\IdeaProjects\premature-grpc-microservices-with-java\snake-and-ladder-server\target\test-classes;C:\Users\HAKANC\IdeaProjects\premature-grpc-microservices-with-java\snake-and-ladder-server\target\classes;C:\Users\HAKANC\.m2\repository\io\grpc\grpc-netty-shaded\1.32.1\grpc-netty-shaded-1.32.1.jar;C:\Users\HAKANC\.m2\repository\io\grpc\grpc-core\1.32.1\grpc-core-1.32.1.jar;C:\Users\HAKANC\.m2\repository\com\google\code\gson\gson\2.8.6\gson-2.8.6.jar;C:\Users\HAKANC\.m2\repository\com\google\android\annotations\4.1.1.4\annotations-4.1.1.4.jar;C:\Users\HAKANC\.m2\repository\io\perfmark\perfmark-api\0.19.0\perfmark-api-0.19.0.jar;C:\Users\HAKANC\.m2\repository\io\grpc\grpc-protobuf\1.32.1\grpc-protobuf-1.32.1.jar;C:\Users\HAKANC\.m2\repository\io\grpc\grpc-api\1.32.1\grpc-api-1.32.1.jar;C:\Users\HAKANC\.m2\repository\io\grpc\grpc-context\1.32.1\grpc-context-1.32.1.jar;C:\Users\HAKANC\.m2\repository\com\google\code\findbugs\jsr305\3.0.2\jsr305-3.0.2.jar;C:\Users\HAKANC\.m2\repository\com\google\protobuf\protobuf-java\3.12.0\protobuf-java-3.12.0.jar;C:\Users\HAKANC\.m2\repository\com\google\api\grpc\proto-google-common-protos\1.17.0\proto-google-common-protos-1.17.0.jar;C:\Users\HAKANC\.m2\repository\io\grpc\grpc-protobuf-lite\1.32.1\grpc-protobuf-lite-1.32.1.jar;C:\Users\HAKANC\.m2\repository\com\google\guava\guava\29.0-android\guava-29.0-android.jar;C:\Users\HAKANC\.m2\repository\com\google\guava\failureaccess\1.0.1\failureaccess-1.0.1.jar;C:\Users\HAKANC\.m2\repository\com\google\guava\listenablefuture\9999.0-empty-to-avoid-conflict-with-guava\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;C:\Users\HAKANC\.m2\repository\org\checkerframework\checker-compat-qual\2.5.5\checker-compat-qual-2.5.5.jar;C:\Users\HAKANC\.m2\repository\com\google\j2objc\j2objc-annotations\1.3\j2objc-annotations-1.3.jar;C:\Users\HAKANC\.m2\repository\com\google\errorprone\error_prone_annotations\2.3.4\error_prone_annotations-2.3.4.jar;C:\Users\HAKANC\.m2\repository\org\codehaus\mojo\animal-sniffer-annotations\1.18\animal-sniffer-annotations-1.18.jar;C:\Users\HAKANC\.m2\repository\io\grpc\grpc-stub\1.32.1\grpc-stub-1.32.1.jar;C:\Users\HAKANC\.m2\repository\org\apache\tomcat\annotations-api\6.0.53\annotations-api-6.0.53.jar;C:\Users\HAKANC\.m2\repository\org\junit\jupiter\junit-jupiter-engine\5.4.2\junit-jupiter-engine-5.4.2.jar;C:\Users\HAKANC\.m2\repository\org\apiguardian\apiguardian-api\1.0.0\apiguardian-api-1.0.0.jar;C:\Users\HAKANC\.m2\repository\org\junit\platform\junit-platform-engine\1.4.2\junit-platform-engine-1.4.2.jar;C:\Users\HAKANC\.m2\repository\org\opentest4j\opentest4j\1.1.1\opentest4j-1.1.1.jar;C:\Users\HAKANC\.m2\repository\org\junit\platform\junit-platform-commons\1.4.2\junit-platform-commons-1.4.2.jar;C:\Users\HAKANC\.m2\repository\org\junit\jupiter\junit-jupiter-api\5.4.2\junit-jupiter-api-5.4.2.jar" com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit5 com.cipek.client.GameClientTest,test_snakeAndLaddersGame
snakes {
  type: SNAKE
  start: 73
  destination: 17
}
snakes {
  type: SNAKE
  start: 41
  destination: 33
}
snakes {
  type: SNAKE
  start: 34
  destination: 10
}
snakes {
  type: SNAKE
  start: 25
  destination: 5
}
snakes {
  type: SNAKE
  start: 79
  destination: 1
}
snakes {
  type: SNAKE
  start: 51
  destination: 29
}
snakes {
  type: SNAKE
  start: 48
  destination: 30
}
snakes {
  type: SNAKE
  start: 53
  destination: 37
}
snakes {
  type: SNAKE
  start: 40
  destination: 8
}
snakes {
  type: SNAKE
  start: 43
  destination: 15
}
ladders {
  type: LADDER
  start: 42
  destination: 74
}
ladders {
  type: LADDER
  start: 76
  destination: 97
}
ladders {
  type: LADDER
  start: 81
  destination: 93
}
ladders {
  type: LADDER
  start: 9
  destination: 57
}
ladders {
  type: LADDER
  start: 64
  destination: 72
}
ladders {
  type: LADDER
  start: 35
  destination: 80
}
ladders {
  type: LADDER
  start: 20
  destination: 88
}
ladders {
  type: LADDER
  start: 23
  destination: 46
}
ladders {
  type: LADDER
  start: 50
  destination: 84
}
ladders {
  type: LADDER
  start: 12
  destination: 52
}


Rolled dice : 1
Clients position : 1
Servers position : 0
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 1
Current Move : SERVER

!--------------!

Rolled dice : 6
Clients position : 1
Servers position : 6
SERVER has rolled dice and get 6. SERVER going step by step... SERVER's Current Position is : 6
Current Move : CLIENT

!--------------!

Rolled dice : 1
Clients position : 2
Servers position : 6
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 2
Current Move : SERVER

!--------------!

Rolled dice : 2
Clients position : 2
Servers position : 8
SERVER has rolled dice and get 2. SERVER going step by step... SERVER's Current Position is : 8
Current Move : CLIENT

!--------------!

Rolled dice : 1
Clients position : 3
Servers position : 8
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 3
Current Move : SERVER

!--------------!

Rolled dice : 4
Clients position : 3
Servers position : 52
SERVER has rolled dice and get 4. It looks like that SERVER found a LADDER! Flying must be feels like this...SERVER's Current Position is : 52
Current Move : CLIENT

!--------------!

Rolled dice : 1
Clients position : 4
Servers position : 52
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 4
Current Move : SERVER

!--------------!

Rolled dice : 3
Clients position : 4
Servers position : 55
SERVER has rolled dice and get 3. SERVER going step by step... SERVER's Current Position is : 55
Current Move : CLIENT

!--------------!

Rolled dice : 2
Clients position : 6
Servers position : 55
CLIENT has rolled dice and get 2. CLIENT going step by step... CLIENT's Current Position is : 6
Current Move : SERVER

!--------------!

Rolled dice : 3
Clients position : 6
Servers position : 58
SERVER has rolled dice and get 3. SERVER going step by step... SERVER's Current Position is : 58
Current Move : CLIENT

!--------------!

Rolled dice : 6
Clients position : 52
Servers position : 58
CLIENT has rolled dice and get 6. It looks like that CLIENT found a LADDER! Flying must be feels like this...CLIENT's Current Position is : 52
Current Move : SERVER

!--------------!

Rolled dice : 3
Clients position : 52
Servers position : 61
SERVER has rolled dice and get 3. SERVER going step by step... SERVER's Current Position is : 61
Current Move : CLIENT

!--------------!

Rolled dice : 3
Clients position : 55
Servers position : 61
CLIENT has rolled dice and get 3. CLIENT going step by step... CLIENT's Current Position is : 55
Current Move : SERVER

!--------------!

Rolled dice : 6
Clients position : 55
Servers position : 67
SERVER has rolled dice and get 6. SERVER going step by step... SERVER's Current Position is : 67
Current Move : CLIENT

!--------------!

Rolled dice : 2
Clients position : 57
Servers position : 67
CLIENT has rolled dice and get 2. CLIENT going step by step... CLIENT's Current Position is : 57
Current Move : SERVER

!--------------!

Rolled dice : 2
Clients position : 57
Servers position : 69
SERVER has rolled dice and get 2. SERVER going step by step... SERVER's Current Position is : 69
Current Move : CLIENT

!--------------!

Rolled dice : 3
Clients position : 60
Servers position : 69
CLIENT has rolled dice and get 3. CLIENT going step by step... CLIENT's Current Position is : 60
Current Move : SERVER

!--------------!

Rolled dice : 4
Clients position : 60
Servers position : 17
SERVER has rolled dice and get 4. So unlucky! SERVER found a SNAKE! Careful, don't brake your hips...SERVER's Current Position is : 17
Current Move : CLIENT

!--------------!

Rolled dice : 3
Clients position : 63
Servers position : 17
CLIENT has rolled dice and get 3. CLIENT going step by step... CLIENT's Current Position is : 63
Current Move : SERVER

!--------------!

Rolled dice : 4
Clients position : 63
Servers position : 21
SERVER has rolled dice and get 4. SERVER going step by step... SERVER's Current Position is : 21
Current Move : CLIENT

!--------------!

Rolled dice : 5
Clients position : 68
Servers position : 21
CLIENT has rolled dice and get 5. CLIENT going step by step... CLIENT's Current Position is : 68
Current Move : SERVER

!--------------!

Rolled dice : 4
Clients position : 68
Servers position : 5
SERVER has rolled dice and get 4. So unlucky! SERVER found a SNAKE! Careful, don't brake your hips...SERVER's Current Position is : 5
Current Move : CLIENT

!--------------!

Rolled dice : 2
Clients position : 70
Servers position : 5
CLIENT has rolled dice and get 2. CLIENT going step by step... CLIENT's Current Position is : 70
Current Move : SERVER

!--------------!

Rolled dice : 1
Clients position : 70
Servers position : 6
SERVER has rolled dice and get 1. SERVER going step by step... SERVER's Current Position is : 6
Current Move : CLIENT

!--------------!

Rolled dice : 6
Clients position : 97
Servers position : 6
CLIENT has rolled dice and get 6. It looks like that CLIENT found a LADDER! Flying must be feels like this...CLIENT's Current Position is : 97
Current Move : SERVER

!--------------!

Rolled dice : 1
Clients position : 97
Servers position : 7
SERVER has rolled dice and get 1. SERVER going step by step... SERVER's Current Position is : 7
Current Move : CLIENT

!--------------!

Rolled dice : 6
Clients position : 97
Servers position : 7
CLIENT has rolled dice and get 6.  but its out of reach! CLIENT's Current Position is : 97
Current Move : SERVER

!--------------!

Rolled dice : 4
Clients position : 97
Servers position : 11
SERVER has rolled dice and get 4. SERVER going step by step... SERVER's Current Position is : 11
Current Move : CLIENT

!--------------!

Rolled dice : 2
Clients position : 99
Servers position : 11
CLIENT has rolled dice and get 2. CLIENT going step by step... CLIENT's Current Position is : 99
Current Move : SERVER

!--------------!

Rolled dice : 6
Clients position : 99
Servers position : 17
SERVER has rolled dice and get 6. SERVER going step by step... SERVER's Current Position is : 17
Current Move : CLIENT

!--------------!

Rolled dice : 2
Clients position : 99
Servers position : 17
CLIENT has rolled dice and get 2.  but its out of reach! CLIENT's Current Position is : 99
Current Move : SERVER

!--------------!

Rolled dice : 1
Clients position : 99
Servers position : 18
SERVER has rolled dice and get 1. SERVER going step by step... SERVER's Current Position is : 18
Current Move : CLIENT

!--------------!

Rolled dice : 1
Clients position : 100
Servers position : 18
CLIENT has rolled dice and get 1. CLIENT going step by step... CLIENT's Current Position is : 100
CLIENT WINS the GAME!

Process finished with exit code 0
```