package com.bikkadit.electronic.store.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {

    private String imageName;
    private String message;
    private boolean success;
}
