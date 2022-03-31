public class Server
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.server",extraArgs))
        {
            
            
            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                for(String v:extraArgs){
                    System.out.println(v);
                }
            }
            
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Server");
            
            String hostname = communicator.getProperties().getProperty("Ice.Default.Host");
            SubjectI subjectI = new SubjectI(null, hostname);

            com.zeroc.Ice.ObjectPrx prx = adapter.add(subjectI, com.zeroc.Ice.Util.stringToIdentity("subject"));
            adapter.activate();

            Demo.SubjectPrx callback =  Demo.SubjectPrx.uncheckedCast(
                prx);
            if(callback == null)
            {
                throw new Error("Invalid Callback proxy");
            }

            SubjectI.proxy=callback;
            
            System.out.println("Server running...");
            communicator.waitForShutdown();
        }
    }
}