package com.damien.account.domain.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class CreateAccountRequest {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;
}
