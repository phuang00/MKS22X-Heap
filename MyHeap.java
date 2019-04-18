import java.util.*;

public class MyHeap{

  /*
  - size  is the number of elements in the data array.
  - push the element at index i downward into the correct position. This will swap with the larger of the child nodes provided thatchild is larger. This stops when a leaf is reached, or neither child is larger. [ should be O(logn) ]
  - precondition: index is between 0 and size-1 inclusive
  - precondition: size is between 0 and data.length-1 inclusive.
  */
  private static void pushDown(int[] data,int size,int index){
    boolean sorted = false;
    while (!sorted){
      int child1 = 2 * index + 1;
      int child2 = 2 * index + 2;
      if (child1 >= size && child2 >= size){
        sorted = true;
      }
      else{
        int max = data[index];
        if (child1 < size){
          max = Math.max(max, data[child1]);
        }
        if (child2 < size){
          max = Math.max(max, data[child2]);
        }
        int temp = data[index];
        if (max == data[index]){
          sorted = true;
        }
        else if (child1 < size && max == data[child1]){
          data[index] = data[child1];
          data[child1] = temp;
          index = child1;
        }
        else{
          data[index] = data[child2];
          data[child2] = temp;
          index = child2;
        }
      }
    }
  }

  /*
  - push the element at index i up into the correct position. This will swap it with the parent node until the parent node is larger or the root is reached. [ should be O(logn) ]
  - precondition: index is between 0 and data.length-1 inclusive.
  */
  private static void pushUp(int[] data,int index){
    boolean sorted = false;
    while (!sorted){
      int parent = (index - 1) / 2;
      if (index == 0 || parent < 0){
        sorted = true;
      }
      else{
        int temp = data[index];
        if (temp > data[parent]){
          data[index] = data[parent];
          data[parent] = temp;
          index = parent;
        }
        else{
          sorted = true;
        }
      }
    }
  }

  /*
  - convert the array into a valid heap. [ should be O(n) ]
  */
  public static void heapify(int[] data){
    for (int i = 0; i < data.length; i++){
      pushUp(data, i);
    }
  }

  /*
  - sort the array by converting it into a heap then removing the largest value n-1 times. [ should be O(nlogn) ]
  */
  public static void heapsort(int[] data){

  }

  public static void main(String[] args) {
    int[] data = new int[]{4, 5, 62, 7, 3, 2};
    System.out.println(Arrays.toString(data));
    /*pushDown(data, data.length, 0);
    System.out.println(Arrays.toString(data));
    pushUp(data, 5);
    System.out.println(Arrays.toString(data));
    pushUp(data, 3);*/
    heapify(data);
    System.out.println(Arrays.toString(data));

    System.out.println();

    data = new int[]{8, 4, 17, 9, 0, 7, 12};
    System.out.println(Arrays.toString(data));
    heapify(data);
    System.out.println(Arrays.toString(data));
  }
}
