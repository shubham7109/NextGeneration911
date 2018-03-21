package sb5.cs309.nextgen911;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.sip.ListeningPoint;
import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;


public class SipStackAndroid implements SipListener {

    private static SipStackAndroid instance = null;
    public static SipStack sipStack;
    public static SipProvider sipProvider;
    public static HeaderFactory headerFactory;
    public static AddressFactory addressFactory;
    public static MessageFactory messageFactory;
    public static SipFactory sipFactory;

    public static ListeningPoint udpListeningPoint;

    public static String localIp;
    public static int localPort = 5080;
    public static String localEndpoint = localIp + ":" + localPort;
    public static String transport = "udp";

    public static String remoteIp = "23.23.228.238";
    public static int remotePort = 5080;
    public static String remoteEndpoint = remoteIp + ":" + remotePort;
    public static String sipUserName;
    public String sipPassword;

    protected SipStackAndroid() {
        initialize();
    }

    public static SipStackAndroid getInstance() {
        if (instance == null) {
            instance = new SipStackAndroid();
        }
        return instance;
    }

    private static void initialize() {
        localIp = getIPAddress(true);
        localEndpoint = localIp + ":" + localPort;
        remoteEndpoint = remoteIp + ":" + remotePort;
        sipStack = null;
        sipFactory = SipFactory.getInstance();
        sipFactory.setPathName("android.gov.nist");
        Properties properties = new Properties();
        properties.setProperty("javaxx.sip.OUTBOUND_PROXY", remoteEndpoint + "/"
                + transport);
        properties.setProperty("javaxx.sip.STACK_NAME", "androidSip");
        try {
            // Create SipStack object
            sipStack = sipFactory.createSipStack(properties);
            System.out.println("createSipStack " + sipStack);
        } catch (PeerUnavailableException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            headerFactory = sipFactory.createHeaderFactory();
            addressFactory = sipFactory.createAddressFactory();
            messageFactory = sipFactory.createMessageFactory();
            udpListeningPoint = sipStack.createListeningPoint(localIp,
                    localPort, transport);
            sipProvider = sipStack.createSipProvider(udpListeningPoint);
            sipProvider.addSipListener(SipStackAndroid.getInstance());
            // this.send_register();
        } catch (PeerUnavailableException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Creating Listener Points");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
}
