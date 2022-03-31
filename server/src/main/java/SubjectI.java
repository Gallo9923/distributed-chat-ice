import java.util.ArrayList;

import com.zeroc.Ice.Current;

import Demo.ObserverPrx;
import Demo.SubjectPrx;

public class SubjectI implements Demo.Subject{

    public static final String BROADCAST = "BC";
    public static final String MSG_PREFIX = "recibido desde ";

    private ArrayList<ObserverPrx> observers;

    private String state;

    public static SubjectPrx proxy;

    private String hostname;

    public SubjectI(SubjectPrx proxy, String hostname){
        
        this.observers = new ArrayList<ObserverPrx>();

        this.state = null;
        
        SubjectI.proxy = proxy;

        this.hostname = hostname;
    }

    private void notifyObservers(){
        
        for (ObserverPrx observer : this.observers){
            try {
                observer.update(SubjectI.proxy);
            }catch(Exception e){ // TODO: Find the correct exception to catch
                this.observers.remove(observer);
            }
            
        }
    }

    @Override
    public void attach(ObserverPrx observer, Current current) {
        
        if (!this.observers.contains(observer)){
            this.observers.add(observer);

            System.out.println("ATTACHED: " + observer.toString());
        }
    }

    @Override
    public void detach(ObserverPrx observer, Current current) {
        
        boolean removed = this.observers.remove(observer);
        if (removed){
            System.out.println("DETACHED: " + observer.toString());
        }
    }


    @Override
    public String getState(Current current) {
        return this.state;
    }

    @Override
    public void msg(String msg, Current current) {
        
        Thread t = new Thread() {
            public void run() {
                processMessage(msg);
            }
        };
        t.start();
    
    }

    private void processMessage(String msg){
        String msgToSend = MSG_PREFIX + this.hostname + ": " + msg;
        
        if (msg.contains(BROADCAST)){
            this.state = msgToSend;
            this.notifyObservers();    
        }

        System.out.println(msg);
    }
}
