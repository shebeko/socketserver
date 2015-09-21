package ru.bis.integration.kase.util;

/**
 * @author shds
 *
 */
public interface KaseConstants {
    public static final String PATH_TO_ADAPTER_CONFIG = "adapter.conf";
    public static final String START_COMMAND = "start";
    public static final String STOP_COMMAND = "stop";
    public static final String STATUS_COMMAND = "status";
    public static final String CHECK_COMMAND = "check";
    public static final String STATUS_COMMAND_MSG = "adapter.kase.status?";
    public static final String MSG_RUNNING = "adapter.kase.isRunning";
    public static final String SHUTDOWN_COMMAND_MSG = "adapter.kase.shtdwn!";
    public static final String EOC_MARKER = "END_OF_FILE";
    public static final String TRANSPORT_SOCKET = "TRANSPORT_SOCKET";
    public static final String FIX_CFG = "conf/fix.cfg";
    public static final String GET_EXECUTION_REPORTS_REQUEST = "GETEXECUTIONREPORTS";
    public static final String GET_MARKET_DATA_REQUEST = "GETMARKETDATA";
    public static final String UNKNOWN_REQUEST_RECEIVED = "Unknown request received: ";
}