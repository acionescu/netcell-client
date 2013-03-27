package ro.zg.netcell.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import ro.zg.util.net.nio.NioClient;

public abstract class NetcellSocketClient {
    protected NioClient engineClient;
    private int port;
    private String host;
    private boolean connected;

    public NetcellSocketClient(String host, int port) {
	super();
	this.port = port;
	this.host = host;
    }

    public synchronized void init() {
	if (connected) {
	    return;
	}

	try {
	    engineClient = new NioClient(InetAddress.getByName(host), port);
	    engineClient.connect();
	    Thread clientThread = new Thread(engineClient);
	    clientThread.setDaemon(true);
	    clientThread.start();
	    connected = true;
	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
    
    public synchronized void destroy() {
	if(connected) {
	    engineClient.disconnect();
	    connected=false;
	}
    }

    public abstract String execute(String flowId, Map<String, String> params);

}
