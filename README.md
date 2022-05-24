# premature-microservices-with-java

This repository is all about a playground to Learn gRPC communication with Microservices with are build in Java.

Based on gRPC Udemy Course

# **Project Protobuf**

- In this module, I learned how to create and use proto files.
- Details can be seen under 'protobuf' modules README.md file.

# **Project Grpc-Operations**

- In this module, I learned and used Unary, Server-Side Streaming, Client-Side Streaming and Bi-Directional Streaming RPC services.
- Details can be seen under 'grpc-operations' modules README.md file.

# **Project Snake & Ladders Game**

- In this module we try to create a game with BiDirectional RPC calls. It was little challenging but also good exercise for using and controlling BiDirectional Streams.
- Details can be seen under 'snake-and-ladder-server' modules README.md file.

# **Project Load Balancing in GRPC**

- In this module, I explore the concepts of Server-side and client-side load balancing in gRPC.
- Hopefully I will add detailed README.md later.

# **Deadlines in gRPC**

- When client sends a request, client could set a deadline, which is some kind of timeout.
- When that happens, client doesn't accept any more responses after deadline reached.
- This could cause some problems, because server could send responses to nowhere. 
- When these responses require a lot of time to build, that means server could waste its resources for nothing.
- Because of that, in case of deadline we always check that client is still waiting for us or not.
- In Java, this could be achieved with this code line;
        `Context.current().isCancelled();`
- When this method returns true, we should stop streaming or in unary calls, it depends on the case but, we should check it before execute some costly process, like getting tons of data from a database or 3rd party service.

# **Interceptors In gRPC**

- When we call the services, sometimes we want to set some options before sending requests. In gRPC, it could be deadlines, maybe we want a generic deadline for every request or we just simply don't want to add deadline to each call.
- Interceptors could work in situations like this.
- There is an example of Interceptor implementation for deadline with Java below.
```
public class CustomInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        return channel.newCall(methodDescriptor, callOptions.withDeadline(Deadline.after(10, TimeUnit.SECONDS)));
    }
}
```
- Then you need to give it to the ChannelBuilder with `intercept(CustomInterceptor)`;
```
ManagedChannel managedChannel = ManagedChannelBuilder
                .forTarget("http://target")
                .intercept(new CustomInterceptor())
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext().build();
```
- There several callOptions, such as;
```
    callOptions.withAuthority();
    callOptions.withDeadline();
    callOptions.withCallCredentials();
    callOptions.withCompression();
    callOptions.withOption();
    callOptions.withDeadlineAfter();
    callOptions.withExecutor();
    callOptions.withMaxInboundMessageSize();
    callOptions.withMaxOutboundMessageSize();
    callOptions.withoutWaitForReady();
    callOptions.withStreamTracerFactory();
    callOptions.withWaitForReady();
```

**gRPC Metadata (Client-Side)**

- It's similar to headers, we can add our options to the Metadata with interceptors.
- Metadata is similar to Map structures, having keys and values as Entries.
- We can create our Metadata data object in Java like this;
```
Metadata metadata = new Metadata();
metadata.put(Metadata.Key.of("key-name", Marshaller), "value");
```
- And add it to the channel object with interceptors like this;

```
...before channel creation opt...
.intercept(MetadataUtils.newAttachHeadersInterceptor(yourMetadataObject)
...after channel creation opt...
```

**gRPC Metadata (Server-Side)**

- In server side there is similar process. 
- In server-side interceptors implemented with ServerInterceptor interface.
- We also need to override `<ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> var1, Metadata var2, ServerCallHandler<ReqT, RespT> var3);` method.
- As we can see, we receive ServerCall, Metadata and ServerCallHandler.
- If we need to check something in metadata before resume the process(for ex. authentication), we can look at the Metadata object and check is our object is there and is our object value is correct or not.
- Here is an example of ServerInterceptor with token validation in Java.
```
public class CustomServerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String token = metadata.get(ServerConstants.TOKEN);
        if (validate(token))
            return serverCallHandler.startCall(serverCall, metadata);
        Status status = Status.UNAUTHENTICATED.withDescription("Invalid/Expired Token");
        serverCall.close(status, metadata);
        return new ServerCall.Listener<>() {};
    }

    private boolean validate (String token) {
        return !StringUtil.isNullOrEmpty(token) && token.equals("client-ultimate-secret-token");
    }
}
```
- After we created our Interceptor, we will past it to ServerBuilder object with `intercept` method.
```
        Server server = ServerBuilder.forPort(aPort)
                .intercept(new CustomServerInterceptor())
                .addService(new CustomService())
                .build();
```

**Call Credentials With Interceptors**

- In beginning of Interceptor, there was a list of methods that we can use with our custom Interceptors.
- One of them is Call Credentials, and it could be useful when we are try to pass a session-token.
- It's a specific way to add credentials to Metadata. I think it's similar process like Authentication headers in REST. In REST request you can add authentication header with its method or manually naming header key as Authentication.
- First we create the CallCredential class;
```
public class SessionTokenCallCredential extends CallCredentials {

    private String jwtToken;

    public SessionTokenCallCredential(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(
                () -> {
                    Metadata metadata = new Metadata();
                    metadata.put(ClientConstants.SESSION_TOKEN_KEY, jwtToken);
                    metadataApplier.apply(metadata);
//                    metadataApplier.fail(Status.UNAUTHENTICATED);
                }
        );
    }

    @Override
    public void thisUsesUnstableApi() {
        //TODO : Later
    }
}                
```
- Then we create our Interceptor to use this credential. (It's optional though. You can still use it before every request.)
```
public class CustomCallCredentialInterceptor implements ClientInterceptor {

    private final String token;

    public CustomCallCredentialInterceptor(String token) {
        this.token = token;
    }
    
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        return channel.newCall(methodDescriptor, callOptions.withCallCredentials(new SessionTokenCallCredential(this.token)));
    }
}
```
- And last, we give it to our Channel with intercept() method.
```
        String jwtToken = authenticationService.authenticate(userName, password);

        ManagedChannel managedChannel = ManagedChannelBuilder
                ....
                .intercept(new CustomCallCredentialInterceptor(jwtToken))
                ....build();
```

