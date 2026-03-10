package de.netschach.game;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
class GameResponseCallback {

    @SuppressWarnings("unused")
    enum Protocol {
        http, https
    }

    @NotNull
    @NotEmpty
    private String uri;

    @NotNull
    @NotEmpty
    private Protocol protocol;

    @NotNull
    @NotEmpty
    private Integer port;

    private String host;

}
