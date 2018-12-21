

public class Queue<E> implements QueueStructure<E> {

private int MAX_SIZE;         // Maximum size of queue
private int first_element;    // Index of first element
private int last_element;     // Index of last element
private E[] Q;                // Array holding queue elements
private int size;             // Size of queue ( # elements )

  /**
   * Constructs an instance of a Queue
   *
    * @param size the size of the queue
   */
public Queue(int size) {
  MAX_SIZE = size + 1; // Pad the queue max size by 1 space
  // Init first_element and read elements
  last_element = 0; 
  first_element = 1;
  Q = (E[]) new Object[MAX_SIZE];  // Create queue array
  size = 0; // init size
}

@Override
public void clear() {
  last_element = 0;
  first_element = 1;
  size = 0;
}

@Override
public void enqueue(E it) {
  last_element = (last_element+1) % MAX_SIZE; // Circular increment
  Q[last_element] = it;
  size++;
}

@Override
public E dequeue() {
  E it = Q[first_element];
  first_element = (first_element+1) % MAX_SIZE; // Circular increment
  size--;
  return it;
}

@Override
public E first() {
  return Q[first_element];
}

@Override
public int length() { return size; }


}
