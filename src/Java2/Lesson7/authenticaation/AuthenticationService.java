package Java2.Lesson7.authenticaation;

import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuthenticationService {
    private static final Set<AuthEntry> users = Set.of(
            new AuthEntry("l1", "pwd1", "nick1"),
            new AuthEntry("l2", "pwd2", "nick2"),
            new AuthEntry("l3", "pwd3", "nick3")
    );

    public AuthEntry findUserByCred(AuthEntry authEntry) {
        for (AuthEntry cred : users) {
            if (cred.equals(authEntry)) {
                return cred;
            }
        }
        return null;
    }
}
