package org.semanticcloud.agency.provider.behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.semanticcloud.agency.provider.ProviderAgent;

import static jade.lang.acl.MessageTemplate.MatchPerformative;
import static jade.lang.acl.MessageTemplate.or;

public class ProviderNegotiationBehaviour extends Behaviour {
    private ProviderAgent providerAgent;

    public ProviderNegotiationBehaviour(ProviderAgent providerAgent) {
        this.providerAgent = providerAgent;
    }

    @Override
    public void action() {
        MessageTemplate template = getMessageTemplate();
        ACLMessage message = providerAgent.receive(template);

        if (message == null) {
            block();
            return;
        }
        AID sender = message.getSender();
        ACLMessage response = null;
        try {
            switch (message.getPerformative()) {
                case ACLMessage.CFP:
                    response = handleCfp(message);
                    break;
                case ACLMessage.ACCEPT_PROPOSAL:
                    response = handleAcceptProposal(null, null, message);
                case ACLMessage.REJECT_PROPOSAL:
                    handleRejectProposal(null, null, message);
                    break;
                default:
                    block();
                    return;
            }
            if (response != null) {
                response.addReceiver(sender);
                providerAgent.send(response);
            }
        } catch (FailureException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    protected ACLMessage handleCfp(ACLMessage cfp) {
        //todo
        System.out.println("Agent " + providerAgent.getLocalName() + ": CFP received from " + cfp.getSender().getName() + ". Action is " + cfp.getContent());
        int proposal = providerAgent.prepareProposal();
        if (proposal > 2) {
            return propose(cfp, proposal);

        } else {
            return refuse(cfp);
        }
    }


    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        //todo resources provisioning
        System.out.println("Agent " + providerAgent.getLocalName() + ": Proposal accepted");
        if (providerAgent.performAction()) {
            System.out.println("Agent " + providerAgent.getLocalName() + ": Action successfully performed");
            ACLMessage inform = accept.createReply();
            inform.setPerformative(ACLMessage.INFORM);
            return inform;
        } else {
            System.out.println("Agent " + providerAgent.getLocalName() + ": Action execution failed");
            throw new FailureException("unexpected-error");
        }
    }

    protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
        System.out.println("Agent " + providerAgent.getLocalName() + ": Proposal rejected");
    }

    private MessageTemplate getMessageTemplate() {
        return or(
                or(
                        MatchPerformative(ACLMessage.CFP),
                        MatchPerformative(ACLMessage.ACCEPT_PROPOSAL)
                ),
                MatchPerformative(ACLMessage.REJECT_PROPOSAL)
        );

    }

    private ACLMessage propose(ACLMessage cfp, int proposal) {
        System.out.println("Agent " + providerAgent.getLocalName() + ": Proposing " + proposal);
        ACLMessage propose = cfp.createReply();
        propose.setPerformative(ACLMessage.PROPOSE);
        propose.setContent(String.valueOf(proposal));
        return propose;
    }

    private ACLMessage refuse(ACLMessage cfp) {
        System.out.println("Agent " + providerAgent.getLocalName() + ": Refuse");
        ACLMessage refuse = cfp.createReply();
        refuse.setPerformative(ACLMessage.REFUSE);
        return refuse;
    }
}
