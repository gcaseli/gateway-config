# gateway-config
Spring Gateway using dynamic configuration

To upload client and config applications, use the gradle clean bootRun command at the root of the configuration-service and gateway projects

# To see all routes
http://localhost:9901/actuator/gateway/routes/

# Test endpoint
curl http://localhost:9901/get

# To update the client without a restart
### call http post to Actuator of aplication client
curl -X POST localhost:9901/actuator/refresh -H "Content-Type: application/json"
