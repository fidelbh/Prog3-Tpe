package tpe.utils;


import java.util.*;

public class CustomLinkedList<E> implements Iterable<Node<E>> {
    private Node<E> first;
    private Node<E> last;
    private int length;
    private Comparator<E> comparator;

    public CustomLinkedList(){
        this.comparator = null;
        length = 0;
    }
    public CustomLinkedList(CustomLinkedList<E> customLinkedList){
        for (Node<E> node : customLinkedList){
            E data = node.getData();
            this.push(data);
        }
    }
    public CustomLinkedList(Comparator<E> comparator) {
        this.comparator = comparator;
        length = 0;
    }

    public Node<E> getFirst() {
        return first;
    }

    public void setFirst(Node<E> first) {
        this.first = first;
    }

    public Node<E> getLast() {
        return last;
    }

    public void setLast(Node<E> last) {
        this.last = last;
    }

    public int getLength() {
        return length;
    }

    public Comparator<E> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<E> comparator) {
        this.comparator = comparator;

        CustomLinkedList<E> auxList = new CustomLinkedList<>(comparator);
        for (Node<E> node : this) {
            auxList.addElement(node.getData());
            this.deleteNode(node);
        }

        for (Node<E> node : auxList) {
            this.addElement(node.getData());
        }
    }

    public void push(E data) {
        Node<E> node = new Node<>(data);
        if (this.isEmpty()) {
            this.setFirst(node);
        } else {
            this.getLast().setNext(node);
            node.setPrev(this.getLast());
        }
        this.setLast(node);
        length++;
    }

    // 4
    // 1, 3, 5, 7,
    public void addElement(E data) {
        if (Objects.nonNull(this.comparator))
            for (Node<E> current : this) {
                if (comparator.compare(current.getData(), data) >= 0) {
                    insertNodeAsPrev(new Node<>(data), current);
                    return;
                }
            }
        push(data);
    }

    private void insertNodeAsPrev(Node<E> nodeToInsert, Node<E> contextNode) {
        if (nodeToInsert == null || contextNode == null)
            throw new NullPointerException();

        // prev < nodeToInsert < contextNode
        Node<E> prev = contextNode.getPrev();
        if (Objects.isNull(prev)) {
            setFirst(nodeToInsert);
        } else {
            prev.setNext(nodeToInsert);
        }
        nodeToInsert.setPrev(prev);
        nodeToInsert.setNext(contextNode);
        contextNode.setPrev(nodeToInsert);
        this.length++;
    }

    private boolean itShouldBeLast(int i) {
        return i == length && i != 0;
    }

    private boolean positionNotFound(int index, int i) {
        return i < length && i != index;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public void validateIndex(int index) {
        if (index > this.length || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    public Node<E> getNodeBy(int index) {
        validateIndex(index);
        int pos = 0;
        for (Node<E> current : this) {
            if (pos == index) {
                return current;
            }
            pos++;
        }
        return null;
    }

    @Override
    public String toString() {
        return "CustomLinkedList{" +
                "first=" + first +
                ", last=" + last +
                ", lenght=" + length +
                '}';
    }

    public void deleteBy(int index) {
        validateIndex(index);

        deleteNode(getNodeBy(index));
    }

    public void deleteBy(E targetData) {
        int pos = 0;
        Node aux = first;
        while (pos < length) {
            if (aux.getData().equals(targetData)) {
                deleteNode(aux);
            } else {
                pos++;
            }
            aux = aux.getNext();
        }
    }

    public void deleteBy(Node<E> targetNode) {
        int pos = 0;
        Node aux = first;
        while (pos < length) {
            if (aux.equals(targetNode)) {
                deleteNode(aux);
            } else {
                pos++;
            }
            aux = aux.getNext();
        }
    }

    private void deleteNode(Node<E> targetNode) {
        // [A, targetNode, C]
        Node prev = targetNode.getPrev(); // A
        Node next = targetNode.getNext(); // C
        if (prev != null)
            prev.setNext(next); // A -> C
        if (next != null)
            next.setPrev(prev); // A <- C
        if(targetNode.equals(getFirst()))
            setFirst(next);
        if (targetNode.equals(getLast()))
            setLast(prev);
        length--;
    }

    public int getIndexBy(Node<E> targetNode) {
        int pos = 0;
        Node aux = first;
        while (pos < length) {
            if (aux.equals(targetNode)) {
                return pos;
            }
            pos++;
            aux = aux.getNext();
        }
        return -1;
    }

    public int getIndexBy(E targetData) {
        int pos = 0;
        Node aux = first;
        while (pos < length) {
            if (aux.getData().equals(targetData)) {
                return pos;
            }
            pos++;
            aux = aux.getNext();
        }
        return -1;
    }

    @Override
    public Iterator<Node<E>> iterator() {
        return new CustomLinkedListIterator();
    }

    public List<E> getAll() {
        List<E> result = new ArrayList<>();
        for (Node<E> node : this){
            result.add(node.getData());
        }
        return result;
    }

    private class CustomLinkedListIterator implements Iterator<Node<E>> {
        Node<E> current;

        public CustomLinkedListIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return Objects.nonNull(current);
        }

        @Override
        public Node<E> next() {
            Node<E> res = current;
            current = current.getNext();
            return res;
        }
    }


}