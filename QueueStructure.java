
public interface QueueStructure<E> {

  /** Clears the queue. */
  public void clear();

  /** Place an element at the back of the queue.
      @param it The element being enqueued. */
  public void enqueue(E it);

  /** Remove and return element at the front of the queue.
      @return The element at the front of the queue. */
  public E dequeue();

  /**  Returns the first element in the queue
   * @return The first element in the queue. */
  public E first();

  /** @return The number of elements in the queue. */
  public int length();
}
