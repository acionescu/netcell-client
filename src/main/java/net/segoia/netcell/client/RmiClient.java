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

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient<O> {
    private String remoteHost;
    private int registryPort;
    private String remoteObjectName;
    
    private Registry registry;
    
    public RmiClient(){
	
    }
    
    public RmiClient(String host,int port,String objName){
	this.remoteHost = host;
	this.registryPort = port;
	this.remoteObjectName = objName;
    }
    
    public O getObject() throws Exception{
	System.out.println("Obtaining object '"+remoteObjectName+"' from "+remoteHost+" on port "+registryPort);
	registry = LocateRegistry.getRegistry(remoteHost,registryPort);
	 return (O)registry.lookup(remoteObjectName);
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public int getRegistryPort() {
        return registryPort;
    }

    public String getRemoteObjectName() {
        return remoteObjectName;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public void setRegistryPort(int registryPort) {
        this.registryPort = registryPort;
    }

    public void setRemoteObjectName(String remoteObjectName) {
        this.remoteObjectName = remoteObjectName;
    }
    
    
}
