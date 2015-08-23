package org.semanticcloud.agency;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JadeBootThread extends Thread {

    private final String jadeBoot_CLASS_NAME = "jade.Boot";

    private final String MAIN_METHOD_NAME = "main";

    //add the <agent-local-name>:<fully-qualified-agent-class> name here;
// you can add more than one by semicolon separated values.
    private final String ACTOR_NAMES_args = "Agent2:org.semanticcloud.agency.agents.ProviderAgent;Agent3:org.semanticcloud.agency.agents.ProviderAgent;Agent1:org.semanticcloud.agency.agents.BrokerAgent";

    private final String GUI_args = "-gui";

    private final Class<?> secondClass;

    private final Method main;

    private final String[] params;

    public JadeBootThread() throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        secondClass = Class.forName(jadeBoot_CLASS_NAME);
        main = secondClass.getMethod(MAIN_METHOD_NAME, String[].class);
        params = new String[]{GUI_args, ACTOR_NAMES_args};
    }

    @Override
    public void run() {
        try {
            main.invoke(null, new Object[]{params});
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace();
        }

    }
}
