package mikrotik.example.command;

public enum MikrotikCommands {

    INTERFACE_PRINT("/interface/print"),
    INTERFACE_ETHERNET_PRINT("/interface/ethernet/print"),
    INTERFACE_PPPOE_SERVER_PRINT_COMMAND("/interface/pppoe-server/print"),
    IP_ADDRESS_PRINT("/ip/address/print"),
    IP_POOL_PRINT_COMMAND("/ip/pool/print"),
    IP_POOL_USER_PRINT_COMMAND("/ip/pool/used/print"),
    DHCP_SERVER_PRINT_COMMAND("/ip/dhcp-server/print"),
    DHCP_SERVER_LEASE_PRINT("/ip/dhcp-server/lease/print"),
    CALEA_ADD_ACTION_SRC_IP("/ip/firewall/calea/add action=sniff-pc chain=forward sniff-id=%s "+
             "sniff-target=%s sniff-target-port=%s src-address=%s"),
    CALEA_ADD_ACTION_DEST_IP("/ip/firewall/calea/add action=sniff-pc chain=forward sniff-id=%s "+
            "sniff-target=%s sniff-target-port=%s dst-address=%s"),

    CALEA_FIND_ACTION_SNIFF_ID("/ip/firewall/calea/find sniff-id=%s"),
    CALEA_FIND_ACTION_SRC_IP("/ip/firewall/calea/find action src-address=%s"),
    CALEA_FIND_ACTION_DST_IP("/ip/firewall/calea/find action dst-address=%s"),
    CALEA_FIND_ALL("/ip/firewall/calea/print"),
    CALEA_REMOVE_SNIFF_ID("/ip/firewall/calea/remove numbers=%s")
;
    private String command;

    MikrotikCommands(String commandName) {
        this.command = commandName;
    }

    public String command() {
        return command;
    }

    public String command(String... args) {
        return String.format(command, args);
    }
}
