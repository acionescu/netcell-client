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

import java.io.Serializable;
import java.util.Map;

import net.segoia.netcell.control.Command;
import net.segoia.netcell.control.CommandResponse;
import net.segoia.netcell.control.NetCell;
import net.segoia.netcell.vo.definitions.EntityDefinitionSummary;

public class ThinClientNetcellDao implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 735484560444577232L;
    private NetCell client;

    public ThinClientNetcellDao() {
	client = new NetcellRmiClient("localhost", 2000, "NetcellRmiReceiver");
    }

    public ThinClientNetcellDao(String host, int port) {
	client = new NetcellRmiClient(host, port, "NetcellRmiReceiver");
    }

    private CommandResponse execute(Command c) {
	try {
	    return client.execute(c);
	} catch (Exception e) {
	    // TODO use an exception handler to deal with this error
	    e.printStackTrace();
	    return null;
	}

    }

    public Map<String, EntityDefinitionSummary> getWorkflowDefinitionSummaries() throws Exception {
	Command c = new Command();
	c.setName("get_workflow_definition_summaries");
	CommandResponse cr = client.execute(c);
	return (Map<String, EntityDefinitionSummary>) cr.getValue("definitions");
    }

    public CommandResponse execute(String flowId, Map<String, Object> values) {
	Command c = new Command();
	c.setName("execute");
	c.put("fid", flowId);
	c.putMap(values);

	return execute(c);
    }
}
