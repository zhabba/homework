package com.ofg.loans.model;

import java.net.InetAddress;

/**
 * Client entity.
 */
public class Client extends BaseEntity {
    private String firstName;
    private String lastName;
    private InetAddress currentIP;

    public Client() {
    }

    public Client(String firstName, String lastName, InetAddress currentIP) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.currentIP = currentIP;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public InetAddress getCurrentIP() {
        return currentIP;
    }

    public void setCurrentIP(InetAddress currentIP) {
        this.currentIP = currentIP;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", currentIP=" + currentIP.toString() +
                '}';
    }
}
