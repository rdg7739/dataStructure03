package proj3;

import java.util.Map;

final class HuffmanNode<AnyType> {
	    public HuffmanNode( )
	    {
	        this( null, null, null, 1 );
	    }
	    public HuffmanNode( AnyType theElement, HuffmanNode<AnyType> lt, HuffmanNode<AnyType> rt, int freq)
	    {
	        element = theElement;
	        left    = lt;
	        right   = rt;
	        frequency = freq;
	        key = "1";
	    }

	    /**
	     * Return the size of the Huffman tree rooted at t.
	     */
	    public static <AnyType> int size( HuffmanNode<AnyType> t )
	    {
	        if( t == null )
	            return 0;
	        else
	            return 1 + size( t.left ) + size( t.right );
	    }

	    /**
	     * Return the height of the Huffman tree rooted at t.
	     */
	    public static <AnyType> int height( HuffmanNode<AnyType> t )
	    {
	        if( t == null )
	            return -1;
	        else
	            return 1 + Math.max( height( t.left ), height( t.right ) );
	    }


	    // Print tree rooted at current node using inorder traversal.
	    public void printInOrderFreq( )
	    {
	        if( left != null )
	            left.printInOrderFreq( );            // Left
	        if(element != null)
	        System.out.printf( "%5s\t %20d\n", this.element, this.getFrequency());       // Node
	        if( right != null )
	            right.printInOrderFreq( );           // Right
	    }
	    public void printInOrderMap(Map<Character, String> map)
	    {
	        if( left != null )
	            left.printInOrderMap(map);            // Left
	        if(element != null)
	        System.out.printf( "%5s\t%20s\n", this.element, map.get(this.element));       // Node
	        if( right != null )
	            right.printInOrderMap( map);           // Right
	    }

	    /**
	     * Return a reference to a node that is the root of a
	     * duplicate of the Huffman tree rooted at the current node.
	     */
	    public HuffmanNode<AnyType> duplicate( )
	    {
	        HuffmanNode<AnyType> root = new HuffmanNode<AnyType>( element, null, null, 1);

	        if( left != null )            // If there's a left subtree
	            root.left = left.duplicate( );    // Duplicate; attach
	        if( right != null )          // If there's a right subtree
	            root.right = right.duplicate( );  // Duplicate; attach
	        return root;                      // Return resulting tree
	    }
	    
	    public AnyType getElement( )
	    {
	        return element;
	    }
	    
	    public HuffmanNode<AnyType> getLeft( )
	    {
	        return left;
	    }
	    
	    public HuffmanNode<AnyType> getRight( )
	    {
	        return right;
	    }
	    
	    public void setElement( AnyType x )
	    {
	        element = x;
	    }
	    
	    public void setLeft( HuffmanNode<AnyType> t )
	    {
	        left = t;
	    }
	    
	    public void setRight( HuffmanNode<AnyType> t )
	    {
	        right = t;
	    }
	    
	    public int getFrequency() {
			return frequency;
		}

		public void incFrequency() {
			this.frequency++;
		}
		public String toString(){
			return "Character: " + this.element + " frequency:" + this.frequency + " key: " + this.key;
		}
		
		public String getKey() {
			return key;
		}

		 
		
		public void setKey(String key) {
			this.key = key;
		}
		private AnyType             element;
	    private HuffmanNode<AnyType> left;
	    private HuffmanNode<AnyType> right;
	    private int 				frequency;
	    private String				key;
}
