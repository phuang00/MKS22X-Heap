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
    int row = (int)(Math.log(data.length)/Math.log(2.0));
    //System.out.println(row);
    for (int i = (int)(Math.pow(2, row)) - 1; i >= 0; i--){
      pushDown(data, data.length, i);
    }
  }

  /*
  - sort the array by converting it into a heap then removing the largest value n-1 times. [ should be O(nlogn) ]
  */
  public static void heapsort(int[] data){
    heapify(data);
    int index = data.length - 1;
    while (index > 0){
      int temp = data[0];
      data[0] = data[index];
      data[index] = temp;
      pushDown(data, index, 0);
      index--;
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
        for(int trial = 0 ; trial <=5; trial++){
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
