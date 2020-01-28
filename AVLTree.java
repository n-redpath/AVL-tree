package avl;

import java.util.LinkedList;

public class AVLTree<T extends Comparable<T>> {

	private TreeNode<T> root;
	public int size;

	public AVLTree() {
		this.root = null;
		this.size = 0;
	}

	public boolean exists(T value) {
		return existsHelper(value, this.root);
	}

	private boolean existsHelper(T value, TreeNode<T> root) {
		if (root == null) { // not found
			return false;
		} else {
			int comparison = root.value.compareTo(value);

			if (comparison == 0) { // found
				return true;
			} else if (comparison > 0) { // still looking - go left
				return existsHelper(value, root.left);
			} else { // still looking - go right
				return existsHelper(value, root.right);
			}
		}
	}


	public T min() {
		return minValueInSubtree(this.root);
	}


	private T minValueInSubtree(TreeNode<T> root) {
		while (root.left != null)
			root = root.left;

		return root.value;
	}

	public T max() {
		return maxValueInSubtree(this.root);
	}

	private T maxValueInSubtree(TreeNode<T> root) {
		while (root.right != null)
			root = root.right;

		return root.value;
	}

	public int insert(T value) 
	{
		this.root = insertHelper(value, this.root);
		return size;
	}

	private TreeNode<T> insertHelper(T value,
			TreeNode<T> root) {
		if (root == null) {
			// add new element as leaf of tree
			TreeNode<T> newNode = new TreeNode<T>(value); 
			size++;
			newNode.height =0; 
			updateHeight(newNode);
			return newNode;
		} else {
			int comparison = value.compareTo(root.value);
			if (comparison == 0) {
				// duplicate element -- return existing node
				//this used to be return root but i changed it to be return null;
				//size++;
				updateHeight(root);  
				return rebalance(root);
			} else if (comparison < 0) {
				// still looking -- go left
				root.setLeft(insertHelper(value, root.left));
				updateHeight(root);
			} else {
				// still looking -- go right

				root.setRight(insertHelper(value, root.right));
				updateHeight(root); 
			}
			
			return rebalance(root);
		}
	}
	
	public void remove(T value) {
		this.root = removeHelper(value, this.root); 
	}


	private TreeNode<T> removeHelper(T value,
			TreeNode<T> root) {

		if (root == null) { // did not find element
			return null;
		} else {
			int comparison = value.compareTo(root.value);

			if (comparison == 0) { // found element to remove 
				if (root.left == null || root.right == null) {
					// base case -- root has at most one subtree,
					// so return whichever one is not null (or null
					// if both are)
					size--;
					this.updateHeight(root);
					return (root.left == null ? root.right : root.left);
				} else {
					// node with two subtrees -- replace key
					// with successor and recursively remove
					// the successor.
					T minValue = minValueInSubtree(root.right);
					root.value = minValue;
					root.setRight(removeHelper(minValue, root.right));
					
				}
			} else if (comparison < 0) {
				// still looking for element to remove -- go left
				root.setLeft(removeHelper(value, root.left));
				
			} else {
				// still looking for element to remove -- go right
				root.setRight(removeHelper(value, root.right));
			} 
			this.updateHeight(root);
			return rebalance(root); 
		}
	}


	private void updateHeight(TreeNode<T> root) {
		int right =0; 
		int left = 0; 

		if (root.right==null) {
			right =-1; 
		}
		else {
			right = root.right.height;  
		}
		if (root.left==null) {
			left =-1; 
		}
		else {
			left = root.left.height; 
		}
		//take the max and add one
		
		root.height = Math.max(right, left)+1; 

	}

	private int getBalance(TreeNode<T> root) {
		int balance; 

		int right =0; 
		int left = 0; 

		if (root.right ==null) {
			right =-1; 
		}
		else {
			right = root.right.height;  
		}
		if (root.left ==null) {
			left =-1; 
		}
		else {
			left = root.left.height; 
		}

		balance = right-left; 
		return balance; 
	}

	private TreeNode<T> rebalance(TreeNode<T> root) { 
		if (this.getBalance(root)==2) {
			//then the right is higher than the left, check right node.:
			if (getBalance(root.right)==-1) {
				//then the right root is LEFT heavy
				//so rotate left at the right child. 
				root.setRight(rightRotate(root.right)); 
			}
			return this.leftRotate(root);
		}
		else if (this.getBalance(root)==-2){
			if (getBalance(root.left)==1) {
				//then the left root is RIGHT heavy
				//so rotate right at the left child. 
				root.setLeft(leftRotate(root.left)); 
			}
			return this.rightRotate(root); 
		}
		//below is just to make eclipse shut up. but also means if getBalance says theyre equal. 
			return root; 

	} 

	
	private TreeNode<T> rightRotate(TreeNode<T> root) {
		
		//set new root equal to the left node. 
		//set new roots left equal to the left of the left
		//set the new roots right equal to the old root
		//set the old roots left pointer to the new roots right pointer. 
		TreeNode<T> newonce = root.left; 
		root.setLeft(newonce.right);
		newonce.setRight(root);
		this.updateHeight(root);
		this.updateHeight(newonce);
		return newonce;
	
	}

	
	private TreeNode<T> leftRotate(TreeNode<T> root) {
		//set the root equal to the right node
		TreeNode<T> newroot = root.right;
		
		root.setRight(newroot.left);
		newroot.setLeft(root);
		updateHeight(newroot.left);
		updateHeight(root);
		updateHeight(newroot);
		return newroot; 

	}


	public TreeNode<T> getRoot() {
		return this.root;
	}

	public LinkedList<T> enumerate() {
		return enumerateHelper(this.root);
	}

	
	private LinkedList<T> enumerateHelper(TreeNode<T> root) {
		if (root == null) 
		{
			return new LinkedList<T>();
		}
		else
		{
			LinkedList<T> list = enumerateHelper(root.left);
			list.addLast(root.value);
			list.addAll(enumerateHelper(root.right));

			return list;
		}
	}
}
