package by.bsuir;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {

    public static byte[] multicast = new byte[]{(byte) 225, 4, 5, 6};
    public static int port = 33333;
    public static String multicastStr = "225.4.5.6";
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            if ("-b".equals(args[0])) {
                broadcastChat();
            } else if ("-m".equals(args[0])) {
                multicastChat();
            } else throw new IllegalStateException("Input -b to set Broadcast mode or -m to set Multicast mode.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("Input -b to set Broadcast mode or -m to set Multicast mode.");
        }
    }

    private static void broadcastChat() throws IOException {
        BroadcastClient broadcastClient = new BroadcastClient(port);
        Thread listener = runListening(broadcastClient);
        System.out.println(String.format("My IP is %s", InetAddress.getLocalHost().getHostAddress()));
        System.out.println("\"--exit\" for exit; \"--ping\" for check online user; \n");
        while (true) {
            String message = scanner.next();
            if ("--exit".equals(message)) {
                break;
            }
            broadcastClient.send(message);
        }
        listener.interrupt();
    }

    private static void multicastChat() throws IOException {
        MulticastClient multicastClient = new MulticastClient(multicast, port);
        InetAddress ipAdress = InetAddress.getByAddress(multicast);
        Thread listener = runListening(multicastClient);
        System.out.println(String.format("My IP is %s", InetAddress.getLocalHost().getHostAddress()));
        System.out.println(String.format("Multicast address is %s port : %d", multicastStr, port));
        System.out.println(
                "\"--exit\" for exit; \"--ping\" for check online user; \n" +
                        "\"--leave\" to leave chat; \"--join\" to join chat;\n" +
                        "\"--offlb\" to off loopback message; \"--onlb\" to on loopback message;"
        );
        while (true)
        {
            String message = scanner.next();
            if ("--exit".equals(message)) {
                break;
            }
            if ("--leave".equals(message)) {
                multicastClient.leave(ipAdress);
                continue;
            }
            if ("--join".equals(message)) {
                multicastClient.join(ipAdress);
                continue;
            }
            if ("--offlb".equals(message)) {
                multicastClient.turnOffLoopbackMode();
                continue;
            }
            if ("--onlb".equals(message)) {
                multicastClient.turnOnLoopbackMode();
                continue;
            }
            multicastClient.send(message);
        }
        listener.interrupt();
    }

    private static Thread runListening(MulticastClient client) {
        Thread listener = new Thread(() -> {
            try {
                client.listen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        listener.start();
        return listener;
    }
}
