module Demo
{   
    
    interface Subject;

    // Cliente    
    interface Observer
    {
        // void update(Subject* subject);
        //void msg(string msg);
        void update(Subject* subject);
        void msg(string msg);
    }
    
    // Servidor
    interface Subject
    {
        void attach(Observer* observer);
        void detach(Observer* observer);
        //void getState(Observer* observer);
        string getState();
        //void receiveMsg(Observer* observer, string msg);
        void msg(string msg);
    }
    
}