package GUI;

//This is the GUI singleton
//this will handle all states for the GUI ONLY


import DLL.DllController;

public class GUISingleton {
    private static GUISingleton instance = null;

    // Controllers
    private DllController dllController = new DllController();

    private GUISingleton() {}


    // Getters
    // -- Dll Controller --
    public DllController getDllController() {
        return dllController;
    }


    // Get the singleton, once this is called once it will set it and next call will be getting the already set singleton
    public static GUISingleton getInstance() {
        if (instance == null) {
            instance = new GUISingleton();
        }
        return instance;
    }
}
