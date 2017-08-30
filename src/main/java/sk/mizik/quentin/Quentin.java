package sk.mizik.quentin;

import sk.mizik.quentin.config.Config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

/**
 * @author Marian Mizik
 * @since 1.0.0
 */
@ApplicationScoped
public class Quentin {

    @Inject
    private XmppService xmppService;

    @Inject
    private Config config;

    //HACK TO IMPLEMENT @Startup FUNCTIONALITY WITHOUT EJB, IN CDI ONLY CONTAINER
    private void start(
            @Observes(during = TransactionPhase.IN_PROGRESS)
            @Initialized(ApplicationScoped.class)
                    Object init
    ) {
        config.load();
        xmppService.init();
    }
}

//TODO replace path detection logic to make it application server implementation independent
//TODO aliases for requesting one command (or even full nice sentences)
//TODO casual chat feature
//TODO check regex validity
//TODO check jwt validity/details
//TODO translate words and phrases sk2en and en2sk
//TODO check for lunch menu
//TODO control redmine remotely (define ideal set of features)
//TODO control jenkins remotely (define ideal set of features)
//TODO get profile of person, colleague, customer
//TODO Searching WIKI
//TODO implement Q&A mechanism with search
//TODO crontab syntax helper (https://crontab.guru/#0/2_*_*_*_*)
