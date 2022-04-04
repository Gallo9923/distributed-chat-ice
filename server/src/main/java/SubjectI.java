import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import com.zeroc.Ice.Current;

import Demo.Message;
import Demo.ObserverPrx;
import Demo.SubjectPrx;

public class SubjectI implements Demo.Subject{

    private ArrayList<ObserverPrx> observers;

    private Message state;

    public static SubjectPrx proxy;

    private String hostname;

    private Semaphore sem;

    public SubjectI(SubjectPrx proxy, String hostname){
        
        this.sem = new Semaphore(1, true);

        this.observers = new ArrayList<ObserverPrx>();

        this.state = null;
        
        SubjectI.proxy = proxy;

        this.hostname = hostname;
    }

    private void notifyObservers(){
        try {
            this.sem.acquire();
            doNotifyObservers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.sem.release();
        }
    }

    private void doNotifyObservers(){
        ArrayList<ObserverPrx> observersToRemove = new ArrayList<ObserverPrx>();
        for (ObserverPrx observer : this.observers){
            try {
                observer.update(SubjectI.proxy);
            // TODO: Find the correct exception to catch
            }catch(Exception e){ 
                observersToRemove.add(observer);
            }
        }
        
        for (ObserverPrx observer : observersToRemove){
            doDetach(observer);
        }

    }

    @Override
    public void attach(ObserverPrx observer, Current current) {
        try {
            this.sem.acquire();
            doAttach(observer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.sem.release();
        }
    }

    private void doAttach(ObserverPrx observer){

        if (!this.observers.contains(observer)){
            this.observers.add(observer);

            System.out.println("ATTACHED: " + observer.toString());
        }

    }

    @Override
    public void detach(ObserverPrx observer, Current current) {
        try {
            this.sem.acquire();
            doDetach(observer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.sem.release();
        }
    }

    private void doDetach(ObserverPrx observer){
        boolean removed = this.observers.remove(observer);
        if (removed){
            System.out.println("DETACHED: " + observer.toString());
        }
    }

    @Override
    public Message getState(Current current) {
        return this.state;
    }

    private void processMessage(ObserverPrx observer, Message msg){
    
        if (msg.broadcast){
            this.state = msg;
            this.notifyObservers();    
        }else{
            Message msgToSend = new Message(msg.message, this.hostname, false);
            observer.msg(msgToSend);
        }
        
        System.out.println(msg.source + ": " + msg.message);
    }

    @Override
    public void msg(ObserverPrx observer, Message msg, Current current) {
        
        Thread t = new Thread() {
            public void run() {
                processMessage(observer, msg);
            }
        };
        t.start();
        
    }
}
