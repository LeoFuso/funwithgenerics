package br.leofuso.funwithgenerics.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Book {

    @NonNull
    private String title;
    private List<Author> authors;
    private List<Tag> tags;

}
