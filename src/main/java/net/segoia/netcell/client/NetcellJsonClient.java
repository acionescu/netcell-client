/**
 * netcell-client - Client library for Netcell ESB
 * Copyright (C) 2009  Adrian Cristian Ionescu - https://github.com/acionescu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.segoia.netcell.client;

import java.util.Map;

import net.segoia.util.net.nio.RspHandler;


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
	    sb.append(",").append(escapeJson(e.getKey())).append(":").append(escapeJson(e.getValue()));
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
    
    private String escapeJson(String json) {
//	return json.replace(":", "\\:");
	if(json.matches("[\\[\\{\"].*")) {
	    return json;
	}
	return "\""+json+"\"";
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
