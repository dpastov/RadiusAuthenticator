# TinyRadiusClient
A simple client based on tinyradius

# Build
mvn package

# Properties
file name: **config.properties**

file content:
```
# RADIUS Server Configuration
radius.host=localhost
radius.sharedSecret=sharedSecret123

# User Authentication Credentials
radius.username=John
radius.token=token123
```

# Run
java -jar TinyRadiusClient-1.0.0.jar

# Sucess run
```
Apr 29, 2024 4:35:49 PM org.tinyradius.util.RadiusClient authenticate
INFO: send Access-Request packet: Access-Request, ID 1
User-Name: John
```
