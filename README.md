

1. Clone repository
2. Modify transfer.sh HOSTNAMES variable to include the the node where the program will execute
3. Run trasnfer.sh and digit the swarch user password
3. SSH to each node.
4. Run Gradle build
5. Execute the jar.

Make sure config.client is 

The -h flag of Subject.Proxy in config.client must be the hostname of the server. 


The key "Ice.Default.Host" in both config files must be the hostname of the node where the application will execute.