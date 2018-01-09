package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ImmutableList {

    private final List<Integer> strings ;

    @SuppressWarnings("unchecked")
	public ImmutableList() {
    	 this(Collections.EMPTY_LIST);
    }
    
    private ImmutableList(List<Integer> delegate) {
        this.strings = Collections.unmodifiableList(delegate);
    }

    public List<Integer> getStrings() {
        List<Integer> newStrings = new ArrayList<Integer>(strings);
        return newStrings;
    }
    
    public ImmutableList cons(Integer e) {
        List<Integer> newStrings = new ArrayList<Integer>(strings);
        newStrings.add(e);
        return new ImmutableList(newStrings);
    }
    
    public ImmutableList remove(Integer e) {
        List<Integer> newStrings = new ArrayList<Integer>(strings);
        newStrings.remove(e);
        return new ImmutableList(newStrings);
    }
    
    public ImmutableList drop(int n) {
        List<Integer> newStrings = new ArrayList<Integer>(strings);
        newStrings.removeAll(newStrings.subList(0, n));
        return new ImmutableList(newStrings);
    }
    
    public Object head() {
    	List<Integer> newStrings = new ArrayList<Integer>(strings);
        return newStrings.get(0);
    }
    
    public List<Integer> tail() {
    	List<Integer> newStrings = new ArrayList<Integer>(strings);
    	return newStrings.subList(1, newStrings.size());
    }
    
    public List<Integer> reverse() {
    	List<Integer> newStrings = new ArrayList<Integer>(strings);
    	Collections.reverse(newStrings);
    	return newStrings;
    }
    
    public List<Integer> filter(Predicate<Integer> criteria, List<Integer> list) {
        return list.stream().filter(criteria).collect(Collectors.<Integer>toList());
 }
    
    
    public List<Integer> map(Function<Integer,Integer> criteria, List<Integer> list) {
        return list.stream().map(criteria).collect(Collectors.<Integer>toList());
 }
    
    public static void main(String[] args) {
    	ImmutableList im = new ImmutableList();
        System.out.println(im.getStrings());
        im = im.cons(new Integer(3));
        im = im.cons(new Integer(4));
        im = im.cons(new Integer(1));
        im = im.cons(new Integer(2));
        im = im.cons(new Integer(5));
        im = im.cons(new Integer(6));
        System.out.println(im.getStrings());
        im = im.remove(3);
        System.out.println(im.getStrings());
        System.out.println(im.head());
        System.out.println(im.tail());
        im = im.cons(10);
        System.out.println(im.getStrings());
        im = im.drop(2);
        System.out.println(im.getStrings());
        System.out.println(im.reverse());
        System.out.println(im.filter(x -> x > 3 , im.getStrings()));
        System.out.println(im.map(x -> x * 3 , im.getStrings()));
    }
}