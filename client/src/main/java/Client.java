import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import Demo.Message;
public class Client
{   
    
    public static final String BROADCAST = "BC ";
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.client",extraArgs))
        {
            //com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");
            Demo.SubjectPrx server = Demo.SubjectPrx.checkedCast(
                communicator.propertyToProxy("Subject.Proxy")).ice_twoway().ice_secure(false);
            if(server == null)
            {
                throw new Error("Invalid proxy");
            }
            
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Client");
            adapter.add(new ObserverI(), com.zeroc.Ice.Util.stringToIdentity("observer"));
            adapter.activate();

            Demo.ObserverPrx observer =  Demo.ObserverPrx.uncheckedCast(
                adapter.createProxy(com.zeroc.Ice.Util.stringToIdentity("observer")));
            if(observer == null)
            {
                throw new Error("Invalid Callback proxy");
            }
            
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    server.detach(observer);
                }
            }, "Shutdown-thread"));
            
            String hostname = communicator.getProperties().getProperty("Ice.Default.Host");
            run(server, observer, hostname);
        }
    }

    private static void printMenu(){
        System.out.println("Enter message or type 'quit' to exit");

    }

    public static String EXIT_STRING = "quit";

    private static void run(Demo.SubjectPrx server, Demo.ObserverPrx observer, String hostname){
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
            server.attach(observer);

            do{
                printMenu();
                String line;
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    continue;
                }
                
                if(line != null && line.equalsIgnoreCase(EXIT_STRING)){
                    System.out.println("Exiting...");
                    break;
                }

                Message msg = new Message();
                msg.source = hostname;

                if (line.startsWith(BROADCAST, 0)){
                    msg.broadcast = true;
                    msg.message = line.substring(BROADCAST.length(), line.length());
                }else{
                    msg.message = line; 
                }
                
                server.msg(observer, msg);
            
            } while(true);

        } catch(Exception e){
            System.out.println("Server could not be reached!");
            System.out.println("Closing...");
            // TODO: Remove this print
            System.out.println(e.getStackTrace());
        } finally {
            server.detach(observer);
        }
    }
}