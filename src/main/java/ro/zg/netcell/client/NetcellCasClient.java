package ro.zg.netcell.client;

import java.util.Map;

import ro.zg.util.net.nio.RspHandler;

public class NetcellCasClient extends NetcellSocketClient {

    public NetcellCasClient(String host, int port) {
	super(host, port);
    }

    public String execute(String flowId, Map<String, String> params) {
	RspHandler handler = new RspHandler();
	StringBuffer sb = new StringBuffer();
	sb.append("execute(fid=").append(flowId);
	String output = null;

	for (Map.Entry<String, String> e : params.entrySet()) {
	    sb.append(",").append(e.getKey()).append("=").append(e.getValue());
	}
	sb.append(")\r");
	try {
	    String command = sb.toString();
	    engineClient.send(command.getBytes(), handler);
	    output = handler.getResponseAsString();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return output;
    }
    
}
