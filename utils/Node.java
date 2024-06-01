package tpe.utils;


import java.util.Objects;

public class Node<E> {
    private Node<E> next;
    private Node<E> prev;
    private E data;

    public Node(E data) {
        setData(data);
        next = null;
        prev = null;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) { this.next = next; }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) { this.prev = prev; }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = Objects.requireNonNull(data);
    }

    public void deleteAt(int index){
        if (index == 0){
            getPrev().setNext(getNext());
        }else{
            deleteAt(index -1);
        }
    }

    @Override
    public String toString() {
        return getData().toString();
    }
}