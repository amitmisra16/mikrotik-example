package mikrotik.example.command;

public enum MikrotikCommands {

    INTERFACE_PRINT("/interface/print"),
    INTERFACE_ETHERNET_PRINT("/interface/ethernet/print"),
    INTERFACE_PPPOE_SERVER_PRINT_COMMAND("/interface/pppoe-server/print"),
    IP_ADDRESS_PRINT("/ip/address/print"),
    IP_POOL_PRINT_COMMAND("/ip/pool/print"),
    IP_POOL_USER_PRINT_COMMAND("/ip/pool/used/print"),
    DHCP_SERVER_PRINT_COMMAND("/ip/dhcp-server/print");

    private String command;

    MikrotikCommands(String commandName) {
        this.command = commandName;
    }

    public String command() {
        return command;
    }
}
