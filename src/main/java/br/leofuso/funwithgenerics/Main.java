package br.leofuso.funwithgenerics;

import br.leofuso.funwithgenerics.model.Author;
import br.leofuso.funwithgenerics.model.Book;
import br.leofuso.funwithgenerics.model.Tag;

import java.lang.reflect.*;
import java.util.*;

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


        for (Method m : c.getDeclaredMethods()) {

            System.out.format("Field name: %s%n", m.getName());
            System.out.format("Return Type: %s%n", m.getReturnType());
            System.out.format("Return GenericType: %s%n", m.getGenericReturnType());
            System.out.format("Get Class: %s%n", m.getGenericReturnType().getClass().getSimpleName());

            System.out.println("\n");

        }
        List<Tag> tags;
        List<Author> authors;

        //objectList.forEach(System.out::println);
        System.out.println("Tags from Book Object: \n");
        tags = (List<Tag>) getCollection(tagClass, books.get(0));
        authors = (List<Author>) getCollection(authorClass, books.get(0));
        tags.forEach(System.out::println);
        authors.forEach(System.out::println);


    }

    @SuppressWarnings("unchecked")
    public static <T, P> Collection<T> getCollection(Class<T> entityClass, P parentEntity) {

        Collection<Method> methods;
        Method toInvoke;

        methods = Arrays.asList(parentEntity.getClass().getDeclaredMethods());

        toInvoke = methods.stream()
                .filter(method -> {

                    Type returnType;
                    ParameterizedType parameterizedType;

                    returnType = method.getGenericReturnType();

                    if (!(returnType instanceof ParameterizedType))
                        return false;

                    parameterizedType = (ParameterizedType) returnType;
                    return parameterizedType.getActualTypeArguments()[0].equals(entityClass);
                })
                .filter(method -> method.getName().equals("get" + entityClass.getSimpleName() + "s"))
                .collect(CollectionUtils.singletonCollector());

        try {
            return (Collection<T>) toInvoke.invoke(parentEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
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
