package ru.yandex.couriers;

public class CourierAuthorization {

    private String login;
    private String password;

    public CourierAuthorization(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierAuthorization from(CreatingCourier creatingCourier) {
        return new CourierAuthorization(creatingCourier.getLogin(), creatingCourier.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}