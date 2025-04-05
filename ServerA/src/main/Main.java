package main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import aspect.ExceptionManager;

public class Main {

    private Skeleton skeleton;
    private ExceptionManager exceptionManager;

    public Main() {
        // Initialize exceptionManager before anything else
        this.exceptionManager = new ExceptionManager();
        
        try {
            // Now initialize skeleton
            this.skeleton = new Skeleton();
        } catch (RemoteException e) {
            // If exception occurs, process it
            exceptionManager.process(e);
        }
    }

    private void intialize() {
        try {

            this.skeleton.initialize();
        } catch (RemoteException | AlreadyBoundException e) {
            exceptionManager.process(e);
        }
    }

    private void run() throws AlreadyBoundException, RemoteException {
        this.skeleton.run();
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        Main main = new Main();
        main.intialize();    
        main.run();
    }

}