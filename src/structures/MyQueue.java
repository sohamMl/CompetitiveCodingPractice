package structures;

import java.util.ArrayList;
import java.util.List;

public class MyQueue<T>{
    //popping happens at the end of the list or the last element
    List<T> insertList;
    List<T> removalList;
    
    public MyQueue(){
      insertList = new ArrayList<>();
      removalList = new ArrayList<>();
    }
  
    public void enqueue(T e){
      insertList.add(e);
    }
  
    public T dequeue(){
      try{
        int currentSize = removalList.size();
        if(currentSize!=0)
          return removalList.remove(currentSize-1);
        //move elements from insertList to removalList and then pop
        moveElements();
        currentSize = removalList.size();
        return removalList.remove(currentSize-1);
      }catch(Exception IndexOutOfBoundsException){
        throw new IndexOutOfBoundsException("Cannot remove elements from empty array");
      }
    }
  
    private void moveElements(){
      for(int i = insertList.size() - 1; i>=0 ;i--)
        removalList.add(insertList.remove(i));
    }
  
    public String toString(){
      String str = "[";
      for(int i=removalList.size()-1; i>=0 ;i-- )
        str+=removalList.get(i) + " ";
      for(int i=0;i<insertList.size();i++)
        str+=insertList.get(i) + " ";
      str += "]";
      return str;
    }
  
    public int size(){
      return insertList.size() + removalList.size();
    }
  
    public T peek(){
      try{
        if(removalList.size()==0)
        moveElements();
        int size = removalList.size();
        return removalList.get(size - 1);
      }catch(Exception IndexOutOfBoundsException){
        throw new IndexOutOfBoundsException("Queue is empty");
      }
    }
}
