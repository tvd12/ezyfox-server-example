using System;
public class SocketProxy {

    private static SocketProxy _instance;

    public static SocketProxy getInstance()
    {
        if (_instance == null)
        {
            _instance = new SocketProxy();
        }

        return _instance;
    }
}
