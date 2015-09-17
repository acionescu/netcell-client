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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import net.segoia.util.net.nio.NioClient;

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
