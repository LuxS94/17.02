package org.example._7_02.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationExceptions extends RuntimeException {
    private List<String> errorsMessages;

    public ValidationExceptions(List<String> errorsMessages) {

        super("Ci sono stati errori nel payload");
        this.errorsMessages = errorsMessages;
    }
}
