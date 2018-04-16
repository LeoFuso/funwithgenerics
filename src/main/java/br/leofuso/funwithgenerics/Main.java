package br.leofuso.funwithgenerics;

import br.leofuso.funwithgenerics.model.Author;
import br.leofuso.funwithgenerics.model.Book;
import br.leofuso.funwithgenerics.model.Tag;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    private static List<Tag> tags = new ArrayList<Tag>();
    private static List<Book> books = new ArrayList<Book>();
    private static List<Author> authors = new ArrayList<Author>();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {

        setup();

        Class bookClass = Book.class;
        Class tagClass = Tag.class;
        Class authorClass = Author.class;


        Class<?> c = Class.forName(bookClass.getName());

        System.out.format("Simple name: %s%n", c.getSimpleName());
        System.out.format("Full name: %s%n", c.getName());

        System.out.println("\nAttributes: \n");

        for (Field f : c.getDeclaredFields()) {

            System.out.format("Field name: %s%n", f.getName());
            System.out.format("Type: %s%n", f.getType());
            System.out.format("GenericType: %s%n", f.getGenericType());
            System.out.format("Get Class: %s%n", f.getGenericType().getClass().getSimpleName());

            System.out.println("\n");

        }

        System.out.println("\nMethods: \n");

        List<Object> objectList = new ArrayList<>();

        for (Method m : c.getDeclaredMethods()) {

            System.out.format("Field name: %s%n", m.getName());
            System.out.format("Return Type: %s%n", m.getReturnType());
            System.out.format("Return GenericType: %s%n", m.getGenericReturnType());
            if(m.getGenericReturnType().toString().equals("java.util.List<br.leofuso.funwithgenerics.model.Tag>"))
                objectList.addAll(Arrays.asList(m.invoke(books.get(0), null)));

            System.out.format("Get Class: %s%n", m.getGenericReturnType().getClass().getSimpleName());

            System.out.println("\n");

        }

        //objectList.forEach(System.out::println);
        ArrayList<Tag> tags = (ArrayList<Tag>) objectList.get(0);
        tags.forEach(System.out::println);


    }

    private static void setup() {

        Tag drama = new Tag("Drama");
        Tag terror = new Tag("Terror");
        Tag romance = new Tag("Romance");
        Tag policial = new Tag("Policial");

        tags.addAll(
                Arrays.asList(
                        drama,
                        terror,
                        romance,
                        policial
                ));


        Author roger = new Author("Roger Waters", "roger@gmail.com");
        Author carl = new Author("Carl Sagan", "carl@gmail.com");

        authors.addAll(
                Arrays.asList(
                        roger,
                        carl
                ));

        Book dramaCop = new Book("Drama Cop");

        dramaCop.setAuthors(new ArrayList<Author>(Collections.singletonList(roger)));
        dramaCop.setTags(new ArrayList<Tag>(Arrays.asList(romance, policial)));
        books.add(dramaCop);
    }
}
