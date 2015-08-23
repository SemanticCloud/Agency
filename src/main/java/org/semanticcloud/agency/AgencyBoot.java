package org.semanticcloud.agency;

public class AgencyBoot {
    public static void main(String[] args) {
        System.out.print("Hello World");

        JadeBootThread jadeBootThread = null;
        try {
            jadeBootThread = new JadeBootThread();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        jadeBootThread.run();
    }
}
