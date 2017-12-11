# ClubAppServer

How to start the ClubAppServer application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/ClubAppServer-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

How to start the ClubAppServer application in Eclipse
---

1. Make sure JDK & Maven is working
2. ClubAppServer -> Run As -> Maven build...
3. Enter `package` in *Goals*
4. Build Success
5. ClubAppServer -> Run As -> Java Application
6. Select *ClubAppServerApplication - de.hummelflug.clubapp.server*
7. ClubAppServer -> Properties -> Run/Debug Settings -> Edit *ClubAppServerApplication*
8. Switch to tab *Arguments*: enter `server config.yml` in *Program arguments*
9. Switch to tab *Environment*: configure database connection (set DBUSER, DBPW, DBURL) and configure path where images should be stored (set IMAGE_PATH) 
10. Run again
11. Server is running

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`