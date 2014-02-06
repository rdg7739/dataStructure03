package proj3;

import java.util.Map;

public class HuffmanTree<AnyType> {
	    public HuffmanTree( )
	    {
	        root = null;
	    }

	    public HuffmanTree( AnyType rootItem )
	    {
	        root = new HuffmanNode<AnyType>( rootItem, null, null, 1);
	    }

	    public void printInOrderFreq( )
	    {
	        if( root != null )
	           root.printInOrderFreq( );
	    }
	    public void printInOrderMap(Map<Character, String> map)
	    {
	        if( root != null )
	           root.printInOrderMap(map);
	    }


	    public void makeEmpty( )
	    {
	        root = null;
	    }
	    
	    public boolean isEmpty( )
	    {
	        return root == null;
	    }
	    
	    /**
	     * Merge routine for HuffmanTree class.
	     * Forms a new tree from rootItem, t1 and t2.
	     * Does not allow t1 and t2 to be the same.
	     * Correctly handles other aliasing conditions.
	     */
	    public void merge( AnyType rootItem, HuffmanTree<AnyType> t1, HuffmanTree<AnyType> t2, int freq)
	    {
	        if( t1.root == t2.root && t1.root != null )
	        {
	            System.err.println( "leftTree==rightTree; merge aborted" );
	            return;
	        }

	            // Allocate new node
	        root = new HuffmanNode<AnyType>( rootItem, t1.root, t2.root, freq);

	            // Ensure that every node is in one tree
	        if( this != t1 )
	            t1.root = null;
	        if( this != t2 )
	            t2.root = null;
	    }

	    public int size( )
	    {
	        return HuffmanNode.size( root );
	    }

	    public int height( )
	    {
	        return HuffmanNode.height( root );
	    }

	    public HuffmanNode<AnyType> getRoot( )
	    {
	        return root;
	    }
	    public void setKey(HuffmanNode<AnyType> node){
	    	if(node != null)
	    	setKey(node, "");
	    }
	    /**
	     * find the node and set the key to node
	     * @param node - itself
	     * @param key - binary code
	     */
	    private void setKey(HuffmanNode<AnyType> node, String key){
	    	
	    	StringBuffer hoo = new StringBuffer(key);
	    	if(node.getLeft() == null && node.getRight() == null){
	    			if(!key.equals(""))
	    				node.setKey(key);
	    	}
	    	if(node.getLeft() != null){
	    		hoo.append("0");
	    		setKey(node.getLeft(), hoo.toString());
	    		key = hoo.deleteCharAt(hoo.length()-1).toString();
	    	}
	    	if(node.getRight() != null){
	    		hoo.append("1");
	    		setKey(node.getRight(), hoo.toString());
	    		key = hoo.deleteCharAt(hoo.length()-1).toString();
	    	}
	    }

	    private HuffmanNode<AnyType> root;

	    static public void main( String [ ] args )
	    {
	        HuffmanTree<Integer> t1 = new HuffmanTree<Integer>( 1 );
	        HuffmanTree<Integer> t3 = new HuffmanTree<Integer>( 3 );
	        HuffmanTree<Integer> t5 = new HuffmanTree<Integer>( 5 );
	        HuffmanTree<Integer> t7 = new HuffmanTree<Integer>( 7 );
	        HuffmanTree<Integer> t2 = new HuffmanTree<Integer>( );
	        HuffmanTree<Integer> t4 = new HuffmanTree<Integer>( );
	        HuffmanTree<Integer> t6 = new HuffmanTree<Integer>( );

	        t2.merge( 2, t1, t3, 1);
	        t6.merge( 6, t5, t7, 1);
	        t4.merge( 4, t2, t6, 1 );

	        System.out.println( "t4 should be perfect 1-7; t2 empty" );
	        System.out.println( "----------------" );
	        System.out.println( "t4" );
	        t4.printInOrderFreq( );
	        System.out.println( "----------------" );
	        System.out.println( "t2" );
	        t2.printInOrderFreq( );
	        System.out.println( "----------------" );
	        System.out.println( "t4 size: " + t4.size( ) );
	        System.out.println( "t4 height: " + t4.height( ) );
	    }
}
