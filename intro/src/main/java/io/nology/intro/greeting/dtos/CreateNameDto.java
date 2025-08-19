package io.nology.intro.greeting.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class CreateNameDto {
    @NotBlank
    @Length(min = 2)
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CreateNameDto [name=" + name + "]";
    }

}
