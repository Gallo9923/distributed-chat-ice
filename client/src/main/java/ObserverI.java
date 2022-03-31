import com.zeroc.Ice.Current;

import Demo.SubjectPrx;

public class ObserverI implements Demo.Observer{

    @Override
    public void update(SubjectPrx subject, Current current) {;
        // subject.ice_ping();
        String state = subject.getState();
        System.out.println(state);
    }

    @Override
    public void msg(String msg, Current current) {
        System.out.println(msg);
    }

}
