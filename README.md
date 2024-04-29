# TinyRadiusClient
A simple client based on tinyradius

# Build
mvn package

# Properties
file name: config.properties

file contnet:
radius.host=127.0.0.1:20001
radius.potr=20001
radius.sharedSecret=mySharedSecret123
radius.username=john doe
radius.validToken=myValidToken123
radius.invalidToken=myInvalidToken456

# Run
java -jar TinyRadiusClient-1.0.0.jar

# Sucess run
