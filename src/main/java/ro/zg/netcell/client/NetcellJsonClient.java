package ro.zg.netcell.client;

import java.util.Map;

import ro.zg.util.net.nio.RspHandler;


public class NetcellJsonClient extends NetcellSocketClient{
    
    

    public NetcellJsonClient(String host, int port) {
	super(host, port);
    }

    @Override
    public String execute(String flowId, Map<String, String> params) {
	RspHandler handler = new RspHandler();
	StringBuffer sb = new StringBuffer();
	sb.append("{method:execute, params:{");
	sb.append("fid:").append(flowId);
	String output = null;

	for (Map.Entry<String, String> e : params.entrySet()) {
	    sb.append(",").append(e.getKey()).append(":").append(e.getValue());
	}
	sb.append("}, id:x}\r");
	try {
	    String command = sb.toString();
	    engineClient.send(command.getBytes(), handler);
	    output = handler.getResponseAsString();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return stripOutput(output);
    }

    
    private String stripOutput(String s) {
	int start = s.indexOf("{\"jsonrpc\":\"2.0\", \"result\":");
	if(start >=0) {
	    int end = s.lastIndexOf(", \"id\":\"x\"");
	    
	    if(end > 0) {
		return s.substring(start+27,end);
	    }
	}
	return s;
    }
}
