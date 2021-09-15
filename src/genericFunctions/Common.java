package genericFunctions;

public class Common {
    // 
    public static void inOrderTraverse(int tree[],int parent){
        int leftChild = (parent * 2 ) + 1;
        int rightChild = leftChild+1;
        
        //if left child is not empty then move left
        System.out.println(tree[parent]+" "+tree[leftChild]+" "+ tree[rightChild]);
        if(tree[leftChild]>0)
            inOrderTraverse(tree, leftChild);

        //print current
        System.out.println(tree[parent]);

        //if right child is not empty then move right
        if(tree[rightChild]>0)
            inOrderTraverse(tree, rightChild);

    }
}
