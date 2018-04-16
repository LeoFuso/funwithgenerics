package br.leofuso.funwithgenerics.model;


import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Tag {

    @NonNull
    String classification;

}
