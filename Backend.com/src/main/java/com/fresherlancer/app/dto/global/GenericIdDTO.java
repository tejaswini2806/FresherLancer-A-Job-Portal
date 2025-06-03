package com.fresherlancer.app.dto.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericIdDTO<T> {
    private T id;
}
