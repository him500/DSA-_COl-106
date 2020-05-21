package col106.assignment4.WeakAVLMap;
import java.util.Vector;
import java.util.Queue;
import java.util.LinkedList; 

public class WeakAVLMap<K extends Comparable,V> implements WeakAVLMapInterface<K,V>{
	class Node<K extends Comparable,V>{
		K key;
		V data;
		int rank;
		Node left,right;
		boolean isExternalNode;
		Node(){
			rank=0;
			isExternalNode=true;
		}
		Node(K ke,V dat){
			key=ke;
			data=dat;
			isExternalNode=false;
			left=right=new Node();
			rank=1;
		}
		
	}

	private Node root;
	public int count;
	private int left_child_rank_diff,right_child_rank_diff;
	private V check_put,remove_return;
	private Vector range;
	private int rt;
	public WeakAVLMap(){
		this.root=new Node();
		this.count=0;
		this.check_put=null;
		this.range=new Vector<>();
		this.remove_return=null;
		this.rt=0;
	}
	Node left_rotation(Node tree){
		Node new_tree=tree.right;
		Node parent=tree;

		parent.right=new_tree.left;
		parent.rank=1+Math.max(parent.left.rank, parent.right.rank);
		new_tree.left=parent;
		new_tree.rank=1+Math.max(new_tree.right.rank,new_tree.left.rank);
		this.count++;

		return new_tree;
	}
	Node right_rotation(Node tree){
		Node new_tree=tree.left;
		Node parent=tree;

		parent.left=new_tree.right;
		parent.rank=1+Math.max(parent.left.rank, parent.right.rank);
		new_tree.right=parent;
		new_tree.rank=1+Math.max(new_tree.right.rank,new_tree.left.rank);
		this.count++;

		return new_tree;
	}
	Node insert(Node tree,K key,V val){
		if (tree.isExternalNode){
			tree=new Node(key,val);
			return tree;
		}
		else if(key.compareTo(tree.key)==0){
			this.check_put=(V)tree.data;
			tree.data=val;
			return tree;
		}
		else if (key.compareTo(tree.key)<0){		//left
			tree.left=insert(tree.left,key,val);

			if(tree.right.rank==tree.left.rank)
				tree.rank=tree.rank;	// move on
			else if(Math.max(tree.rank-tree.left.rank,tree.rank-tree.right.rank)<2)
				tree.rank++;			//promotion

			left_child_rank_diff=(tree.rank)-(tree.left.rank);
			if (left_child_rank_diff==0){

				right_child_rank_diff=(tree.rank)-(tree.right.rank);
				if (right_child_rank_diff==2){


					if(tree.left.rank-tree.left.right.rank==1){
						tree.left=left_rotation(tree.left);	//LR rotaion
						tree=right_rotation(tree);
					}
					else if(tree.left.rank-tree.left.left.rank==1)
						tree=right_rotation(tree);		//RR rotation

				}
				else if(right_child_rank_diff<2){
					tree.rank++;
				}

			}
		}
		else if (key.compareTo(tree.key)>0){		//right
			tree.right=insert(tree.right,key,val);
			if(tree.right.rank==tree.left.rank)
				tree.rank=tree.rank;	// move on
			else if(Math.max(tree.rank-tree.left.rank,tree.rank-tree.right.rank)<2)
				tree.rank++;			//promotion

			right_child_rank_diff=(tree.rank)-(tree.right.rank);
			if (right_child_rank_diff==0){

				left_child_rank_diff=(tree.rank)-(tree.left.rank);
				if (left_child_rank_diff==2){

					if(tree.right.rank-tree.right.left.rank==1){
						tree.right=right_rotation(tree.right);	//RL rotaion
						tree=left_rotation(tree);
					}
					else if(tree.right.rank-tree.right.right.rank==1)
						tree=left_rotation(tree);		//LL rotation
				}
				else if(left_child_rank_diff<2){
					tree.rank++;
				}
			}
		}
		return tree;

	}
	

	public V put(K key, V value){
		this.check_put=null;
		this.root=insert(this.root,key,value);
		if(this.check_put!=null){
			return this.check_put;
		}
		return null;
	}
	Node inorder_successor(Node tree){
		Node current_node=tree.right;
		while(current_node.left.isExternalNode!=true){
			current_node=current_node.left;
		}
		return current_node;
	}
	Node delete(Node tree,K key){
		if(tree.isExternalNode)
			return tree;
		else if(key.compareTo(tree.key)>0){			//right
			tree.right=delete(tree.right,key);
			right_child_rank_diff=tree.rank-tree.right.rank;
			if(right_child_rank_diff==2){
				if(tree.left.isExternalNode && tree.right.isExternalNode)
					tree.rank--;		//demotion if both child external
			}
			else if(right_child_rank_diff==3){
				left_child_rank_diff=tree.rank-tree.left.rank;
				if(left_child_rank_diff==2)
					tree.rank--;
				else if(left_child_rank_diff==1){
					if(!tree.left.isExternalNode){
						if(tree.left.rank-tree.left.right.rank==2 && tree.left.rank-tree.left.left.rank==2){
							tree.left.rank--;	tree.rank--;	//2-2 case
						}
						else if(tree.left.rank-tree.left.left.rank==1 && tree.left.rank-tree.left.right.rank==2){
							tree=right_rotation(tree);		//1-2 case
							tree.right.rank++;	tree.rank++;
						}
						else if(tree.left.rank-tree.left.left.rank==1 && tree.left.rank-tree.left.right.rank==1){
							tree=right_rotation(tree);		//1-1 case
						}
						else if(tree.left.rank-tree.left.left.rank==2 && tree.left.rank-tree.left.right.rank==1){
							tree.left=left_rotation(tree.left);		//2-1 case 
							tree=right_rotation(tree);				//LR rotaion
							tree.rank++;
						}
					}
				}
			}
		}
		else if(tree.key.compareTo(key)>0){
			tree.left=delete(tree.left,key);	//left
			left_child_rank_diff=tree.rank-tree.left.rank;
			if(left_child_rank_diff==2){
				if(tree.left.isExternalNode && tree.right.isExternalNode)
					tree.rank--;		//demotion if both child external
			}
			else if(left_child_rank_diff==3){
				right_child_rank_diff=tree.rank-tree.right.rank;
				if(right_child_rank_diff==2)
					tree.rank--;
				else if(right_child_rank_diff==1){
					if(!tree.right.isExternalNode){
						if(tree.right.rank-tree.right.left.rank==2 && tree.right.rank-tree.right.right.rank==2){
							tree.right.rank--;	tree.rank--;	//2-2 case
						}
						else if(tree.right.rank-tree.right.right.rank==1 && tree.right.rank-tree.right.left.rank==2){
							tree=left_rotation(tree);		//1-2 case
							tree.left.rank++;	tree.rank++;
						}
						else if(tree.right.rank-tree.right.right.rank==1 && tree.right.rank-tree.right.left.rank==1){
							tree=left_rotation(tree);		//1-1 case
						}
						else if(tree.right.rank-tree.right.right.rank==2 && tree.right.rank-tree.right.left.rank==1){
							tree.right=right_rotation(tree.right);		//2-1 case 
							tree=left_rotation(tree);				//RL rotaion
							tree.rank++;
						}
					}
				}
			}
		}
		else{
			this.rt++;
			if(this.rt==1)
				this.remove_return=(V) tree.data;
			// found key
			// System.out.println("found key "+ key);
			if(tree.right.isExternalNode && tree.left.isExternalNode)	//No child
				return tree.right;
			else if(!tree.right.isExternalNode && tree.left.isExternalNode)	//only right child
				return tree.right;
			else if(tree.right.isExternalNode && !tree.left.isExternalNode)	//only left child
				return tree.left;
			
			else{		// both child exist
				Node successor=inorder_successor(tree);
				tree.key=successor.key;
				tree.data=successor.data;
				tree.right=delete(tree.right, (K)successor.key);
				right_child_rank_diff=tree.rank-tree.right.rank;
				if(right_child_rank_diff==2){
					if(tree.left.isExternalNode && tree.right.isExternalNode)
						tree.rank--;		//demotion if both child external
				}
				else if(right_child_rank_diff==3){
					left_child_rank_diff=tree.rank-tree.left.rank;
					if(left_child_rank_diff==2)
						tree.rank--;
					else if(left_child_rank_diff==1){
						if(!tree.left.isExternalNode){
							if(tree.left.rank-tree.left.right.rank==2 && tree.left.rank-tree.left.left.rank==2){
								tree.left.rank--;	tree.rank--;	//2-2 case
							}
							else if(tree.left.rank-tree.left.left.rank==1 && tree.left.rank-tree.left.right.rank==2){
								tree=right_rotation(tree);		//1-2 case
								tree.right.rank++;	tree.rank++;
							}
							else if(tree.left.rank-tree.left.left.rank==1 && tree.left.rank-tree.left.right.rank==1){
								tree=right_rotation(tree);		//1-1 case
							}
							else if(tree.left.rank-tree.left.left.rank==2 && tree.left.rank-tree.left.right.rank==1){
								tree.left=left_rotation(tree.left);		//2-1 case 
								tree=right_rotation(tree);				//LR rotaion
								tree.rank++;
							}
						}
					}
				}
				return tree;
			}
		}
		return tree;
	}



	public V remove(K key){
		this.rt=0;
		this.remove_return=null;
		this.root=delete(this.root, key);
		return this.remove_return;
	}

	V search(Node tree,K key){
		V r=null;
		if (tree.isExternalNode){
			return null;
		}
		else if(tree.key.compareTo(key)==0){
			r=(V)tree.data;
		}
		else if (key.compareTo(tree.key)<0)		//left
			r=search(tree.left,key);
		else if (key.compareTo(tree.key)>0)		//right
			r=search(tree.right,key);
		
		return r;
	}
	public V get(K key){
		return search(this.root, key);
	}

	public Vector<V> inorder(Node tree,K key1,K key2,Vector v){
		if(tree.isExternalNode)
			return v;
		else{
			if (key1.compareTo(tree.key)<0) 
				v=inorder(tree.left, key1, key2,v); 
		
			if (key1.compareTo(tree.key)<=0 && key2.compareTo(tree.key)>=0)
				v.add(tree.data);
		
			if ((tree.key).compareTo(key2)<0) 
				v=inorder(tree.right, key1, key2,v); 

			return v;
		}
		
	
	} 
	public Vector<V> searchRange(K key1, K key2){
		this.range.clear();
		this.range=inorder(this.root,key1,key2,this.range);
		return this.range;
	}

	public int rotateCount(){
		return count;
	}

	int height(Node tree){
		if(tree.isExternalNode){
            return 0;
        }else{
			int lheight=0,rheight=0;
			lheight=height(tree.left);
			rheight=height(tree.right);

			return 1+Math.max(lheight,rheight);
        }
	}

	public int getHeight(){
		return height(this.root);
	}

	public Vector<K> BFS(){
		Vector<K> output=new Vector<>();
		Queue<Node> q=new LinkedList<>();
		Node current_node;
		q.add(this.root);
		if (this.root.isExternalNode)
			return output;

		while(!q.isEmpty()){
			current_node=q.remove();
			output.add((K)current_node.key);

			if(current_node.left.isExternalNode==false)
				q.add(current_node.left);
			if(current_node.right.isExternalNode==false)
				q.add(current_node.right);
		}	
		return output;
	}
	// public static void main(String[] args){
	// 	WeakAVLMap<Integer,Integer> WeakAVL = new WeakAVLMap();
		// WeakAVLMap<Integer,Integer> tree = new WeakAVLMap();
		// Vector<Integer> v;
		// tree.put(2,41);
		// tree.put(69,72);
		// tree.put(83,55);
		// tree.remove(83);
		// tree.put(5,100);
		// tree.put(109,14);
		// v=tree.BFS();
		// System.out.println("bfs "+v);
		// tree.put(3245,1435);
		// tree.put(345,643);
		// v=tree.BFS();
		// System.out.println("bfs "+v);
		// tree.put(13,12);


		// tree.remove(2);
		// System.out.println(tree.rotateCount());
		// v=tree.BFS();
		// System.out.println("bfs "+v);
		// // System.out.println(tree.root.right.left.rank);
		// tree.remove(3245);
		// tree.remove(109);
		// v=tree.BFS();
		// System.out.println("bfs "+v);
		// System.out.println("rank "+tree.root.rank);
		// System.out.println("rotate count "+tree.rotateCount());
		// tree.remove(13);
		// tree.remove(5);
		// tree.remove(96);
		// tree.remove(345);
		// tree.remove(69);

		// tree.put(2,41);
		// tree.put(69,72);
		// tree.put(83,55);
		// tree.remove(83);
		// tree.put(5,100);
		// tree.put(109,14);
		// tree.put(3245,1435);
		// tree.put(345,643);
		// tree.put(13,12);
		// System.out.println(tree.rotateCount());

		// // System.out.println("rank "+tree.root.rank+" root "+tree.root.key);
		// // System.out.println("remove val "+tree.remove(1));

		// // System.out.println("rank "+tree.root.rank+" root "+tree.root.key);

		// Vector<Integer> v1=tree.BFS();
		// System.out.println(v1);
	// 	System.out.println(WeakAVL.put(20059, 101));
	// 	System.out.println(WeakAVL.put(10012, 102));
	// 	System.out.println(WeakAVL.put(60492, 103));
	// 	System.out.println(WeakAVL.put(9441, 104));
	// 	System.out.println(WeakAVL.put(26652, 105));
	// 	System.out.println(WeakAVL.put(99932, 106));
	// 	System.out.println(WeakAVL.put(55306, 107));
	// 	System.out.println(WeakAVL.remove(55306));
	// 	System.out.println(WeakAVL.BFS());
	// 	System.out.println(WeakAVL.put(92379, 108));
	// 	System.out.println(WeakAVL.BFS());

	// 	System.out.println(WeakAVL.remove(9441));
	// 	System.out.println(WeakAVL.BFS());
	// 	System.out.println(WeakAVL.remove(60492));
	// 	System.out.println(WeakAVL.BFS());
	// 	System.out.println(WeakAVL.put(53275, 109));
	// 	System.out.println(WeakAVL.BFS());
		


	// }

}
