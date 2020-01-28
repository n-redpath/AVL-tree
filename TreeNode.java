package avl;

public class TreeNode<T extends Comparable<T>> {

    public T value;            

    public TreeNode<T> left;   
    public TreeNode<T> right;  
    public TreeNode<T> parent; 


    public int height;
    
    // constructor
    public TreeNode(T value) {
        this.value = value;
	
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
	if (left != null)
	    left.parent = this;
    }
    public void setRight(TreeNode<T> right) {
        this.right = right;
	if (right != null)
	    right.parent = this;
    }
    
    public String toString() {
        return this.value.toString();
    }
}
