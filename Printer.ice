module Demo
{   
    
    class Message
    {
        string message;
        string source;
        bool broadcast;
    }

    interface Subject;

    // Cliente    
    interface Observer
    {
        void update(Subject* subject);
        void msg(Message msg);
    }
    
    // Servidor
    interface Subject
    {
        void attach(Observer* observer);
        void detach(Observer* observer);
        Message getState();
        void msg(Observer* observer, Message msg);
    }
    
}