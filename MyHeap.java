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
      // while the array is not sorted
      int child1 = 2 * index + 1;
      int child2 = 2 * index + 2;
      if (child1 >= size && child2 >= size){
        // if the current index is a leaf (does not have any children)
        sorted = true;
        // the value has been pushed as far as it can go and thus the loop stops
      }
      else{
        // else
        int max = Math.max(data[index], data[child1]);
        // max is meant to find the largest of the parent and its children
        // it is first set to the max of the parent and first child (since it must have a child)
        if (child2 < size){
          // if the parent has another child
          max = Math.max(max, data[child2]);
          // max is set to the max of the original max and the value of the second child
        }
        int temp = data[index];
        if (max == temp){
          // if the parent is already the greatest
          sorted = true;
          // it is in the right place and sorted is set to true so the loop stops
        }
        else if (max == data[child1]){
          // else if child1 is the largest
          data[index] = data[child1];
          data[child1] = temp;
          // the value at the current index and child1 swaps place
          index = child1;
          // index is now updated to where the parent value is
        }
        else{
          // else if child2 is the largest
          data[index] = data[child2];
          data[child2] = temp;
          // parent value swaps with child2 value
          index = child2;
          // index is updated to where the parent value is
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
      // while the element at the index is not in place
      int parent = (index - 1) / 2;
      if (parent < 0){
        // if the index does not have a parent
        sorted = true;
        // the element at index is in place and sorted is set to true so the loop stops
      }
      else{
        // else
        int temp = data[index];
        if (temp > data[parent]){
          // if the element at index is greater than the parent
          data[index] = data[parent];
          data[parent] = temp;
          // the two values switch place
          index = parent;
          // index becomes the new index of the element being pushed up
        }
        else{
          // else if the parent is greater than the element
          sorted = true;
          // the element at index is in place and sorted is set to true so the loop stops
        }
      }
    }
  }

  /*
  - convert the array into a valid heap. [ should be O(n) ]
  */
  public static void heapify(int[] data){

    for (int i = (data.length - 2)/2; i >= 0; i--){
      // for every index starting from the last one that has a child
      pushDown(data, data.length, i);
      // push the element down to the correct place
    }
  }

  /*
  - sort the array by converting it into a heap then removing the largest value n-1 times. [ should be O(nlogn) ]
  */
  public static void heapsort(int[] data){
    heapify(data);
    // turn the array into a heap
    for (int i = data.length - 1; i > 0; i--){
      // for every index from the last index to 1 (excluding 0 since an array with one value is a heap by default)
      int temp = data[0];
      data[0] = data[i];
      data[i] = temp;
      // swap the max element (at index 0) with the element at index (the index of the last element that is not yet sorted)
      pushDown(data, i, 0);
      // push down the element that was swapped to index 0
    }
  }

  public static void main(String[]args){
    System.out.println("Size\t\tMax Value\tmerge /builtin ratio ");
    int[]MAX_LIST = {1000000000,500,10};
    for(int MAX : MAX_LIST){
      for(int size = 31250; size < 2000001; size*=2){
        long qtime=0;
        long btime=0;
        //average of 5 sorts.
        for(int trial = 0 ; trial <=20; trial++){
          int []data1 = new int[size];
          int []data2 = new int[size];
          for(int i = 0; i < data1.length; i++){
            data1[i] = (int)(Math.random()*MAX);
            data2[i] = data1[i];
          }
          long t1,t2;
          t1 = System.currentTimeMillis();
          MyHeap.heapsort(data2);
          t2 = System.currentTimeMillis();
          qtime += t2 - t1;
          t1 = System.currentTimeMillis();
          Arrays.sort(data1);
          t2 = System.currentTimeMillis();
          btime+= t2 - t1;
          if(!Arrays.equals(data1,data2)){
            System.out.println("FAIL TO SORT!");
            System.exit(0);
          }
        }
        System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
      }
      System.out.println();
    }
  }
}
