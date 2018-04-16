package br.leofuso.funwithgenerics.model;


import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Author {

    @NonNull
    private String name;

    @NonNull
    private String email;

}
