package Java2.Lesson7.authenticaation;

import java.util.Objects;

public class AuthEntry {
    private String login;
    private String password;
    private String nickname;

    public AuthEntry(String login, String password) {
        this.login = login;
        this.password = password;
        nickname = login;
    }

    public AuthEntry(String login, String password, String nickname) {
        this(login,password);
        this.nickname = nickname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthEntry)) return false;
        AuthEntry authEntry = (AuthEntry) o;
        return Objects.equals(login, authEntry.login) && Objects.equals(password, authEntry.password) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
