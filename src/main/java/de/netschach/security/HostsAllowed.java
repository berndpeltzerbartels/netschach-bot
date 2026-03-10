package de.netschach.security;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HostsAllowed {

    @Value("${hosts-allowed}")
    private Set<String> hostsAllowed;

    private final Map<String, Set<String>> hostsByCallback = new HashMap<>();
    private final Set<String> ips = new HashSet<>();

    @PostConstruct
    void init() {
        hostsAllowed.forEach(this::addHosts);
    }

    public void check(@NonNull String ip, @NonNull String callbackServerName) throws IllegalHostException {
        if (!hostsByCallback.getOrDefault(callbackServerName, Collections.emptySet()).contains(ip)) {
            throw new IllegalHostException(ip);
        }
    }

    public void check(@NonNull String ip) {
        if (!ips.contains(ip)) {
            throw new IllegalHostException(ip);
        }
    }

    private void addHosts(String serverName) {
        try {
            Set<String> hostAddresses = getHostAdresses(serverName);
            ips.addAll(hostAddresses);
            hostsByCallback.put(serverName, getHostAdresses(serverName));
        } catch (Exception e) {
            log.error("can not add hosts for " + serverName);
        }
    }

    private Set<String> getHostAdresses(String serverName) throws Exception {
        return Arrays.stream(InetAddress.getAllByName(serverName))
                .map(InetAddress::getHostAddress)
                .peek(addr -> log.info("allowed-host-address mapping : {} -> {}", serverName, addr))
                .collect(Collectors.toSet());
    }
}
