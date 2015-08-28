package org.semanticcloud.agency.provider;

import jade.domain.FIPAException;
import org.semanticcloud.agency.core.BaseAgent;
import org.semanticcloud.agency.provider.behaviours.ProviderNegotiationBehaviour;

public class ProviderAgent extends BaseAgent {
    @Override
    protected void setup() {
        try {
            registerAgent("provider");
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        System.out.println("Agent " + getLocalName() + " waiting for CFP...");
        addBehaviour(new ProviderNegotiationBehaviour(this));
    }

    public int prepareProposal() {
        // Simulate an evaluation by generating a random number
        return (int) (Math.random() * 10);
    }

    public boolean performAction() {
        // Simulate action execution by generating a random number
        return (Math.random() > 0.2);
    }
}
