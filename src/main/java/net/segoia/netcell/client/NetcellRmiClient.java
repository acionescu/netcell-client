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

import net.segoia.netcell.control.Command;
import net.segoia.netcell.control.CommandResponse;
import net.segoia.netcell.control.NetCell;

public class NetcellRmiClient extends RmiClient<NetCell> implements NetCell {
    private NetCell remoteObject;


    public NetcellRmiClient(String host, int port, String objName) {
	super(host, port, objName);
	System.out.println("init netcell rmi client (host="+host+" port="+port+")");
    }

    public CommandResponse execute(Command input) throws Exception {
	try {
	    return getRemoteObject().execute(input);
	} catch (Exception e) {
	    remoteObject = null;
	    return getRemoteObject().execute(input);
	}
    }

    private NetCell getRemoteObject() throws Exception {
	if (remoteObject == null) {
	    remoteObject = getObject();
	}
	return remoteObject;
    }
}
