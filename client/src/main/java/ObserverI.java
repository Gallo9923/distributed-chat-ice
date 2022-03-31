import com.zeroc.Ice.Current;

import Demo.SubjectPrx;
import Demo.Message;

public class ObserverI implements Demo.Observer{

    @Override
    public void update(SubjectPrx subject, Current current) {;
        // subject.ice_ping();
        Message state = subject.getState();
        System.out.println(state.source + ": " + state.message);
    }

    @Override
    public void msg(Message msg, Current current) {
        System.out.println(msg.source + ": " + msg.message);
    }

}
