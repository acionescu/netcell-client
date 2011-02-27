/*******************************************************************************
 * Copyright 2011 Adrian Cristian Ionescu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package ro.zg.netcell.client;

import java.io.Serializable;
import java.util.Map;

import ro.zg.netcell.control.Command;
import ro.zg.netcell.control.CommandResponse;
import ro.zg.netcell.control.NetCell;
import ro.zg.netcell.vo.definitions.EntityDefinitionSummary;

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
