## Authors:
- Christian Gallo Pelaez
- Juan Fernando Angulo

# Instructions to run:

**1.** Clone repository.
```bash
git clone https://github.com/Gallo9923/distributed-chat-ice
````
**2.** Modify transfer.sh HOSTNAMES variable to include the nodes (hostnames/IP addresses) where the program will execute.
**3.** Run trasnfer.sh and digit the swarch user password.
```bash
sh transfer.sh
````
or
```bash
bash transfer.sh
````
**4.** Connect via SSH to each node.
```bash
ssh user@hostname
````
Make sure config files are well configured:

- The -h flag of Subject.Proxy in config.client must be the hostname or IP address of the server. 
- The key "Ice.Default.Host" in both config files must be the hostname or IP address of the node where the application will execute.

**5.** Run Gradle build.
```bash
gradle build
````
**6.** Execute the respectives .jar files in each node.
```bash
java -jar file_name.jar
````
