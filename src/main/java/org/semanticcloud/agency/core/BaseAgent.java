package org.semanticcloud.agency.core;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public abstract class BaseAgent extends Agent {

    protected void registerAgent(String serviceType) throws FIPAException
    {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(getLocalName());
        dfd.addServices(sd);
        DFService.register(this, dfd);
    }

    protected void deregisterAgent() throws FIPAException {
        DFService.deregister(this);
    }
}
