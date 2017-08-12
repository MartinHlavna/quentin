package sk.mizik.quentin;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.mizik.quentin.commands.Command;
import sk.mizik.quentin.commands.Result;
import sk.mizik.quentin.commands.Status;
import sk.mizik.quentin.config.Config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marian Mizik
 * @since 1.0.0
 */
@ApplicationScoped
public class XmppService {

    @Inject
    @Any
    Instance<Command> commands;

    @Inject
    private Config config;

    private final Logger LOG = LoggerFactory.getLogger(XmppService.class);

    private final Map<String, Chat> activeChats = new ConcurrentHashMap<>();

    void init() {
        AbstractXMPPConnection connection;
        try {
            //CONNECT TO XMPP SERVER
            XMPPTCPConnectionConfiguration xcc = XMPPTCPConnectionConfiguration.builder()
                    .setHost(config.getString(Constants.XMPP_HOST))
                    .setPort(config.getNumber(Constants.XMPP_PORT))
                    .setUsernameAndPassword(config.getString(Constants.XMPP_USER), config.getString(Constants.XMPP_PASS))
                    .setServiceName(config.getString(Constants.XMPP_HOST))
                    .setHostnameVerifier((s, sslSession) -> true)
                    .build();

            connection = new XMPPTCPConnection(xcc);
            connection.connect().login();

            //SET PRESENCE STATUS
            Presence presence = new Presence(Presence.Type.available);
            presence.setStatus("Quentin has arrived!");
            connection.sendStanza(presence);

            //LISTEN FOR ANY INCOMING MESSAGES
            ChatManager chatManager = ChatManager.getInstanceFor(connection);
            chatManager.addChatListener(
                    (newChat, createdLocally) -> {
                        if (!createdLocally) {
                            activeChats.put(newChat.getParticipant(), newChat);
                            newChat.addMessageListener(new QuentinChatListener());
                        }
                    });
            LOG.info("Quentin is connected and listening");
        } catch (SmackException | IOException | XMPPException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    //OBSERVE ASYNC RESULT EVENT AND SEND IT BACK TO SENDER
    public void resultEventObserver(@Observes Result result) {
        if (result.getStatus() != Status.OK) {
            LOG.error(result.getValue());
        }
        try {
            activeChats.get(result.getSender()).sendMessage(result.getValue());
        } catch (SmackException.NotConnectedException e) {
            LOG.error("Failed to execute command. Quentin is not connected");
        }
    }

    //LISTEN FOR INCOMING CHAT MESSAGES, CONSTRUCT PROPER RESULT OBJECT AND SEND IT BACK TO SENDER
    private class QuentinChatListener implements ChatMessageListener {
        @Override
        public void processMessage(Chat chat, Message message) {
            if (message.getBody() != null) {

                delay();

                String sanitizedBody = message.getBody().toLowerCase().trim();
                try {
                    //CHECK IF IT IS NORMALIZED COMMAND
                    if (sanitizedBody.startsWith("q:")) {
                        String[] parts = sanitizedBody.substring(2).split(" ");
                        boolean responded = false;
                        //LOOP OVER ALL FOUND COMMANDS AND IF SOME MATCHES, THEN EXECUTE
                        for (Command c : commands) {
                            if (c.getName().equalsIgnoreCase(parts[0])) {

                                Result result = c.execute(chat.getParticipant(), Arrays.copyOfRange(parts, 1, parts.length));
                                if (result.getStatus() != Status.OK) {
                                    LOG.error(result.getValue());
                                }
                                chat.sendMessage(result.getValue());
                                responded = true;
                                break;
                            }
                        }
                        if (!responded) {
                            chat.sendMessage("I do not recognize such command. Sorry.");
                        }
                    } else {
                        chat.sendMessage("No idea what are you talking about.");
                    }
                } catch (SmackException.NotConnectedException e) {
                    LOG.error("Failed to execute command. Quentin is not connected");
                }
            }
        }
    }

    //GENERATE DELAY TO MAKE IT LOOK MORE NATURAL IN CHAT
    private void delay() {
        try {
            Thread.sleep((long) 1000);
        } catch (InterruptedException e) {
            //DO NOTHING
        }
    }
}