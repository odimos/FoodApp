package data;

public final class GlobalConfig {
    public static final String MASTER_HOST_IP = "192.168.1.2";
    public static final int MASTER_PORT_FOR_CLIENTS = 4440;
    public static final int MASTER_PORT_FOR_REDUCER_AS_CLIENT = 5011;

    public static final String WORKERNODE_HOST_IP = "192.168.1.2";
    public static final int INITIAL_PORT_FOR_WORKERS = 4442;

    public static final String REDUCER_HOST_IP = "192.168.1.2";
    public static final int REDUCER_PORT_FOR_MASTER_AS_CLIENT = 5012;

    public static final int ADD_STORE = 1;
    public static final int ADD_PRODUCT = 2;
    public static final int REMOVE_PRODUCT =3;
    public static final int RATE = 4;
    public static final int BUY = 5;
    public static final int FILTER_STORES = 6;

    public static String getCommandName(int command) {
    switch (command) {
        case 1:
            return "ADD_STORE";
        case 2:
            return "ADD_PRODUCT";
        case 3:
            return "REMOVE_PRODUCT";
        case 4:
            return "RATE";
        case 5:
            return "BUY";
        case 6:
            return "FILTER_STORES";
        default:
            return "UNKNOWN_COMMAND";
    }
}
 
    private GlobalConfig() {
        // Prevent instantiation
    }
}